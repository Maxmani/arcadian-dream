/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.world;

import net.fabricmc.fabric.api.biome.v1.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.reimaden.arcadiandream.ArcadianDream;

import java.util.function.BiConsumer;

public class ModWorldGen {

    public static final RegistryKey<ConfiguredFeature<?, ?>> OVERWORLD_ORE_DRAGON_GEM_CF = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE,
            new Identifier(ArcadianDream.MOD_ID, "overworld_ore_dragon_gem"));
    public static final RegistryKey<ConfiguredFeature<?, ?>> END_ORE_DRAGON_GEM_CF = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE,
            new Identifier(ArcadianDream.MOD_ID, "end_ore_dragon_gem"));
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_MAKAITE_CF = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE,
            new Identifier(ArcadianDream.MOD_ID, "ore_makaite"));

    public static final RegistryKey<PlacedFeature> OVERWORLD_ORE_DRAGON_GEM_PF = RegistryKey.of(RegistryKeys.PLACED_FEATURE,
            new Identifier(ArcadianDream.MOD_ID, "overworld_ore_dragon_gem"));
    public static final RegistryKey<PlacedFeature> END_ORE_DRAGON_GEM_PF = RegistryKey.of(RegistryKeys.PLACED_FEATURE,
            new Identifier(ArcadianDream.MOD_ID, "end_ore_dragon_gem"));
    public static final RegistryKey<PlacedFeature> ORE_MAKAITE_PF = RegistryKey.of(RegistryKeys.PLACED_FEATURE,
            new Identifier(ArcadianDream.MOD_ID, "ore_makaite"));

    private static BiConsumer<BiomeSelectionContext, BiomeModificationContext> overworldOreModifier() {
        return (biomeSelectionContext, biomeModificationContext) ->
                biomeModificationContext.getGenerationSettings().addFeature(
                        GenerationStep.Feature.UNDERGROUND_ORES,
                        OVERWORLD_ORE_DRAGON_GEM_PF
                );
    }

    private static BiConsumer<BiomeSelectionContext, BiomeModificationContext> endOreModifier() {
        return (biomeSelectionContext, biomeModificationContext) ->
                biomeModificationContext.getGenerationSettings().addFeature(
                        GenerationStep.Feature.UNDERGROUND_ORES,
                        END_ORE_DRAGON_GEM_PF
                );
    }

    private static BiConsumer<BiomeSelectionContext, BiomeModificationContext> netherOreModifier() {
        return (biomeSelectionContext, biomeModificationContext) ->
                biomeModificationContext.getGenerationSettings().addFeature(
                        GenerationStep.Feature.UNDERGROUND_ORES,
                        ORE_MAKAITE_PF
                );
    }

    public static void register() {
        BiomeModifications.create(new Identifier(ArcadianDream.MOD_ID, "features"))
                .add(ModificationPhase.ADDITIONS,
                        BiomeSelectors.foundInOverworld(),
                        overworldOreModifier())
                .add(ModificationPhase.ADDITIONS,
                        BiomeSelectors.foundInTheEnd(),
                        endOreModifier())
                .add(ModificationPhase.ADDITIONS,
                        BiomeSelectors.foundInTheNether(),
                        netherOreModifier());
    }
}
