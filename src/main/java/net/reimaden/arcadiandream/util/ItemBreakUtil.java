/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.util;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.Item;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.item.custom.danmaku.BaseBulletItem;

public class ItemBreakUtil {
    private static final ImmutableList<Item> DISALLOWED_ITEMS = ImmutableList.of(
            ModItems.HOURAI_ELIXIR
    );

    public static boolean isDisallowed(Item item) {
        return DISALLOWED_ITEMS.contains(item) || item instanceof BaseBulletItem;
    }
}

