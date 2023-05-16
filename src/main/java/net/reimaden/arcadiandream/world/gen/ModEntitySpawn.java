/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;
import net.reimaden.arcadiandream.entity.ModEntities;
import net.reimaden.arcadiandream.entity.custom.mob.FairyEntity;
import net.reimaden.arcadiandream.entity.custom.mob.IceFairyEntity;
import net.reimaden.arcadiandream.entity.custom.mob.SunflowerFairyEntity;

public class ModEntitySpawn {

    public static final int FAIRY_WEIGHT = 80;

    public static void addEntitySpawn() {
        modifyBiomes();
        restrictSpawns();
    }

    private static void modifyBiomes() {
        BiomeModifications.addSpawn(BiomeSelectors.tag(ConventionalBiomeTags.CLIMATE_TEMPERATE), SpawnGroup.MONSTER,
                ModEntities.FAIRY, FAIRY_WEIGHT, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.tag(ConventionalBiomeTags.CLIMATE_TEMPERATE), SpawnGroup.MONSTER,
                ModEntities.SUNFLOWER_FAIRY, FAIRY_WEIGHT / 4, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SUNFLOWER_PLAINS), SpawnGroup.MONSTER,
                ModEntities.SUNFLOWER_FAIRY, (int) (FAIRY_WEIGHT * 1.25f), 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.SPAWNS_SNOW_FOXES), SpawnGroup.MONSTER,
                ModEntities.ICE_FAIRY, FAIRY_WEIGHT / 4, 1, 2);
    }

    private static void restrictSpawns() {
        SpawnRestriction.register(ModEntities.FAIRY, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FairyEntity::canSpawn);
        SpawnRestriction.register(ModEntities.SUNFLOWER_FAIRY, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SunflowerFairyEntity::canSpawn);
        SpawnRestriction.register(ModEntities.ICE_FAIRY, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, IceFairyEntity::canSpawn);
    }
}
