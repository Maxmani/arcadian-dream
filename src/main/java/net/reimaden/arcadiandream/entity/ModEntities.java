/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.entity.custom.CircleBulletEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.entity.custom.FairyEntity;

public class ModEntities {

    private static final int DANMAKU_RANGE = 4;
    private static final int DANMAKU_RATE = 10;

    public static final EntityType<CircleBulletEntity> CIRCLE_BULLET = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(ArcadianDream.MOD_ID, "circle_bullet"),
            FabricEntityTypeBuilder.<CircleBulletEntity>create(SpawnGroup.MISC, CircleBulletEntity::new)
                    .dimensions(EntityDimensions.changing(0.25f, 0.25f))
                    .trackRangeChunks(DANMAKU_RANGE).trackedUpdateRate(DANMAKU_RATE)
                    .build()
    );
    public static final EntityType<FairyEntity> FAIRY = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(ArcadianDream.MOD_ID, "fairy"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, FairyEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 1.5f))
                    .build()
    );

    public static void register() {
        ArcadianDream.LOGGER.debug("Registering entities for " + ArcadianDream.MOD_ID);
    }
}
