/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.TagKey;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.entity.custom.BaseBulletEntity;
import net.reimaden.arcadiandream.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements IEntityDataSaver {

    private NbtCompound persistentData;

    // TODO: Find a better way to do this
    @Inject(method = "updateMovementInFluid", at = @At("HEAD"), cancellable = true)
    private void arcadiandream$preventPushFromFluids(TagKey<Fluid> tag, double speed, CallbackInfoReturnable<Boolean> cir) {
        Entity bullet = ((Entity) (Object) this);
        if (bullet instanceof BaseBulletEntity) {
            cir.setReturnValue(false);
        }
    }

    @Override
    public NbtCompound getPersistentData() {
        if (this.persistentData == null) {
            this.persistentData = new NbtCompound();
        }

        return persistentData;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    private void arcadiandream$injectWriteMethod(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        if (persistentData != null) {
            nbt.put(ArcadianDream.MOD_ID, persistentData);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    private void arcadiandream$injectReadMethod(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains(ArcadianDream.MOD_ID)) {
            persistentData = nbt.getCompound(ArcadianDream.MOD_ID);
        }
    }
}
