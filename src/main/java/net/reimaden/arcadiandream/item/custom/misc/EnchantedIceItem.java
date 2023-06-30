/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item.custom.misc;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnchantedIceItem extends Item {

    public EnchantedIceItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
