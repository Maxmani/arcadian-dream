/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;

import java.util.Collections;
import java.util.List;

public interface EnchantmentHandler {

    default List<EnchantmentTarget> getEnchantmentTypes() {
        return Collections.emptyList();
    }

    default List<Enchantment> isInvalid() {
        return Collections.emptyList();
    }

    default boolean isExplicitlyValid(Enchantment enchantment) {
        return false;
    }
}
