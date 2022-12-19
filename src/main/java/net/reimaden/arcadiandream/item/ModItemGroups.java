/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.reimaden.arcadiandream.ArcadianDream;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup ITEMS = FabricItemGroup.builder(new Identifier(ArcadianDream.MOD_ID, "items"))
            .icon(() -> new ItemStack(ModItems.POWER_ITEM))
            .build();

    public static final ItemGroup BLOCKS = FabricItemGroup.builder(new Identifier(ArcadianDream.MOD_ID, "blocks"))
            .icon(() -> new ItemStack(ModItems.ONBASHIRA))
            .build();

    public static final ItemGroup DANMAKU = FabricItemGroup.builder(new Identifier(ArcadianDream.MOD_ID, "danmaku"))
            .icon(() -> new ItemStack(ModItems.ORB_BULLET))
            .build();
}
