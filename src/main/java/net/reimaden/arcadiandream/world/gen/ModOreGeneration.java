/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;
import net.reimaden.arcadiandream.world.feature.ModPlacedFeatures;

public class ModOreGeneration {

    public static void generate() {
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                ModPlacedFeatures.DRAGON_GEM_ORE_PLACED_KEY
        );
        BiomeModifications.addFeature(BiomeSelectors.foundInTheEnd(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                ModPlacedFeatures.END_DRAGON_GEM_ORE_PLACED_KEY
        );
        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                ModPlacedFeatures.MAKAITE_ORE_PLACED_KEY
        );
    }
}
