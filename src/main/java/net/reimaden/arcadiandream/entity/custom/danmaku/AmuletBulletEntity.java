/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.custom.danmaku;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.damage.ModDamageSources;
import net.reimaden.arcadiandream.entity.ModEntities;
import net.reimaden.arcadiandream.item.ModItems;

public class AmuletBulletEntity extends BaseBulletEntity {

    public AmuletBulletEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public AmuletBulletEntity(World world, LivingEntity owner) {
        super(ModEntities.AMULET_BULLET, owner, world);
    }

    public AmuletBulletEntity(World world, double x, double y, double z) {
        super(ModEntities.AMULET_BULLET, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.AMULET_SHOT;
    }

    @Override
    protected void applyDamage(Entity target, Entity owner) {
        if (target instanceof LivingEntity livingEntity && livingEntity.getGroup() == EntityGroup.UNDEAD) {
            target.damage(ModDamageSources.danmaku(getWorld(), this, owner), getPower() * 2 * ArcadianDream.CONFIG.danmakuDamageMultiplier());
        } else {
            target.damage(ModDamageSources.danmaku(getWorld(), this, owner), getPower() * ArcadianDream.CONFIG.danmakuDamageMultiplier());
        }
        if (isIcy() && target.canFreeze()) {
            applyFreeze(target);
        }
    }
}
