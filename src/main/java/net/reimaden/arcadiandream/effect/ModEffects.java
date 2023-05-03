/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;

public class ModEffects {

    public static final StatusEffect ETHEREAL = registerStatusEffect("ethereal", new EtherealEffect(StatusEffectCategory.BENEFICIAL, 0x96F1FF));

    @SuppressWarnings("SameParameterValue")
    private static StatusEffect registerStatusEffect(String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(ArcadianDream.MOD_ID, name), effect);
    }

    public static void register() {
        ArcadianDream.LOGGER.debug("Registering status effects for " + ArcadianDream.MOD_ID);
    }
}
