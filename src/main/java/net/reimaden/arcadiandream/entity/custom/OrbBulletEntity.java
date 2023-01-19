/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.custom;

import net.reimaden.arcadiandream.entity.ModEntities;
import net.reimaden.arcadiandream.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class OrbBulletEntity extends BaseBulletEntity {

    public OrbBulletEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public OrbBulletEntity(World world, LivingEntity owner) {
        super(ModEntities.ORB_BULLET, owner, world);
    }

    public OrbBulletEntity(World world, double x, double y, double z) {
        super(ModEntities.ORB_BULLET, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.ORB_BULLET;
    }
}
