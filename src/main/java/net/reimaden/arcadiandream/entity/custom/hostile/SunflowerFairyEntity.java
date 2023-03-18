/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.custom.hostile;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.*;
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
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.entity.ai.DanmakuGoal;
import net.reimaden.arcadiandream.entity.custom.danmaku.BaseBulletEntity;
import net.reimaden.arcadiandream.entity.custom.danmaku.CircleBulletEntity;
import net.reimaden.arcadiandream.entity.custom.danmaku.StarBulletEntity;
import net.reimaden.arcadiandream.item.custom.danmaku.MobBulletPatterns;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SunflowerFairyEntity extends BaseFairyEntity {

    private final AttackPatterns patterns;
    private byte attackType;

    public SunflowerFairyEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = STRONG_MONSTER_XP;
        this.patterns = new AttackPatterns();
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
        targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public int getAttackCooldown() {
        int cooldown = super.getAttackCooldown();

        switch (getAttackType()) {
            case 0 -> cooldown = 20;
            case 1 -> cooldown = 40;
            case 2 -> cooldown = 60;
            case 3 -> cooldown = 30;
            case 4 -> cooldown = 12;
            case 5 -> cooldown = 35;
        }

        return cooldown + getCooldownOffset();
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        super.attack(target, pullProgress);

        switch (getAttackType()) {
            case 0 -> patterns.burstAttack(this, target, world);
            case 1 -> patterns.spreadAttack(this, target, world);
            case 2 -> patterns.carpetBombAttack(this, world);
            case 3 -> patterns.coneAttack(this, target, world);
            case 4 -> patterns.crossAttack(this, target, world);
            case 5 -> patterns.ringAttack(this, target, world);
            default -> throw new IllegalStateException("Unexpected value: " + getAttackType());
        }
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
        nbt.putByte("AttackType", getAttackType());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        setAttackType(nbt.getByte("AttackType"));
    }

    @Override
    public @Nullable EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        byte attackType = (byte) getRandom().nextInt(6);

        setAttackType(attackType);

        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public byte getAttackType() {
        return attackType;
    }

    private void setAttackType(byte attackType) {
        this.attackType = attackType;
    }

    @Override
    public ImmutableList<BaseBulletEntity> availableBullets(World world, LivingEntity user) {
        return ImmutableList.of(
                new CircleBulletEntity(world, user), new StarBulletEntity(world, user)
        );
    }

    private class AttackPatterns implements MobBulletPatterns {

        private final int randomDensity = getRandom().nextInt(6) + 5;

        // TODO: Add more patterns

        private void burstAttack(LivingEntity fairy, LivingEntity target, World world) {
            createSpread(world, fairy, target, randomDensity, 0.5f, 5, 5, 50, getBulletColor());
        }

        private void spreadAttack(LivingEntity fairy, LivingEntity target, World world) {
            createSpread(world, fairy, target, randomDensity + 5, 0.2f, 180, 4, 20, getBulletColor());
        }

        private void carpetBombAttack(LivingEntity fairy, World world) {
            createRain(world, fairy, 15, 1.2f, 8, 5, 100, getBulletColor(), 0.1f);
        }

        private void coneAttack(LivingEntity fairy, LivingEntity target, World world) {
            createCone(world, fairy, target, 5, 0.5f, 0, 6, 50, getBulletColor());
        }

        private void crossAttack(LivingEntity fairy, LivingEntity target, World world) {
            createRing(world, fairy, target, 4, 0.65f, 0, 3, 40, getBulletColor());
        }

        private void ringAttack(LivingEntity fairy, LivingEntity target, World world) {
            createRing(world, fairy, target, 15, 0.45f, 0, 4, 60, getBulletColor());
        }

        @Override
        public @NotNull BaseBulletEntity getBullet(World world, LivingEntity user) {
            return SunflowerFairyEntity.this.availableBullets(world, user).get(getBulletType());
        }
    }
}
