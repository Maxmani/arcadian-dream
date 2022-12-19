/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.datagen;

import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.reimaden.arcadiandream.block.ModBlocks;
import net.reimaden.arcadiandream.world.ModWorldGen;

import java.util.List;

public class ModWorldGenGenerator {

    private ModWorldGenGenerator() {}

    public static void configuredFeatures(Registerable<ConfiguredFeature<?, ?>> registry) {
        registry.register(ModWorldGen.OVERWORLD_ORE_DRAGON_GEM_CF, createOverworldDragonGemOreConfiguredFeature());
        registry.register(ModWorldGen.END_ORE_DRAGON_GEM_CF, createEndDragonGemOreConfiguredFeature());
        registry.register(ModWorldGen.ORE_MAKAITE_CF, createMakaiteOreConfiguredFeature());
    }

    private static ConfiguredFeature<?, ?> createOverworldDragonGemOreConfiguredFeature() {
        TagMatchRuleTest stoneOres = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        TagMatchRuleTest deepslateOres = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreFeatureConfig.Target> overworldDragonGemOres = List.of(
                OreFeatureConfig.createTarget(stoneOres, ModBlocks.DRAGON_GEM_ORE.getDefaultState()),
                OreFeatureConfig.createTarget(deepslateOres, ModBlocks.DEEPSLATE_DRAGON_GEM_ORE.getDefaultState()));

        return new ConfiguredFeature<>(Feature.ORE, new OreFeatureConfig(overworldDragonGemOres, 5, 0.5f));
    }

    private static ConfiguredFeature<?, ?> createEndDragonGemOreConfiguredFeature() {
        BlockMatchRuleTest endStone = new BlockMatchRuleTest(Blocks.END_STONE);

        return new ConfiguredFeature<>(Feature.ORE, new OreFeatureConfig(endStone,
                ModBlocks.END_STONE_DRAGON_GEM_ORE.getDefaultState(), 4, 0.5f));
    }

    private static ConfiguredFeature<?, ?> createMakaiteOreConfiguredFeature() {
        BlockMatchRuleTest netherrack = new BlockMatchRuleTest(Blocks.NETHERRACK);

        return new ConfiguredFeature<>(Feature.ORE, new OreFeatureConfig(netherrack,
                ModBlocks.MAKAITE_ORE.getDefaultState(), 8, 0.25f));
    }

    public static void placedFeatures(Registerable<PlacedFeature> registry) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> configuredFeatureLookup =
                registry.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        registry.register(ModWorldGen.OVERWORLD_ORE_DRAGON_GEM_PF, createOverworldDragonGemOrePlacedFeature(configuredFeatureLookup));
        registry.register(ModWorldGen.END_ORE_DRAGON_GEM_PF, createEndDragonGemOrePlacedFeature(configuredFeatureLookup));
        registry.register(ModWorldGen.ORE_MAKAITE_PF, createMakaiteOrePlacedFeature(configuredFeatureLookup));
    }

    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
    }

    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return ModWorldGenGenerator.modifiers(CountPlacementModifier.of(count), heightModifier);
    }

    @SuppressWarnings("unused")
    private static List<PlacementModifier> modifiersWithRarity(int chance, PlacementModifier heightModifier) {
        return ModWorldGenGenerator.modifiers(RarityFilterPlacementModifier.of(chance), heightModifier);
    }

    private static PlacedFeature createOverworldDragonGemOrePlacedFeature(RegistryEntryLookup<ConfiguredFeature<?, ?>> configuredFeatureLookup) {
        return new PlacedFeature(
                configuredFeatureLookup.getOrThrow(ModWorldGen.OVERWORLD_ORE_DRAGON_GEM_CF),
                modifiersWithCount(4, HeightRangePlacementModifier.trapezoid(
                        YOffset.aboveBottom(-80),
                        YOffset.aboveBottom(80))));
    }
    private static PlacedFeature createEndDragonGemOrePlacedFeature(RegistryEntryLookup<ConfiguredFeature<?, ?>> configuredFeatureLookup) {
        return new PlacedFeature(
                configuredFeatureLookup.getOrThrow(ModWorldGen.END_ORE_DRAGON_GEM_CF),
                modifiersWithCount(3, HeightRangePlacementModifier.uniform(
                        YOffset.getBottom(),
                        YOffset.fixed(80))));
    }
    private static PlacedFeature createMakaiteOrePlacedFeature(RegistryEntryLookup<ConfiguredFeature<?, ?>> configuredFeatureLookup) {
        return new PlacedFeature(
                configuredFeatureLookup.getOrThrow(ModWorldGen.ORE_MAKAITE_CF),
                modifiersWithCount(6, PlacedFeatures.TEN_ABOVE_AND_BELOW_RANGE));
    }
}
