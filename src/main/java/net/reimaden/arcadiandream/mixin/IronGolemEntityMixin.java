/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.IronGolemEntity;
import net.reimaden.arcadiandream.util.ModTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IronGolemEntity.class)
public class IronGolemEntityMixin {

    @Inject(method = "canTarget", at = @At("HEAD"), cancellable = true)
    private void arcadiandream$excludeFairies(EntityType<?> type, CallbackInfoReturnable<Boolean> cir) {
        if (type.isIn(ModTags.EntityTypes.FAIRIES)) {
            cir.setReturnValue(false);
        }
    }
}
