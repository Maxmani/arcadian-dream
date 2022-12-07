package net.reimaden.arcadiandream.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.reimaden.arcadiandream.world.feature.ModPlacedFeatures;
import net.minecraft.world.gen.GenerationStep;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class ModOreGeneration {

    public static void generate() {
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.DRAGON_GEM_ORE_PLACED.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.foundInTheEnd(),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.END_DRAGON_GEM_ORE_PLACED.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.MAKAITE_ORE_PLACED.getKey().get());
    }
}
