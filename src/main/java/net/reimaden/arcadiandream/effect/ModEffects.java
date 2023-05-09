/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;

public class ModEffects {

    public static final StatusEffect ETHEREAL = registerStatusEffect("ethereal", new EtherealEffect(StatusEffectCategory.BENEFICIAL, 0x96F1FF));
    public static final StatusEffect ELIXIR_FATIGUE = registerStatusEffect("elixir_fatigue", new ElixirFatigueEffect(StatusEffectCategory.HARMFUL, 0xEB4034)
            .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "EC250697-07AC-48A4-BE85-F56501DF64B3", -0.2, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "BAD32E34-9947-4046-864A-BCF0F4406FF7", -0.1f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, "367ADDF1-00E0-4006-A93B-0ED98FC4F6B5", -2, EntityAttributeModifier.Operation.ADDITION));

    private static StatusEffect registerStatusEffect(String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(ArcadianDream.MOD_ID, name), effect);
    }

    public static void register() {
        ArcadianDream.LOGGER.debug("Registering status effects for " + ArcadianDream.MOD_ID);
    }
}
