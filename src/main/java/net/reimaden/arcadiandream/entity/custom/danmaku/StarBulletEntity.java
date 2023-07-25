/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.custom.danmaku;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.damage.ModDamageSources;
import net.reimaden.arcadiandream.entity.ModEntities;
import net.reimaden.arcadiandream.item.ModItems;

public class StarBulletEntity extends BaseBulletEntity {

    private final boolean rotationAngle = random.nextBoolean();

    public StarBulletEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public StarBulletEntity(World world, LivingEntity owner) {
        super(ModEntities.STAR_BULLET, owner, world);
    }

    public StarBulletEntity(World world, double x, double y, double z) {
        super(ModEntities.STAR_BULLET, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.STAR_SHOT;
    }

    @Override
    protected void applyDamage(Entity target, Entity owner) {
        target.damage(ModDamageSources.danmakuSharp(getWorld(), this, owner), getPower() * ArcadianDream.CONFIG.danmakuDamageMultiplier());
        if (isIcy() && target.canFreeze()) {
            applyFreeze(target);
        }
    }

    public boolean getRotationAngle() {
        return rotationAngle;
    }
}
