/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {

    public static final FoodComponent HEAVENLY_PEACH = new FoodComponent.Builder().hunger(6).saturationModifier(0.4f).alwaysEdible()
            .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE,600),1.0f).build();
    public static final FoodComponent LAMPREY = new FoodComponent.Builder().hunger(3).saturationModifier(0.3f)
            .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 140, 1), 1.0f).build();
    public static final FoodComponent COOKED_LAMPREY = new FoodComponent.Builder().hunger(10).saturationModifier(0.8f).build();
}
