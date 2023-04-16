/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.world.feature;

import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.block.ModBlocks;

import java.util.List;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> DRAGON_GEM_ORE_KEY = registerKey("dragon_gem_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> END_DRAGON_GEM_ORE_KEY = registerKey("end_dragon_gem_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MAKAITE_ORE_KEY = registerKey("makaite_ore");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherReplaceables = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
        RuleTest endStoneReplaceables = new BlockMatchRuleTest(Blocks.END_STONE);

        List<OreFeatureConfig.Target> overworldDragonGemOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.DRAGON_GEM_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.DEEPSLATE_DRAGON_GEM_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> endDragonGemOres =
                List.of(OreFeatureConfig.createTarget(endStoneReplaceables, ModBlocks.END_STONE_DRAGON_GEM_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> makaiteOres =
                List.of(OreFeatureConfig.createTarget(netherReplaceables, ModBlocks.MAKAITE_ORE.getDefaultState()));

        register(context, DRAGON_GEM_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldDragonGemOres, 5, 0.5f));
        register(context, END_DRAGON_GEM_ORE_KEY, Feature.ORE, new OreFeatureConfig(endDragonGemOres, 4, 0.5f));
        register(context, MAKAITE_ORE_KEY, Feature.ORE, new OreFeatureConfig(makaiteOres, 8, 0.25f));
    }

    private static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(ArcadianDream.MOD_ID, name));
    }

    @SuppressWarnings("SameParameterValue")
    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
