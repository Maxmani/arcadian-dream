package net.reimaden.arcadiandream.world.structure;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.chunk.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.gen.chunk.placement.SpreadType;
import net.minecraft.world.gen.structure.Structure;
import net.reimaden.arcadiandream.ArcadianDream;

public class ModStructureSets {

    public static final RegistryKey<StructureSet> ABANDONED_SHRINES = registerKey("abandoned_shrines");

    public static void bootstrap(Registerable<StructureSet> context) {
        RegistryEntryLookup<Structure> structureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.STRUCTURE);
        // RegistryEntryLookup<Biome> biomeRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.BIOME);

        register(context, ABANDONED_SHRINES, new StructureSet(structureRegistryEntryLookup.getOrThrow(ModStructures.ABANDONED_SHRINE),
                new RandomSpreadStructurePlacement(75, 32, SpreadType.LINEAR, 478423525)));
    }

    @SuppressWarnings("SameParameterValue")
    private static RegistryKey<StructureSet> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.STRUCTURE_SET, new Identifier(ArcadianDream.MOD_ID, name));
    }

    @SuppressWarnings("SameParameterValue")
    private static void register(Registerable<StructureSet> context, RegistryKey<StructureSet> key, StructureSet structureSet) {
        context.register(key, structureSet);
    }
}
