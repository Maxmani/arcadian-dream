/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.entity.custom.mob.FairyEntity;
import net.reimaden.arcadiandream.entity.custom.danmaku.AmuletBulletEntity;
import net.reimaden.arcadiandream.entity.custom.danmaku.BubbleBulletEntity;
import net.reimaden.arcadiandream.entity.custom.danmaku.CircleBulletEntity;
import net.reimaden.arcadiandream.entity.custom.danmaku.StarBulletEntity;
import net.reimaden.arcadiandream.entity.custom.mob.SunflowerFairyEntity;

public class ModEntities {

    private static final int DANMAKU_RANGE = 4;
    private static final int DANMAKU_RATE = 10;

    // Danmaku
    public static final EntityType<CircleBulletEntity> CIRCLE_BULLET = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(ArcadianDream.MOD_ID, "circle_bullet"),
            FabricEntityTypeBuilder.<CircleBulletEntity>create(SpawnGroup.MISC, CircleBulletEntity::new)
                    .dimensions(EntityDimensions.changing(0.25f, 0.25f))
                    .trackRangeChunks(DANMAKU_RANGE).trackedUpdateRate(DANMAKU_RATE)
                    .build()
    );
    public static final EntityType<BubbleBulletEntity> BUBBLE_BULLET = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(ArcadianDream.MOD_ID, "bubble_bullet"),
            FabricEntityTypeBuilder.<BubbleBulletEntity>create(SpawnGroup.MISC, BubbleBulletEntity::new)
                    .dimensions(EntityDimensions.changing(0.75f, 0.75f))
                    .trackRangeChunks(DANMAKU_RANGE).trackedUpdateRate(DANMAKU_RATE)
                    .build()
    );
    public static final EntityType<AmuletBulletEntity> AMULET_BULLET = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(ArcadianDream.MOD_ID, "amulet_bullet"),
            FabricEntityTypeBuilder.<AmuletBulletEntity>create(SpawnGroup.MISC, AmuletBulletEntity::new)
                    .dimensions(EntityDimensions.changing(0.25f, 0.25f))
                    .trackRangeChunks(DANMAKU_RANGE).trackedUpdateRate(DANMAKU_RATE)
                    .build()
    );
    public static final EntityType<StarBulletEntity> STAR_BULLET = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(ArcadianDream.MOD_ID, "star_bullet"),
            FabricEntityTypeBuilder.<StarBulletEntity>create(SpawnGroup.MISC, StarBulletEntity::new)
                    .dimensions(EntityDimensions.changing(0.50f, 0.50f))
                    .trackRangeChunks(DANMAKU_RANGE).trackedUpdateRate(DANMAKU_RATE)
                    .build()
    );

    // Mobs
    public static final EntityType<FairyEntity> FAIRY = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(ArcadianDream.MOD_ID, "fairy"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, FairyEntity::new)
                    .dimensions(EntityDimensions.fixed(0.55f, 1.55f))
                    .build()
    );
    public static final EntityType<SunflowerFairyEntity> SUNFLOWER_FAIRY = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(ArcadianDream.MOD_ID, "sunflower_fairy"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SunflowerFairyEntity::new)
                    .dimensions(EntityDimensions.fixed(0.7f, 1.7f))
                    .build()
    );

    public static void register() {
        ArcadianDream.LOGGER.debug("Registering entities for " + ArcadianDream.MOD_ID);
    }

    @SuppressWarnings("InstantiationOfUtilityClass")
    public static class EntityGroups {
        // Dunno if there's a better way to do this
        public static final EntityGroup YOUKAI = new EntityGroup();
    }
}
