/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.util.client;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.Item;
import net.reimaden.arcadiandream.item.custom.danmaku.DyeableBullet;
import net.reimaden.arcadiandream.item.ModItems;

import java.util.List;
import java.util.stream.Stream;

public class ModColorProviders {

    private static final List<Item> SHOTS = Stream.of(
            ModItems.CIRCLE_SHOT
    ).toList();

    public static void register() {
        for (Item bullet : SHOTS) {
            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? -1 : ((DyeableBullet) stack.getItem()).getColor(stack), bullet);
        }
    }
}
