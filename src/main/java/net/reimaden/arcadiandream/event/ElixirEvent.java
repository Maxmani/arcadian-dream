/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.config.ArcadianDreamConfig;
import net.reimaden.arcadiandream.effect.ModEffects;
import net.reimaden.arcadiandream.sound.ModSounds;
import net.reimaden.arcadiandream.util.IEntityDataSaver;

public class ElixirEvent implements ServerLivingEntityEvents.AllowDeath {

    private static final int MAX_AMPLIFIER = 4;
    private static final int DURATION = 6000;
    public static final ArcadianDreamConfig.HouraiElixirOptions_ HOURAI_ELIXIR_OPTIONS = ArcadianDream.CONFIG.houraiElixirOptions;

    @Override
    public boolean allowDeath(LivingEntity entity, DamageSource damageSource, float damageAmount) {
        if (((IEntityDataSaver) entity).arcadiandream$getPersistentData().getByte("elixir") < 3) return true;

        final StatusEffectInstance elixirFatigue = getElixirFatigue(entity);
        if (HOURAI_ELIXIR_OPTIONS.allowDying()) {
            if (hasElixirFatigue(entity) && elixirFatigue.getAmplifier() >= HOURAI_ELIXIR_OPTIONS.elixirFatigueThreshold() - 1) {
                return true;
            }
        }

        if (entity.getHealth() - damageAmount <= 0 && !damageSource.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            entity.getWorld().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                    ModSounds.ENTITY_GENERIC_RESURRECT, entity.getSoundCategory(), 1.0f, 1.0f);
            ((ServerWorld) entity.getWorld()).spawnParticles(ParticleTypes.FLAME, entity.getX(), entity.getY() + entity.getHeight() / 2, entity.getZ(),
                    50, 0, 0, 0, 0.05);

            if (HOURAI_ELIXIR_OPTIONS.nerfElixir()) {
                // If the entity already has max Elixir Fatigue, reset the timer
                if (hasElixirFatigue(entity) && elixirFatigue.getAmplifier() >= MAX_AMPLIFIER) {
                    entity.addStatusEffect(new StatusEffectInstance(ModEffects.ELIXIR_FATIGUE, DURATION, MAX_AMPLIFIER, true, true));
                } else {
                    entity.addStatusEffect(new StatusEffectInstance(ModEffects.ELIXIR_FATIGUE, DURATION,
                            hasElixirFatigue(entity) ? elixirFatigue.getAmplifier() + 1 : 0, true, true));
                }
            }

            entity.setHealth(entity.getMaxHealth());
            if (entity instanceof PlayerEntity player) {
                player.sendMessage(Text.translatable(ArcadianDream.MOD_ID + ".message.resurrection"), true);
            }

            return false;
        }

        return true;
    }

    public static StatusEffectInstance getElixirFatigue(LivingEntity entity) {
        return entity.getStatusEffect(ModEffects.ELIXIR_FATIGUE);
    }

    // Make sure the entity has Elixir Fatigue to avoid a NullPointerException
    public static boolean hasElixirFatigue(LivingEntity entity) {
        return entity.hasStatusEffect(ModEffects.ELIXIR_FATIGUE);
    }
}
