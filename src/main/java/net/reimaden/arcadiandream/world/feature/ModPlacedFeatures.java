/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.world.feature;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.reimaden.arcadiandream.ArcadianDream;

import java.util.List;

public class ModPlacedFeatures {

    public static final RegistryKey<PlacedFeature> DRAGON_GEM_ORE = registerKey("dragon_gem_ore");
    public static final RegistryKey<PlacedFeature> END_DRAGON_GEM_ORE = registerKey("end_dragon_gem_ore");
    public static final RegistryKey<PlacedFeature> MAKAITE_ORE = registerKey("makaite_ore");
    public static final RegistryKey<PlacedFeature> HIHIIROKANE_ORE = registerKey("hihiirokane_ore");
    public static final RegistryKey<PlacedFeature> HIHIIROKANE_ORE_BURIED = registerKey("hihiirokane_ore_buried");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, DRAGON_GEM_ORE, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.DRAGON_GEM_ORE),
                modifiersWithCount(4, // Veins per chunk
                        HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-80), YOffset.aboveBottom(80))));
        register(context, END_DRAGON_GEM_ORE, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.END_DRAGON_GEM_ORE),
                modifiersWithCount(3, // Veins per chunk
                        HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(80))));
        register(context, MAKAITE_ORE, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.MAKAITE_ORE),
                modifiersWithCount(9, // Veins per chunk
                        PlacedFeatures.TEN_ABOVE_AND_BELOW_RANGE));
        register(context, HIHIIROKANE_ORE, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.HIHIIROKANE_ORE),
                modifiersWithCount(4, // Veins per chunk
                        HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(5))));
        register(context, HIHIIROKANE_ORE_BURIED, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.HIHIIROKANE_ORE_BURIED),
                modifiersWithCount(4, // Veins per chunk
                        HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(5))));
    }

    private static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(ArcadianDream.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
    }
    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacementModifier.of(count), heightModifier);
    }
    @SuppressWarnings("unused")
    private static List<PlacementModifier> modifiersWithRarity(int chance, PlacementModifier heightModifier) {
        return modifiers(RarityFilterPlacementModifier.of(chance), heightModifier);
    }
}
