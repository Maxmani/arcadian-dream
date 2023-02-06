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

public class CircleBulletEntity extends BaseBulletEntity {

    public CircleBulletEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public CircleBulletEntity(World world, LivingEntity owner) {
        super(ModEntities.CIRCLE_BULLET, owner, world);
    }

    public CircleBulletEntity(World world, double x, double y, double z) {
        super(ModEntities.CIRCLE_BULLET, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.CIRCLE_SHOT;
    }
}
