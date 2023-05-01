/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.custom.danmaku;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.entity.ModEntities;
import net.reimaden.arcadiandream.item.ModItems;

public class KunaiBulletEntity extends BaseBulletEntity {

    public KunaiBulletEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public KunaiBulletEntity(World world, LivingEntity owner) {
        super(ModEntities.KUNAI_BULLET, owner, world);
    }

    public KunaiBulletEntity(World world, double x, double y, double z) {
        super(ModEntities.KUNAI_BULLET, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.KUNAI_SHOT;
    }

    @Override
    protected void postHit(HitResult.Type type) {
        if (type == HitResult.Type.ENTITY) return;
        super.postHit(type);
    }
}
