/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.custom.mob;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.entity.ai.DanmakuGoal;
import net.reimaden.arcadiandream.entity.custom.danmaku.BaseBulletEntity;
import net.reimaden.arcadiandream.entity.custom.danmaku.CircleBulletEntity;
import net.reimaden.arcadiandream.entity.custom.danmaku.PelletBulletEntity;
import net.reimaden.arcadiandream.entity.variant.FairyPersonality;
import net.reimaden.arcadiandream.entity.variant.FairyVariant;
import net.reimaden.arcadiandream.item.custom.danmaku.MobBulletPatterns;
import org.jetbrains.annotations.NotNull;

public class FairyEntity extends BaseFairyEntity {

    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(FairyEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> PERSONALITY = DataTracker.registerData(FairyEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private final AttackPatterns patterns;

    public FairyEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.patterns = new AttackPatterns();
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return BaseFairyEntity.setAttributes();
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
        targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true, this::shouldAngerAt));
        targetSelector.add(3, new UniversalAngerGoal<>(this, false));
    }

    @Override
    public int getAttackCooldown() {
        int cooldown = super.getAttackCooldown();

        switch (getPersonality()) {
            case AGGRESSIVE -> cooldown = 20;
            case CURIOUS, SPONTANEOUS, TIMID -> cooldown = 40;
            case CONFIDENT -> cooldown = 25;
            case PLAYFUL -> cooldown = 30;
        }

        return cooldown + getCooldownOffset();
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        super.attack(target, pullProgress);

        switch (getPersonality()) {
            case AGGRESSIVE -> patterns.aggressive(this, target, getWorld());
            case CURIOUS -> patterns.curious(this, target, getWorld());
            case CONFIDENT -> patterns.confident(this, target, getWorld());
            case PLAYFUL -> patterns.playful(this, target, getWorld());
            case SPONTANEOUS -> patterns.spontaneous(this, target, getWorld());
            case TIMID -> patterns.timid(this, target, getWorld());
            default -> throw new IllegalStateException("Unexpected value: " + getPersonality());
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", getTypeVariant());
        nbt.putInt("Personality", getTypePersonality());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        dataTracker.set(VARIANT, nbt.getInt("Variant"));
        dataTracker.set(PERSONALITY, nbt.getInt("Personality"));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(VARIANT, 0);
        dataTracker.startTracking(PERSONALITY, 0);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt) {
        FairyVariant variant = Util.getRandom(FairyVariant.values(), getRandom());
        FairyPersonality personality = Util.getRandom(FairyPersonality.values(), getRandom());

        setVariant(variant);
        setPersonality(personality);

        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public static boolean canSpawn(EntityType<? extends BaseFairyEntity> type, ServerWorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        return BaseFairyEntity.canSpawn(type, world, reason, pos, random) && ArcadianDream.CONFIG.fairyOptions.spawnFairies();
    }

    public FairyVariant getVariant() {
        return FairyVariant.byId(getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return dataTracker.get(VARIANT);
    }

    private void setVariant(FairyVariant variant) {
        dataTracker.set(VARIANT, variant.getId() & 255);
    }

    public FairyPersonality getPersonality() {
        return FairyPersonality.byId(getTypePersonality() & 255);
    }

    private int getTypePersonality() {
        return this.dataTracker.get(PERSONALITY);
    }

    private void setPersonality(FairyPersonality personality) {
        dataTracker.set(PERSONALITY, personality.getId() & 255);
    }

    @Override
    public ImmutableList<BaseBulletEntity> availableBullets(World world, LivingEntity user) {
        return ImmutableList.of(
                new CircleBulletEntity(world, user), new PelletBulletEntity(world, user)
        );
    }

    private class AttackPatterns implements MobBulletPatterns {

        private void aggressive(LivingEntity fairy, LivingEntity target, World world) {
            createRing(world, fairy, target, 10, 0.5f, 0, 4, 50, getBulletColor());
        }

        private void curious(LivingEntity fairy, LivingEntity target, World world) {
            createRay(world, fairy, target, 5, 0.5f, 1, 4, 50, getBulletColor());
        }

        private void confident(LivingEntity fairy, LivingEntity target, World world) {
            createSpread(world, fairy,target, 4, 0.5f, 5, 4, 50, getBulletColor());
        }

        private void playful(LivingEntity fairy, LivingEntity target, World world) {
            createDouble(world, fairy, target, 6,0.5f, 0, 3, 50, getBulletColor());
        }

        private void spontaneous(LivingEntity fairy, LivingEntity target, World world) {
            createTriple(world, fairy, target, 3, 0.6f, 0, 4, 50, getBulletColor());
        }

        private void timid(LivingEntity fairy, LivingEntity target, World world) {
            createSpread(world, fairy, target, 1, 0.4f, 1, 3, 50, getBulletColor());
        }

        @Override
        public @NotNull BaseBulletEntity getBullet(World world, LivingEntity user) {
            return FairyEntity.this.availableBullets(world, user).get(getBulletType());
        }
    }
}
