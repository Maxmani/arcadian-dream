/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.entity.custom.OrbBulletEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities {
    public static final EntityType<OrbBulletEntity> ORB_BULLET = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(ArcadianDream.MOD_ID, "orb_bullet"),
            FabricEntityTypeBuilder.<OrbBulletEntity>create(SpawnGroup.MISC, OrbBulletEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build()
    );

    public static void register() {
        ArcadianDream.LOGGER.debug("Registering entities for " + ArcadianDream.MOD_ID);
    }
}
