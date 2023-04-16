package net.reimaden.arcadiandream.world.structure;

import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureSpawns;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureTerrainAdaptation;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.heightprovider.ConstantHeightProvider;
import net.minecraft.world.gen.structure.JigsawStructure;
import net.minecraft.world.gen.structure.Structure;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.util.ModTags;

import java.util.Map;

public class ModStructures {

    public static final RegistryKey<Structure> ABANDONED_SHRINE = registerKey("abandoned_shrine");

    public static void bootstrap(Registerable<Structure> context) {
        RegistryEntryLookup<Biome> biomeRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.BIOME);
        RegistryEntryLookup<StructurePool> poolRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.TEMPLATE_POOL);

        register(context, ABANDONED_SHRINE, new JigsawStructure(createConfig(biomeRegistryEntryLookup.getOrThrow(ModTags.Biomes.ABANDONED_SHRINE_HAS_STRUCTURE),
                StructureTerrainAdaptation.BEARD_THIN), poolRegistryEntryLookup.getOrThrow(ModStructurePools.ABANDONED_SHRINE),
                2, ConstantHeightProvider.create(YOffset.fixed(-1)), false, Heightmap.Type.WORLD_SURFACE_WG));
    }

    private static Structure.Config createConfig(RegistryEntryList<Biome> biomes, Map<SpawnGroup, StructureSpawns> spawns, GenerationStep.Feature featureStep, StructureTerrainAdaptation terrainAdaptation) {
        return new Structure.Config(biomes, spawns, featureStep, terrainAdaptation);
    }

    @SuppressWarnings("unused")
    private static Structure.Config createConfig(RegistryEntryList<Biome> biomes, GenerationStep.Feature featureStep, StructureTerrainAdaptation terrainAdaptation) {
        return createConfig(biomes, Map.of(), featureStep, terrainAdaptation);
    }

    @SuppressWarnings("SameParameterValue")
    private static Structure.Config createConfig(RegistryEntryList<Biome> biomes, StructureTerrainAdaptation terrainAdaptation) {
        return createConfig(biomes, Map.of(), GenerationStep.Feature.SURFACE_STRUCTURES, terrainAdaptation);
    }

    @SuppressWarnings("SameParameterValue")
    private static RegistryKey<Structure> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.STRUCTURE, new Identifier(ArcadianDream.MOD_ID, name));
    }

    @SuppressWarnings("SameParameterValue")
    private static void register(Registerable<Structure> context, RegistryKey<Structure> key, Structure structure) {
        context.register(key, structure);
    }
}
