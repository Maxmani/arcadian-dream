/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
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

public class BaseBulletEntity extends ThrownItemEntity {

    private int power = 0;
    private int duration = 200;
    private float gravity = 0.0f;

    public BaseBulletEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @SuppressWarnings("DataFlowIssue") // This is never used, so I'll suppress it
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
        if (!world.isClient()) {
            if (age >= getDuration()) {
                kill();
                despawnParticle((ServerWorld) world);
            }
        }
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.damage(ModDamageSources.danmaku(this, getOwner()), getPower());
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);

        if (!world.isClient()) {
            kill();
            bulletEffects((ServerWorld) world);
        }
    }

    private void bulletEffects(ServerWorld serverWorld) {
        despawnParticle(serverWorld);
        serverWorld.playSound(null, getX(), getY(), getZ(),
                ModSounds.ENTITY_DANMAKU_HIT, SoundCategory.NEUTRAL, 1f, getSoundPitch());
    }

    private void despawnParticle(ServerWorld serverWorld) {
        serverWorld.spawnParticles(ModParticles.BULLET_DESPAWN,
                getPos().getX(), getPos().getY() + getHeight() / 2, getPos().getZ(),
                1, 0, 0, 0, 0);
    }

    public void cancelParticle(ServerWorld serverWorld) {
        serverWorld.spawnParticles(ModParticles.BULLET_CANCEL,
                getPos().getX(), getPos().getY() + getHeight() / 2, getPos().getZ(),
                1, 0, 0, 0, 0);
    }

    private float getPower() {
        if (getStack().hasNbt()) {
            return getStack().getOrCreateNbt().getFloat("power");
        } else {
            return power;
        }
    }

    private int getDuration() {
        if (getStack().hasNbt()) {
            return getStack().getOrCreateNbt().getInt("duration");
        } else {
            return duration;
        }
    }

    @Override
    protected float getGravity() {
        if (getStack().hasNbt()) {
            return getStack().getOrCreateNbt().getFloat("gravity");
        } else {
            return gravity;
        }
    }

    public float getSoundPitch() {
        return 1f;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }
}
