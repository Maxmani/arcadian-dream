/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.world.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;

public class ModStructurePools {

    public static final RegistryKey<StructurePool> ABANDONED_SHRINE = registerKey("abandoned_shrine/start_pool");

    public static void bootstrap(Registerable<StructurePool> context) {
        RegistryEntryLookup<StructurePool> poolRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.TEMPLATE_POOL);
        RegistryEntry.Reference<StructurePool> emptyPool = poolRegistryEntryLookup.getOrThrow(StructurePools.EMPTY);
        RegistryEntryLookup<StructureProcessorList> processorListRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.PROCESSOR_LIST);

        register(context, ABANDONED_SHRINE, new StructurePool(
                emptyPool, ImmutableList.of(Pair.of(StructurePoolElement.ofProcessedSingle(
                        new Identifier(ArcadianDream.MOD_ID, "abandoned_shrine/abandoned_shrine").toString(),
                processorListRegistryEntryLookup.getOrThrow(ModStructureProcessorLists.SHRINE_DEGRADATION)), 1)), StructurePool.Projection.RIGID
        ));
    }

    @SuppressWarnings("SameParameterValue")
    private static RegistryKey<StructurePool> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.TEMPLATE_POOL, new Identifier(ArcadianDream.MOD_ID, name));
    }

    @SuppressWarnings("SameParameterValue")
    private static void register(Registerable<StructurePool> context, RegistryKey<StructurePool> key, StructurePool structurePool) {
        context.register(key, structurePool);
    }
}
