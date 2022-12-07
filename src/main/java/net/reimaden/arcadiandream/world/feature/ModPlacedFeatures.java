package net.reimaden.arcadiandream.world.feature;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {

    // Dragon Gem Ores
    public static final RegistryEntry<PlacedFeature> DRAGON_GEM_ORE_PLACED = PlacedFeatures.register("dragon_gem_ore_placed",
            ModConfiguredFeatures.DRAGON_GEM_ORE, modifiersWithCount(4,
                    HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-80), YOffset.aboveBottom(80))));
    public static final RegistryEntry<PlacedFeature> END_DRAGON_GEM_ORE_PLACED = PlacedFeatures.register("end_dragon_gem_ore_placed",
            ModConfiguredFeatures.END_DRAGON_GEM_ORE, modifiersWithCount(3,
                    HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(80))));

    // Makaite Ores
    public static final RegistryEntry<PlacedFeature> MAKAITE_ORE_PLACED = PlacedFeatures.register("makaite_ore_placed",
            ModConfiguredFeatures.MAKAITE_ORE, modifiersWithCount(6,
                    PlacedFeatures.TEN_ABOVE_AND_BELOW_RANGE));

    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
    }

    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacementModifier.of(count), heightModifier);
    }

    private static List<PlacementModifier> modifiersWithRarity(int chance, PlacementModifier heightModifier) {
        return modifiers(RarityFilterPlacementModifier.of(chance), heightModifier);
    }
}
