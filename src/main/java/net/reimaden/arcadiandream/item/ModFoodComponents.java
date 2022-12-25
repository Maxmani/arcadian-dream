/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {

    public static final FoodComponent HEAVENLY_PEACH = new FoodComponent.Builder().hunger(6).saturationModifier(0.4F).alwaysEdible()
            .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE,600),1F).build();
}
