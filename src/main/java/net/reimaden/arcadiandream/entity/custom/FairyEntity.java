/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.control.FlightMoveControl;
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
import net.reimaden.arcadiandream.entity.variant.FairyVariant;
import net.reimaden.arcadiandream.sound.ModSounds;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;

public class FairyEntity extends HostileEntity implements GeoEntity, RangedAttackMob, Angerable {

    @Nullable
    private UUID angryAt;
    private int angerTime;
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public FairyEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 10, true);
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

        targetSelector.add(1, new RevengeGoal(this));
        targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        targetSelector.add(3, new UniversalAngerGoal<>(this, false));
    }

    static class DanmakuGoal extends ProjectileAttackGoal {

        public DanmakuGoal(FairyEntity fairy, double speed, int cooldown, float maxAttackDistance) {
            super(fairy, speed, cooldown, maxAttackDistance);
        }

        @Override
        public boolean shouldContinue() {
            return canStart();
        }
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
        CircleBulletEntity bullet = new CircleBulletEntity(world, this);
        bullet.setPower(10);
        double d = target.getY() + target.getHeight() / 2;
        double e = target.getX() - this.getX();
        double f = d - bullet.getY();
        double g = target.getZ() - this.getZ();
        bullet.setVelocity(e, f, g, 0.8f, 0f);
        playSound(ModSounds.ENTITY_DANMAKU_FIRE, 1f, 1f + (random.nextFloat() - 0.5f) * 0.1f);
        world.spawnEntity(bullet);
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
        writeAngerToNbt(nbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        dataTracker.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
        readAngerFromNbt(world, nbt);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
    }

    private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
            DataTracker.registerData(FairyEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt) {
        FairyVariant variant = Util.getRandom(FairyVariant.values(), random);
        setVariant(variant);

        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public FairyVariant getVariant() {
        return FairyVariant.byId(getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
    }

    private void setVariant(FairyVariant variant) {
        dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
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
}
