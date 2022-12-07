package net.reimaden.arcadiandream.world.feature;

import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;

import java.util.List;

public class ModConfiguredFeatures {

    // Dragon Gem Ores
    public static final List<OreFeatureConfig.Target> OVERWORLD_DRAGON_GEM_ORES = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, ModBlocks.DRAGON_GEM_ORE.getDefaultState()),
            OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_DRAGON_GEM_ORE.getDefaultState()));
    public static final List<OreFeatureConfig.Target> END_DRAGON_GEM_ORES = List.of(
            OreFeatureConfig.createTarget(new BlockMatchRuleTest(Blocks.END_STONE), ModBlocks.END_STONE_DRAGON_GEM_ORE.getDefaultState()));

    // Makaite Ores
    public static final List<OreFeatureConfig.Target> MAKAITE_ORES = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.BASE_STONE_NETHER, ModBlocks.MAKAITE_ORE.getDefaultState()));

    // Dragon Gem Ores
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> DRAGON_GEM_ORE =
            ConfiguredFeatures.register("dragon_gem_ore", Feature.ORE, new OreFeatureConfig(OVERWORLD_DRAGON_GEM_ORES, 5, 0.5f));
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> END_DRAGON_GEM_ORE =
            ConfiguredFeatures.register("end_dragon_gem_ore", Feature.ORE, new OreFeatureConfig(END_DRAGON_GEM_ORES, 4, 0.5f));

    // Makaite Ores
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> MAKAITE_ORE =
            ConfiguredFeatures.register("makaite_ore", Feature.ORE, new OreFeatureConfig(MAKAITE_ORES, 8, 0.25f));

    public static void register() {
        ArcadianDream.LOGGER.debug("Registering features for " + ArcadianDream.MOD_ID);
    }
}
