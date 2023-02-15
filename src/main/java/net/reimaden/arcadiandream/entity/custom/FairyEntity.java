/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import net.reimaden.arcadiandream.entity.ai.DanmakuGoal;
import net.reimaden.arcadiandream.entity.variant.FairyPersonality;
import net.reimaden.arcadiandream.entity.variant.FairyVariant;
import net.reimaden.arcadiandream.item.custom.danmaku.MobBulletPatterns;
import net.reimaden.arcadiandream.sound.ModSounds;
import net.reimaden.arcadiandream.util.ColorMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;

public class FairyEntity extends HostileEntity implements GeoEntity, DanmakuMob, Angerable {

    @Nullable
    private UUID angryAt;
    private int angerTime;
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);

    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(FairyEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> PERSONALITY = DataTracker.registerData(FairyEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private final int bulletColor;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public FairyEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 10, true);
        this.lookControl = new LookControl(this);
        this.bulletColor = ColorMap.getRandomBulletColor(getRandom());
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.5D);
    }

    @Override
    protected void initGoals() {
        goalSelector.add(0, new SwimGoal(this));
        goalSelector.add(1, new DanmakuGoal(this, 2.0, 20, 10.0F));
        goalSelector.add(2, new FlyGoal(this, 1.0));
        goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        goalSelector.add(4, new LookAtEntityGoal(this, MobEntity.class, 6.0F));
        goalSelector.add(5, new LookAroundGoal(this));

        targetSelector.add(1, new RevengeGoal(this, FairyEntity.class));
        targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        targetSelector.add(3, new UniversalAngerGoal<>(this, false));
    }

    public int getPersonalityBasedCooldown() {
        return switch (getPersonality()) {
            case AGGRESSIVE -> 20;
            case CURIOUS, SPONTANEOUS, TIMID -> 40;
            case CONFIDENT -> 25;
            case PLAYFUL -> 30;
        };
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        Vec3d vec3d = getVelocity();
        if (!onGround && vec3d.y < 0.0 && !(moveControl.getTargetY() < getY())) {
            setVelocity(vec3d.multiply(1.0, 0.6, 1.0));
        }
        if (!world.isClient()) {
            tickAngerLogic((ServerWorld) world, true);
        }
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        AttackPatterns patterns = new AttackPatterns();

        switch (getPersonality()) {
            case AGGRESSIVE -> patterns.aggressive(this, target, world);
            case CURIOUS -> patterns.curious(this, target, world);
            case CONFIDENT -> patterns.confident(this, target, world);
            case PLAYFUL -> patterns.playful(this, target, world);
            case SPONTANEOUS -> patterns.spontaneous(this, target, world);
            case TIMID -> patterns.timid(this, target, world);
            default -> throw new IllegalStateException("Unexpected value: " + getPersonality());
        }

        playSound(ModSounds.ENTITY_DANMAKU_FIRE, 1f, 1f + (random.nextFloat() - 0.5f) * 0.1f);
    }

    private PlayState predicate(AnimationState<?> state) {
        if (age >= 1) {
            state.getController().setTransitionLength(5);
        }

        if (state.isMoving()) {
            state.getController().setAnimation(RawAnimation.begin().then("move", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if (isOnGround()) {
            state.getController().setAnimation(RawAnimation.begin().then("idle.ground", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        state.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar registrar) {
        registrar.add(new AnimationController<>(this, "controller",
                0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {}

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_BAT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_BAT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BAT_DEATH;
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 1.1875f;
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(true);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {}

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", getTypeVariant());
        nbt.putInt("Personality", getTypePersonality());
        writeAngerToNbt(nbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        dataTracker.set(VARIANT, nbt.getInt("Variant"));
        dataTracker.set(PERSONALITY, nbt.getInt("Personality"));
        readAngerFromNbt(world, nbt);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(VARIANT, 0);
        dataTracker.startTracking(PERSONALITY, 0);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt) {
        FairyVariant variant = Util.getRandom(FairyVariant.values(), random);
        FairyPersonality personality = Util.getRandom(FairyPersonality.values(), random);

        setVariant(variant);
        setPersonality(personality);

        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public FairyVariant getVariant() {
        return FairyVariant.byId(getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(VARIANT);
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

    public static boolean canSpawn(EntityType<FairyEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getDifficulty() != Difficulty.PEACEFUL && isOutside(world, pos) && canMobSpawn(type, world, spawnReason, pos, random);
    }

    private static boolean isOutside(BlockRenderView world, BlockPos pos) {
        return isLightLevelValidForNaturalSpawn(world, pos) && isAboveSeaLevel(pos);
    }

    private static boolean isLightLevelValidForNaturalSpawn(BlockRenderView world, BlockPos pos) {
        return world.getBaseLightLevel(pos, 0) > 8;
    }

    private static boolean isAboveSeaLevel(BlockPos pos) {
        return pos.getY() >= 63;
    }

    @Override
    public int getAngerTime() {
        return angerTime;
    }

    @Override
    public void setAngerTime(int angerTime) {
        this.angerTime = angerTime;
    }

    @Nullable
    @Override
    public UUID getAngryAt() {
        return angryAt;
    }

    @Override
    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    @Override
    public void chooseRandomAngerTime() {
        setAngerTime(ANGER_TIME_RANGE.get(random));
    }

    private static class AttackPatterns implements MobBulletPatterns {

        public void aggressive(FairyEntity fairy, LivingEntity target, World world) {
            createRing(world, fairy, target, 10, 0.5f, 0, 4, 50, fairy.bulletColor);
        }

        public void curious(FairyEntity fairy, LivingEntity target, World world) {
            createRay(world, fairy, target, 5, 0.5f, 1, 4, 50, fairy.bulletColor);
        }

        public void confident(FairyEntity fairy, LivingEntity target, World world) {
            createSpread(world, fairy,target, 4, 0.5f, 5, 4, 50, fairy.bulletColor);
        }

        public void playful(FairyEntity fairy, LivingEntity target, World world) {
            createDouble(world, fairy, target, 6,0.5f, 0, 3, 50, fairy.bulletColor);
        }

        public void spontaneous(FairyEntity fairy, LivingEntity target, World world) {
            createTriple(world, fairy, target, 3, 0.6f, 0, 4, 50, fairy.bulletColor);
        }

        public void timid(FairyEntity fairy, LivingEntity target, World world) {
            createSpread(world, fairy, target, 1, 0.4f, 1, 3, 50, fairy.bulletColor);
        }

        @Override
        public @NotNull BaseBulletEntity getBullet(World world, LivingEntity user) {
            return new CircleBulletEntity(world, user);
        }
    }
}
