/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.TagKey;
import net.reimaden.arcadiandream.entity.custom.BaseBulletEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    // TODO: Find a better way to do this
    @Inject(method = "updateMovementInFluid", at = @At("HEAD"), cancellable = true)
    private void arcadiandream$preventPushFromFluids(TagKey<Fluid> tag, double speed, CallbackInfoReturnable<Boolean> cir) {
        Entity bullet = ((Entity) (Object) this);
        if (bullet instanceof BaseBulletEntity) {
            cir.setReturnValue(false);
        }
    }
}
