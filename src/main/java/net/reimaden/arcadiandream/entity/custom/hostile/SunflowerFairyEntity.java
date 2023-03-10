/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.custom.hostile;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.entity.ai.DanmakuGoal;
import net.reimaden.arcadiandream.entity.custom.danmaku.BaseBulletEntity;
import net.reimaden.arcadiandream.item.custom.danmaku.MobBulletPatterns;
import org.jetbrains.annotations.NotNull;

public class SunflowerFairyEntity extends BaseFairyEntity {

    private final AttackPatterns patterns;
    private final int attackType;

    public SunflowerFairyEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = STRONG_MONSTER_XP;
        this.patterns = new AttackPatterns();
        this.attackType = getRandom().nextInt(1);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return BaseFairyEntity.setAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D);
    }

    @Override
    protected void initGoals() {
        goalSelector.add(0, new SwimGoal(this));
        goalSelector.add(1, new DanmakuGoal(this, 2.0, super.getAttackCooldown(), 10.0F));
        goalSelector.add(2, new FlyGoal(this, 1.0));
        goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        goalSelector.add(4, new LookAtEntityGoal(this, MobEntity.class, 6.0F));
        goalSelector.add(5, new LookAroundGoal(this));

        targetSelector.add(1, new RevengeGoal(this, BaseFairyEntity.class));
        targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true, false));
        targetSelector.add(3, new UniversalAngerGoal<>(this, false));
    }

    @Override
    public int getAttackCooldown() {
        return super.getAttackCooldown();
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        //noinspection SwitchStatementWithTooFewBranches
        switch (getAttackType()) {
            case 0 -> patterns.test(this, target, world);
            default -> throw new IllegalStateException("Unexpected value: " + getAttackType());
        }

        super.attack(target, pullProgress);
    }

    @Override
    public void tickAngerLogic(ServerWorld world, boolean angerPersistent) {
        super.tickAngerLogic(world, angerPersistent);
        angerNearbyFairies();
    }

    private void angerNearbyFairies() {
        LivingEntity attacker = getAttacker();
        if (attacker == null) return;

        double d = getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
        Box box = Box.from(getPos()).expand(d, 10.0, d);
        world.getEntitiesByClass(BaseFairyEntity.class, box, EntityPredicates.EXCEPT_SPECTATOR).stream()
                .filter(fairy -> fairy != this)
                .filter(fairy -> fairy.getTarget() == null)
                .filter(fairy -> !fairy.isTeammate(attacker))
                .forEach(fairy -> {
                    fairy.setAngryAt(attacker.getUuid());
                    fairy.chooseRandomAngerTime();
                });
    }

    @Override
    public float getSoundPitch() {
        return super.getSoundPitch() * 0.9f;
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 1.3f;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
    }

    public int getAttackType() {
        return attackType;
    }

    @Override
    public BaseBulletEntity availableBullets(World world, LivingEntity user) {
        return super.availableBullets(world, user);
    }

    private class AttackPatterns implements MobBulletPatterns {

        private final int randomDensity = getRandom().nextInt(6) + 5;

        // TODO: Add more patterns

        private void test(LivingEntity fairy, LivingEntity target, World world) {
            createSpread(world, fairy, target, randomDensity, 0.5f, 2, 5, 50, getBulletColor());
        }

        @Override
        public @NotNull BaseBulletEntity getBullet(World world, LivingEntity user) {
            return SunflowerFairyEntity.this.availableBullets(world, user);
        }
    }
}
