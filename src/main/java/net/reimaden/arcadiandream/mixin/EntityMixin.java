/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.entity.custom.danmaku.BaseBulletEntity;
import net.reimaden.arcadiandream.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements IEntityDataSaver {

    @Unique
    private NbtCompound arcadiandream$persistentData;

    @ModifyExpressionValue(method = "updateMovementInFluid", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;length()D", ordinal = 0))
    private double arcadiandream$preventPushFromFluids(double original) {
        Entity entity = ((Entity) (Object) this);
        return original * (entity instanceof BaseBulletEntity ? 0 : 1);
    }

    @Override
    public NbtCompound arcadiandream$getPersistentData() {
        if (this.arcadiandream$persistentData == null) {
            this.arcadiandream$persistentData = new NbtCompound();
        }

        return arcadiandream$persistentData;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    private void arcadiandream$injectWriteMethod(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        if (arcadiandream$persistentData != null) {
            nbt.put(ArcadianDream.MOD_ID, arcadiandream$persistentData);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    private void arcadiandream$injectReadMethod(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains(ArcadianDream.MOD_ID)) {
            arcadiandream$persistentData = nbt.getCompound(ArcadianDream.MOD_ID);
        }
    }
}
