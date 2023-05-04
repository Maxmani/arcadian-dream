/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.ItemStack;
import net.reimaden.arcadiandream.util.EnchantmentHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {

    @Final public EnchantmentTarget target;

    @Inject(method = "isAcceptableItem", at = @At("HEAD"), cancellable = true)
    private void arcadiandream$isAcceptable(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() instanceof EnchantmentHandler) {
            if (((EnchantmentHandler) stack.getItem()).isExplicitlyValid((Enchantment) (Object) this)) {
                cir.setReturnValue(true);
            }

            boolean contains = ((EnchantmentHandler) stack.getItem()).getEnchantmentTypes().contains(target);
            boolean itemAccepts = !((EnchantmentHandler) stack.getItem()).isInvalid().contains((Enchantment) (Object) this);

            if (contains && itemAccepts) {
                cir.setReturnValue(true);
            }
        }
    }
}
