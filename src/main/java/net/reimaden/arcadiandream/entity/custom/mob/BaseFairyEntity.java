/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.custom.mob;

import com.google.common.collect.ImmutableList;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.LookControl;
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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Pair;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.entity.ModEntities;
import net.reimaden.arcadiandream.entity.custom.DanmakuMob;
import net.reimaden.arcadiandream.entity.custom.danmaku.BaseBulletEntity;
import net.reimaden.arcadiandream.entity.custom.danmaku.CircleBulletEntity;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.sound.ModSounds;
import net.reimaden.arcadiandream.util.ColorMap;
import net.reimaden.arcadiandream.util.ModTags;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * This class holds common methods and stats for all fairies.
 */
public class BaseFairyEntity extends HostileEntity implements GeoEntity, DanmakuMob, Angerable {

    @Nullable private UUID angryAt;
    private int angerTime;
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private int bulletColor;
    private int cooldownOffset;
    private int randomNumberProvider;
    private static final TrackedData<Byte> BULLET_TYPE = DataTracker.registerData(BaseFairyEntity.class, TrackedDataHandlerRegistry.BYTE);

    protected BaseFairyEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 10, true);
        this.lookControl = new LookControl(this);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.5D);
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        if (getBulletType() > availableBullets(getWorld(), this).size() - 1 || getBulletType() < 0) {
            setBulletType((byte) 0);
        }

        getDanmakuSound(this, getRandom());
    }

    public int getAttackCooldown() {
        return 20;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        Vec3d vec3d = getVelocity();
        if (!isOnGround() && vec3d.y < 0.0 && !(moveControl.getTargetY() < getY())) {
            setVelocity(vec3d.multiply(1.0, 0.6, 1.0));
        }
    }

    @Override
    protected void mobTick() {
        super.mobTick();
        tickAngerLogic((ServerWorld) getWorld(), true);
    }

    @SuppressWarnings("SameReturnValue")
    private PlayState predicate(AnimationState<?> state) {
        if (state.isMoving() && hurtTime == 0) {
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
                5, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {}

    @Override
    protected float getSoundVolume() {
        return 0.8f;
    }

    @Override
    public float getSoundPitch() {
        return super.getSoundPitch() * 0.9f;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.ENTITY_FAIRY_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ENTITY_FAIRY_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ENTITY_FAIRY_DEATH;
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 1.1875f;
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {}

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        writeAngerToNbt(nbt);
        nbt.putInt("BulletColor", getBulletColor());
        nbt.putInt("CooldownOffset", getCooldownOffset());
        nbt.putInt("RandomNumberProvider", getRandomNumberProvider());
        nbt.putByte("BulletType", getBulletType());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        readAngerFromNbt(getWorld(), nbt);
        setBulletColor(nbt.getInt("BulletColor"));
        setCooldownOffset(nbt.getInt("CooldownOffset"));
        setRandomNumberProvider(nbt.getInt("RandomNumberProvider"));
        dataTracker.set(BULLET_TYPE, nbt.getByte("BulletType"));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(BULLET_TYPE, (byte) 0);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        int bulletColor = ColorMap.getRandomBulletColor(getRandom());
        int cooldownOffset = getRandom().nextInt(11) - 5;
        int randomNumberProvider = getRandom().nextInt(11) + 1;
        byte bulletType = (byte) getRandom().nextInt(availableBullets(this.getWorld(), this).size());

        initializeBullets(bulletColor, cooldownOffset, randomNumberProvider, bulletType);

        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    protected void initializeBullets(int bulletColor, int cooldownOffset, int randomNumberProvider, byte bulletType) {
        setBulletColor(bulletColor);
        setCooldownOffset(cooldownOffset);
        setRandomNumberProvider(randomNumberProvider);
        setBulletType(bulletType);
    }

    public static boolean canSpawn(EntityType<? extends BaseFairyEntity> type, ServerWorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        return world.getDifficulty() != Difficulty.PEACEFUL && isValidSpawn(world, pos) && canMobSpawn(type, world, reason, pos, random) && !isSpawnBlockerNear(world, pos);
    }

    private static boolean isValidSpawn(ServerWorldAccess world, BlockPos pos) {
        return world.getBlockState(pos.down()).isIn(ModTags.Blocks.FAIRIES_SPAWNABLE_ON) && isLightLevelValid(world, pos);
    }

    private static boolean isLightLevelValid(ServerWorldAccess world, BlockPos pos) {
        long timeOfDay = Objects.requireNonNull(world.getServer()).getOverworld().getTimeOfDay() % 24000;
        return world.getLightLevel(pos) > 8 && timeOfDay >= 0 && timeOfDay < 12000;
    }

    private static boolean isSpawnBlockerNear(ServerWorldAccess world, BlockPos pos) {
        if (!ArcadianDream.CONFIG.fairyCharmOptions.canPreventSpawning()) return false;

        PlayerEntity closestPlayer = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), ArcadianDream.CONFIG.fairyCharmOptions.distance(), false);
        Optional<TrinketComponent> trinketComponent = TrinketsApi.getTrinketComponent(closestPlayer);

        if (trinketComponent.isEmpty()) return false;

        List<Pair<SlotReference, ItemStack>> list = trinketComponent.get().getEquipped(ModItems.FAIRY_CHARM);
        if (list.size() > 0) {
            ItemStack stack = list.get(0).getRight();
            if (stack != null) {
                NbtCompound nbt = stack.getOrCreateNbt();
                return closestPlayer != null && (nbt.getInt("mode") >= 1);
            }
        }

        return false;
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return 0.0f;
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

    public int getBulletColor() {
        return bulletColor;
    }

    protected void setBulletColor(int bulletColor) {
        this.bulletColor = bulletColor;
    }

    public int getCooldownOffset() {
        return cooldownOffset;
    }

    protected void setCooldownOffset(int cooldownOffset) {
        this.cooldownOffset = cooldownOffset;
    }

    public int getRandomNumberProvider() {
        return randomNumberProvider;
    }

    protected void setRandomNumberProvider(int randomNumberProvider) {
        this.randomNumberProvider = randomNumberProvider;
    }

    public byte getBulletType() {
        return dataTracker.get(BULLET_TYPE);
    }

    protected void setBulletType(byte bulletType) {
        dataTracker.set(BULLET_TYPE, bulletType);
    }

    @Override
    public ImmutableList<BaseBulletEntity> availableBullets(World world, LivingEntity user) {
        return ImmutableList.of(
                new CircleBulletEntity(world, user)
        );
    }

    @Override
    public EntityGroup getGroup() {
        return ModEntities.EntityGroups.YOUKAI;
    }
}
