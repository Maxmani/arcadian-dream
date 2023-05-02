/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.custom.danmaku;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.entity.ModEntities;
import net.reimaden.arcadiandream.item.ModItems;

public class PelletBulletEntity extends BaseBulletEntity {

    public PelletBulletEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public PelletBulletEntity(World world, LivingEntity owner) {
        super(ModEntities.PELLET_BULLET, owner, world);
    }

    public PelletBulletEntity(World world, double x, double y, double z) {
        super(ModEntities.PELLET_BULLET, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.PELLET_SHOT;
    }
}
