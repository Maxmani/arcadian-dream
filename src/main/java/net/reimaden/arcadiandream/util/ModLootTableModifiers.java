/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.block.Block;
import net.minecraft.loot.LootTables;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.item.ModItems;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.LocationCheckLootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.ItemPredicate;

import java.util.Map;

public class ModLootTableModifiers {

    private static final int peach_height = ArcadianDream.CONFIG.hisouSwordOptions.minHeightForPeaches();

    public static void modify() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            LootPool.Builder poolBuilder = LootPool.builder();

            if (LootTables.END_CITY_TREASURE_CHEST.equals(id)) {
                poolBuilder
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.25f))
                        .with(ItemEntry.builder(ModItems.DRAGON_GEM))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            for (Map.Entry<RegistryKey<Block>, Block> entry : Registry.BLOCK.getEntrySet()) {
                Block block = entry.getValue();
                if (entry.getKey().getValue().toString().endsWith("leaves") && block.getLootTableId().equals(id)) {
                    poolBuilder
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.10f))
                            .with(ItemEntry.builder(ModItems.HEAVENLY_PEACH))
                            .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(ModItems.HISOU_SWORD)))
                            .conditionally(LocationCheckLootCondition.builder(LocationPredicate.Builder.create().y(NumberRange.FloatRange.atLeast(peach_height))));
                    tableBuilder.pool(poolBuilder.build());
                }
            }
        });
    }
}
