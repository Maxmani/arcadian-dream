/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.loot;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.block.Block;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.LocationCheckLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.item.ModItems;

import java.util.Map;

public class ModLootTableModifiers {

    private static final int PEACH_HEIGHT = ArcadianDream.CONFIG.hisouSwordOptions.minHeightForPeaches();

    private static final float TEMPLATE_CHANCE = 0.25f;
    private static final float TEMPLATE_CHANCE_ALT = 0.1f;
    private static final float ENCHANTED_ICE_CHANCE = 0.1f;

    private static final Identifier[] SPREAD_PATTERN_STRUCTURES = {
            LootTables.SHIPWRECK_SUPPLY_CHEST, LootTables.SHIPWRECK_TREASURE_CHEST, LootTables.SHIPWRECK_MAP_CHEST
    };
    private static final Identifier[] TRIPLE_PATTERN_STRUCTURES = {
            LootTables.STRONGHOLD_CORRIDOR_CHEST, LootTables.STRONGHOLD_CROSSING_CHEST, LootTables.STRONGHOLD_LIBRARY_CHEST
    };
    private static final Identifier[] EXTRA_LOOT_TABLES = {
            LootTables.SIMPLE_DUNGEON_CHEST, LootTables.END_CITY_TREASURE_CHEST, LootTables.BASTION_TREASURE_CHEST, ModLootTables.ABANDONED_SHRINE_TREASURE_CHEST,
            LootTables.DESERT_PYRAMID_CHEST, LootTables.WOODLAND_MANSION_CHEST, LootTables.ANCIENT_CITY_ICE_BOX_CHEST, LootTables.ANCIENT_CITY_CHEST,
            LootTables.BURIED_TREASURE_CHEST, LootTables.JUNGLE_TEMPLE_CHEST, LootTables.ABANDONED_MINESHAFT_CHEST, LootTables.STRONGHOLD_CROSSING_CHEST,
            LootTables.STRONGHOLD_CORRIDOR_CHEST
    };

    // A loot pool that is used across multiple tables
    private static final LootPool EXTRA_LOOT_POOL = LootPool.builder()
            .rolls(ConstantLootNumberProvider.create(1))
            .conditionally(RandomChanceLootCondition.builder(0.05f))
            .with(ItemEntry.builder(ModItems.TIME_ORB).weight(50))
            .with(ItemEntry.builder(ModItems.FOLDING_CHAIR).weight(50))
            .build();

    private static final LootCondition.Builder NEEDS_FROZEN_OCEAN_BIOME = LocationCheckLootCondition.builder(LocationPredicate.Builder.create().biome(BiomeKeys.FROZEN_OCEAN));
    private static final LootCondition.Builder NEEDS_DEEP_FROZEN_OCEAN_BIOME = LocationCheckLootCondition.builder(LocationPredicate.Builder.create().biome(BiomeKeys.DEEP_FROZEN_OCEAN));

    public static void modify() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            LootPool.Builder poolBuilder = LootPool.builder();

            if (LootTables.END_CITY_TREASURE_CHEST.equals(id)) {
                LootPool.Builder poolBuilderDragonGem = LootPool.builder();
                LootPool.Builder poolBuilderUpgrade = LootPool.builder();

                poolBuilderDragonGem
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.25f))
                        .with(ItemEntry.builder(ModItems.DRAGON_GEM))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f)));
                tableBuilder.pool(poolBuilderDragonGem.build());
                poolBuilderUpgrade
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.1f))
                        .with(ItemEntry.builder(ModItems.HIHIIROKANE_UPGRADE_SMITHING_TEMPLATE))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f)));
                tableBuilder.pool(poolBuilderUpgrade.build());
            }

            for (Map.Entry<RegistryKey<Block>, Block> entry : Registries.BLOCK.getEntrySet()) {
                Block block = entry.getValue();
                if (entry.getKey().getValue().toString().endsWith("leaves") && block.getLootTableId().equals(id)) {
                    poolBuilder
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.1f))
                            .with(ItemEntry.builder(ModItems.HEAVENLY_PEACH))
                            .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(ModItems.HISOU_SWORD)))
                            .conditionally(LocationCheckLootCondition.builder(LocationPredicate.Builder.create().y(NumberRange.FloatRange.atLeast(PEACH_HEIGHT))));
                    tableBuilder.pool(poolBuilder.build());
                    break;
                }
            }

            // "Global" extra loot
            for (Identifier matchingId : EXTRA_LOOT_TABLES) {
                if (matchingId.equals(id)) {
                    tableBuilder.pool(EXTRA_LOOT_POOL);
                    break;
                }
            }

            // Fishing
            if (LootTables.FISHING_FISH_GAMEPLAY.equals(id)) {
                tableBuilder.modifyPools(builder -> builder.with(ItemEntry.builder(ModItems.LAMPREY).weight(12)));
            }
            if (LootTables.FISHING_TREASURE_GAMEPLAY.equals(id)) {
                tableBuilder.modifyPools(builder -> builder.with(ItemEntry.builder(ModItems.LIFE_FRAGMENT)));
            }

            // Pattern templates
            for (Identifier matchingId : SPREAD_PATTERN_STRUCTURES) {
                if (matchingId.equals(id)) {
                    LootPool.Builder poolBuilderTemplate = LootPool.builder();
                    LootPool.Builder poolBuilderIce = LootPool.builder();

                    poolBuilderTemplate
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(TEMPLATE_CHANCE_ALT))
                            .with(ItemEntry.builder(ModItems.SPREAD_PATTERN_TEMPLATE));
                    tableBuilder.pool(poolBuilderTemplate.build());
                    // Enchanted Ice in iceberg shipwrecks
                    poolBuilderIce
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(ENCHANTED_ICE_CHANCE))
                            .conditionally(NEEDS_FROZEN_OCEAN_BIOME.or(NEEDS_DEEP_FROZEN_OCEAN_BIOME))
                            .with(ItemEntry.builder(ModItems.ENCHANTED_ICE));
                    tableBuilder.pool(poolBuilderIce.build());
                    break;
                }
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
                        .with(ItemEntry.builder(ModItems.ARC_PATTERN_TEMPLATE));
                tableBuilder.pool(poolBuilder.build());
            }
            if (LootTables.ABANDONED_MINESHAFT_CHEST.equals(id)) {
                poolBuilder
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(TEMPLATE_CHANCE))
                        .with(ItemEntry.builder(ModItems.DOUBLE_PATTERN_TEMPLATE));
                tableBuilder.pool(poolBuilder.build());
            }
            for (Identifier matchingId : TRIPLE_PATTERN_STRUCTURES) {
                if (matchingId.equals(id)) {
                    poolBuilder
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(TEMPLATE_CHANCE_ALT))
                            .with(ItemEntry.builder(ModItems.TRIPLE_PATTERN_TEMPLATE));
                    tableBuilder.pool(poolBuilder.build());
                    break;
                }
            }

            if (LootTables.IGLOO_CHEST_CHEST.equals(id)) {
                poolBuilder
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(ENCHANTED_ICE_CHANCE))
                        .with(ItemEntry.builder(ModItems.ENCHANTED_ICE));
                tableBuilder.pool(poolBuilder.build());
            }
            if (LootTables.ANCIENT_CITY_ICE_BOX_CHEST.equals(id)) {
                poolBuilder
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(ENCHANTED_ICE_CHANCE))
                        .with(ItemEntry.builder(ModItems.ENCHANTED_ICE));
                tableBuilder.pool(poolBuilder.build());
            }
        });
    }
}
