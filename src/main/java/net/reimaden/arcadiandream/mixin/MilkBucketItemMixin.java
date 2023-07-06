/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.mixin;

import net.fabricmc.fabric.api.tag.convention.v1.TagUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.util.ModTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(MilkBucketItem.class)
public class MilkBucketItemMixin {

    @Unique
    private List<StatusEffectInstance> arcadiandream$storedEffects;

    @Inject(method = "finishUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z", shift = At.Shift.BEFORE))
    private void arcadiandream$storeEffects(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        ArrayList<StatusEffectInstance> list = new ArrayList<>();

        for (StatusEffectInstance effect : user.getStatusEffects()) {
            if (TagUtil.isIn(ModTags.StatusEffects.UNREMOVABLE, effect.getEffectType())) {
                list.add(effect);
            }
        }

        arcadiandream$storedEffects = list;
    }

    @Inject(method = "finishUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z", shift = At.Shift.AFTER))
    private void arcadiandream$restoreEffects(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        for (StatusEffectInstance effect : arcadiandream$storedEffects) {
            user.addStatusEffect(effect);
        }

        arcadiandream$storedEffects = null;
    }
}
