/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item.custom.consumables;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.effect.ModEffects;
import net.reimaden.arcadiandream.sound.ModSounds;

public class GhastlyLanternItem extends Item {

    public GhastlyLanternItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient()) {
            user.addStatusEffect(new StatusEffectInstance(ModEffects.ETHEREAL, 300));
            if (!user.isCreative()) {
                stack.decrement(1);
            }
            user.incrementStat(Stats.USED.getOrCreateStat(this));

            float pitch = 0.9f + user.getRandom().nextFloat() * 0.2f;
            world.playSound(null, user.getX(), user.getY(), user.getZ(),
                    ModSounds.ITEM_GHASTLY_LANTERN_USE, user.getSoundCategory(), 1.0f, pitch);
            ((ServerWorld) world).spawnParticles(ParticleTypes.POOF,
                    user.getX(), user.getY() + 0.5, user.getZ(),
                    10, 0.5, 0.5, 0.5, 0.0);

            return TypedActionResult.success(stack);
        }

        return TypedActionResult.pass(stack);
    }
}
