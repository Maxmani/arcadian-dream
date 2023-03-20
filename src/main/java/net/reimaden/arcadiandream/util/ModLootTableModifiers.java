/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.block.Block;
import net.minecraft.loot.LootTables;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
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

    private static final int PEACH_HEIGHT = ArcadianDream.CONFIG.hisouSwordOptions.minHeightForPeaches();
    private static final float TEMPLATE_CHANCE = 0.25f;
    private static final float TEMPLATE_CHANCE_ALT = 0.10f;

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

            for (Map.Entry<RegistryKey<Block>, Block> entry : Registries.BLOCK.getEntrySet()) {
                Block block = entry.getValue();
                if (entry.getKey().getValue().toString().endsWith("leaves") && block.getLootTableId().equals(id)) {
                    poolBuilder
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.10f))
                            .with(ItemEntry.builder(ModItems.HEAVENLY_PEACH))
                            .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(ModItems.HISOU_SWORD)))
                            .conditionally(LocationCheckLootCondition.builder(LocationPredicate.Builder.create().y(NumberRange.FloatRange.atLeast(PEACH_HEIGHT))));
                    tableBuilder.pool(poolBuilder.build());
                }
            }

            // Pattern templates
            if (LootTables.SHIPWRECK_SUPPLY_CHEST.equals(id)) {
                poolBuilder
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(TEMPLATE_CHANCE_ALT))
                        .with(ItemEntry.builder(ModItems.SPREAD_PATTERN_TEMPLATE));
                tableBuilder.pool(poolBuilder.build());
            }
            if (LootTables.SHIPWRECK_TREASURE_CHEST.equals(id)) {
                poolBuilder
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(TEMPLATE_CHANCE_ALT))
                        .with(ItemEntry.builder(ModItems.SPREAD_PATTERN_TEMPLATE));
                tableBuilder.pool(poolBuilder.build());
            }
            if (LootTables.SHIPWRECK_MAP_CHEST.equals(id)) {
                poolBuilder
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(TEMPLATE_CHANCE_ALT))
                        .with(ItemEntry.builder(ModItems.SPREAD_PATTERN_TEMPLATE));
                tableBuilder.pool(poolBuilder.build());
            }
            if (LootTables.PILLAGER_OUTPOST_CHEST.equals(id)) {
                poolBuilder
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(TEMPLATE_CHANCE))
                        .with(ItemEntry.builder(ModItems.RAY_PATTERN_TEMPLATE));
                tableBuilder.pool(poolBuilder.build());
            }
            if (LootTables.NETHER_BRIDGE_CHEST.equals(id)) {
                poolBuilder
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(TEMPLATE_CHANCE))
                        .with(ItemEntry.builder(ModItems.RING_PATTERN_TEMPLATE));
                tableBuilder.pool(poolBuilder.build());
            }
            if (LootTables.ANCIENT_CITY_CHEST.equals(id)) {
                poolBuilder
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(TEMPLATE_CHANCE))
                        .with(ItemEntry.builder(ModItems.CONE_PATTERN_TEMPLATE));
                tableBuilder.pool(poolBuilder.build());
            }
            if (LootTables.ABANDONED_MINESHAFT_CHEST.equals(id)) {
                poolBuilder
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(TEMPLATE_CHANCE))
                        .with(ItemEntry.builder(ModItems.DOUBLE_PATTERN_TEMPLATE));
                tableBuilder.pool(poolBuilder.build());
            }
            if (LootTables.STRONGHOLD_CORRIDOR_CHEST.equals(id)) {
                poolBuilder
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(TEMPLATE_CHANCE_ALT))
                        .with(ItemEntry.builder(ModItems.TRIPLE_PATTERN_TEMPLATE));
                tableBuilder.pool(poolBuilder.build());
            }
            if (LootTables.STRONGHOLD_CROSSING_CHEST.equals(id)) {
                poolBuilder
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(TEMPLATE_CHANCE_ALT))
                        .with(ItemEntry.builder(ModItems.TRIPLE_PATTERN_TEMPLATE));
                tableBuilder.pool(poolBuilder.build());
            }
            if (LootTables.STRONGHOLD_LIBRARY_CHEST.equals(id)) {
                poolBuilder
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(TEMPLATE_CHANCE_ALT))
                        .with(ItemEntry.builder(ModItems.TRIPLE_PATTERN_TEMPLATE));
                tableBuilder.pool(poolBuilder.build());
            }
        });
    }
}
