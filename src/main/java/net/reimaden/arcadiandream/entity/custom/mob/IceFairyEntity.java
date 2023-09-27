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
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.entity.ai.DanmakuGoal;
import net.reimaden.arcadiandream.entity.custom.danmaku.BaseBulletEntity;
import net.reimaden.arcadiandream.entity.custom.danmaku.CircleBulletEntity;
import net.reimaden.arcadiandream.entity.custom.danmaku.KunaiBulletEntity;
import net.reimaden.arcadiandream.entity.custom.danmaku.PelletBulletEntity;
import net.reimaden.arcadiandream.item.custom.danmaku.MobBulletPatterns;
import net.reimaden.arcadiandream.util.ColorMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IceFairyEntity extends BaseFairyEntity {

    private final AttackPatterns patterns;
    private byte attackType;

    public IceFairyEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.patterns = new AttackPatterns();
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return BaseFairyEntity.setAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0D);
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

        switch (getAttackType()) {
            case 0 -> cooldown = 6;
            case 1, 2 -> cooldown = 20;
            case 3 -> cooldown = 10;
            case 4 -> cooldown = 12;
            case 5 -> cooldown = 40;
            case 6 -> cooldown = 25;
            case 7 -> cooldown = 30;
        }

        return cooldown + getCooldownOffset();
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        super.attack(target, pullProgress);

        switch (getAttackType()) {
            case 0 -> patterns.rainAttack(this, getWorld());
            case 1 -> patterns.hexagonAttack(this, target, getWorld());
            case 2 -> patterns.divergedHexagonAttack(this, target, getWorld());
            case 3 -> patterns.snowballAttack(this, target, getWorld());
            case 4 -> patterns.rayAttack(this, target, getWorld());
            case 5 -> patterns.rayButBigAttack(this, target, getWorld());
            case 6 -> patterns.randomAttack(this, target, getWorld());
            case 7 -> patterns.randomArcAttack(this, target, getWorld());
            default -> throw new IllegalStateException("Unexpected value: " + getAttackType());
        }
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
        byte attackType = (byte) getRandom().nextInt(8);

        setAttackType(attackType);

        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void initializeBullets(int bulletColor, int cooldownOffset, int randomNumberProvider, byte bulletType) {
        setBulletColor(ColorMap.getColorInt("cyan"));
        setCooldownOffset(cooldownOffset);
        setRandomNumberProvider(randomNumberProvider);
        setBulletType(bulletType);
    }

    public static boolean canSpawn(EntityType<? extends BaseFairyEntity> type, ServerWorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        return BaseFairyEntity.canSpawn(type, world, reason, pos, random) && ArcadianDream.CONFIG.fairyOptions.spawnIceFairies();
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
                new CircleBulletEntity(world, user), new KunaiBulletEntity(world, user), new PelletBulletEntity(world, user)
        );
    }

    private class AttackPatterns implements MobBulletPatterns {

        private void rainAttack(LivingEntity fairy, World world) {
            createRain(world, fairy, 1, 1.2f, 20, 5, 100, getBulletColor(), 0.1f);
        }

        private void hexagonAttack(LivingEntity fairy, LivingEntity target, World world) {
            createRing(world, fairy, target, 6, 0.8f, 0, 4, 40, getBulletColor());
        }

        private void divergedHexagonAttack(LivingEntity fairy, LivingEntity target, World world) {
            createRing(world, fairy, target, 6, 0.8f, getRandomNumberProvider() + 10, 4, 40, getBulletColor());
        }

        private void snowballAttack(LivingEntity fairy, LivingEntity target, World world) {
            createSpread(world, fairy, target, 1, 0.8f, 5, 3, 50, getBulletColor());
        }

        private void rayAttack(LivingEntity fairy, LivingEntity target, World world) {
            createRay(world, fairy, target, 15, 1.25f, 2, 3, 20, getBulletColor());
        }

        private void rayButBigAttack(LivingEntity fairy, LivingEntity target, World world) {
            createDouble(world, fairy, target, 32, 1.25f, 3, 3, 30, getBulletColor());
        }

        private void randomAttack(LivingEntity fairy, LivingEntity target, World world) {
            createTriple(world, fairy, target, getRandomNumberProvider() + 2, 0.75f, getRandomNumberProvider() + 4, 4, 40, getBulletColor());
        }

        private void randomArcAttack(LivingEntity fairy, LivingEntity target, World world) {
            createArc(world, fairy, target, getRandomNumberProvider() + 1, 0.75f, getRandomNumberProvider() + 6, 4, 40, getBulletColor());
        }

        @Override
        public @NotNull BaseBulletEntity getBullet(World world, LivingEntity user) {
            return IceFairyEntity.this.availableBullets(world, user).get(getBulletType());
        }
    }
}
