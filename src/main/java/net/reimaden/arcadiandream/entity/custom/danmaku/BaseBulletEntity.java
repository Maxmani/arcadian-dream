/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.custom.danmaku;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.damage.ModDamageSources;
import net.reimaden.arcadiandream.entity.custom.mob.BaseFairyEntity;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.particle.ModParticles;
import net.reimaden.arcadiandream.sound.ModSounds;
import net.reimaden.arcadiandream.statistic.ModStats;

import java.util.Optional;

public class BaseBulletEntity extends ThrownItemEntity {

    protected BaseBulletEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @SuppressWarnings("DataFlowIssue") // This is never used, so I'll suppress it
    public BaseBulletEntity(World world, LivingEntity owner) {
        super(null, owner, world);
    }

    protected BaseBulletEntity(EntityType<? extends ThrownItemEntity> entityType, LivingEntity owner, World world) {
        super(entityType, owner, world);
    }

    protected BaseBulletEntity(EntityType<? extends ThrownItemEntity> entityType, double x, double y, double z, World world) {
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
        if (!getWorld().isClient()) {
            if (age >= getDuration()) {
                discardBullet();
            }
            if (getOwner() instanceof LivingEntity livingEntity && livingEntity.deathTime >= 19) {
                discardBullet();
            }
        }
        frozenParticles(this, getWorld());
    }

    private static void frozenParticles(BaseBulletEntity entity, World world) {
        if (!entity.isIcy()) return;
        float offset = 2.0f;
        double xOffset = entity.getVelocity().x * offset;
        double yOffset = entity.getVelocity().y * offset;
        double zOffset = entity.getVelocity().z * offset;
        world.addParticle(ParticleTypes.SNOWFLAKE, entity.getX() - xOffset, entity.getY() + entity.getHeight() / 2 - yOffset, entity.getZ() - zOffset, 0, 0, 0);
    }

    private void discardBullet() {
        discard();
        despawnParticle((ServerWorld) getWorld());
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (getWorld().isClient()) return;
        super.onEntityHit(entityHitResult);

        Entity entity = entityHitResult.getEntity();
        Entity owner = getOwner();
        if (entity instanceof BaseFairyEntity && owner instanceof BaseFairyEntity) {
            return;
        }
        applyDamage(entity, owner);
    }

    protected void applyDamage(Entity target, Entity owner) {
        target.damage(ModDamageSources.danmaku(getWorld(), this, owner), getPower() * ArcadianDream.CONFIG.danmakuDamageMultiplier());
        if (isIcy() && target.canFreeze()) {
            applyFreeze(target);
        }
    }

    protected void applyFreeze(Entity target) {
        int frozenTicks = target.getFrozenTicks();
        target.setFrozenTicks((int) Math.min(target.getMinFreezeDamageTicks() * 4, frozenTicks + 10 * getPower()));
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        // Make bullets ignore their owner
        HitResult.Type type = hitResult.getType();
        if (type == HitResult.Type.ENTITY) {
            Entity entity = ((EntityHitResult) hitResult).getEntity();
            if (entity == getOwner()) return;
        }
        super.onCollision(hitResult);

        postHit(type);
    }

    protected void postHit(HitResult.Type type) {
        if (!getWorld().isClient()) {
            kill();
            bulletEffects((ServerWorld) getWorld());
        }
    }

    private void bulletEffects(ServerWorld serverWorld) {
        despawnParticle(serverWorld);
        playSound(ModSounds.ENTITY_DANMAKU_HIT, 0.8f, getSoundPitch(random));
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

    protected float getPower() {
        if (getStack().hasNbt()) {
            return getStack().getOrCreateNbt().getFloat("power");
        } else {
            return 0.0f;
        }
    }

    protected int getDuration() {
        if (getStack().hasNbt()) {
            return getStack().getOrCreateNbt().getInt("duration");
        } else {
            return 200;
        }
    }

    @Override
    protected float getGravity() {
        if (getStack().hasNbt()) {
            return getStack().getOrCreateNbt().getFloat("gravity");
        } else {
            return 0.0f;
        }
    }

    public boolean isIcy() {
        if (getStack().hasNbt()) {
            return getStack().getOrCreateNbt().getBoolean("icy");
        } else {
            return false;
        }
    }

    public static float getSoundPitch(Random random) {
        return 1.0f + (random.nextFloat() - 0.5f) * 0.1f;
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    public boolean canBeHitByProjectile() {
        return false;
    }

    @Override
    public float getTargetingMargin() {
        return (float) getBoundingBox().getXLength() * 1.1f;
    }

    @Override
    public boolean handleAttack(Entity attacker) {
        return !playerHasMagatama((LivingEntity) attacker) || getOwner() == attacker;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (isInvulnerableTo(source)) return false;
        scheduleVelocityUpdate();

        final Entity entity = source.getAttacker();
        if (entity == getOwner()) return false;

        if (!getWorld().isClient()) {
            if (entity instanceof PlayerEntity player && playerHasMagatama(player)
                    && player.getItemCooldownManager().getCooldownProgress(ModItems.MAGATAMA_NECKLACE, 0.0f) == 0) {
                kill();
                cancelParticle((ServerWorld) getWorld());
                player.getItemCooldownManager().set(ModItems.MAGATAMA_NECKLACE, 10);
                player.increaseStat(ModStats.BULLETS_CANCELLED, 1);
                return true;
            }
        }

        return false;
    }

    private static boolean playerHasMagatama(LivingEntity entity) {
        Optional<TrinketComponent> trinketComponent = TrinketsApi.getTrinketComponent(entity);

        return trinketComponent.isPresent() && trinketComponent.get().isEquipped(ModItems.MAGATAMA_NECKLACE);
    }
}
