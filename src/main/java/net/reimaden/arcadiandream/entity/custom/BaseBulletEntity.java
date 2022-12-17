/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.custom;

import net.reimaden.arcadiandream.damage.ModDamageSources;
import net.reimaden.arcadiandream.particle.ModParticles;
import net.reimaden.arcadiandream.sound.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

@SuppressWarnings("ConstantConditions")
public class BaseBulletEntity extends ThrownItemEntity {

    public BaseBulletEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public BaseBulletEntity(World world, LivingEntity owner) {
        super(null, owner, world);
    }

    public BaseBulletEntity(EntityType<? extends ThrownItemEntity> entityType, LivingEntity owner, World world) {
        super(entityType, owner, world);
    }

    public BaseBulletEntity(EntityType<? extends ThrownItemEntity> entityType, double x, double y, double z, World world) {
        super(entityType, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    @Override
    public boolean doesRenderOnFire() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!world.isClient) {
            if (age <= 1) {
                ((ServerWorld) world).spawnParticles(ModParticles.BULLET_SPAWN, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
            } else if (age >= getMaxAge()) {
                this.kill();
                ((ServerWorld) world).spawnParticles(ModParticles.BULLET_DESPAWN, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
            }
        }
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.damage(ModDamageSources.danmaku(this, this.getOwner()), (float)getPower());
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            this.kill();
        }
        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ModParticles.BULLET_DESPAWN, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
            serverWorld.playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.ENTITY_DANMAKU_HIT, SoundCategory.NEUTRAL, 1f, 1f);
        }
    }

    public int getPower() {
        if (getStack().hasNbt()) {
            return this.getStack().getNbt().getInt("power");
        } else {
            return 0;
        }
    }

    public int getMaxAge() {
        if (getStack().hasNbt()) {
            return this.getStack().getNbt().getInt("duration");
        } else {
            return 0;
        }
    }

    @Override
    protected float getGravity() {
        if (getStack().hasNbt()) {
            return this.getStack().getNbt().getFloat("gravity");
        } else {
            return 0.0f;
        }
    }
}
