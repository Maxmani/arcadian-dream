/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.custom.mob;

import com.google.common.collect.ImmutableList;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.entity.ai.DanmakuGoal;
import net.reimaden.arcadiandream.entity.custom.danmaku.BaseBulletEntity;
import net.reimaden.arcadiandream.entity.custom.danmaku.CircleBulletEntity;
import net.reimaden.arcadiandream.entity.custom.danmaku.PelletBulletEntity;
import net.reimaden.arcadiandream.entity.custom.danmaku.StarBulletEntity;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.item.custom.danmaku.MobBulletPatterns;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

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
        targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true, this::isTarget));
    }

    private boolean isTarget(LivingEntity entity) {
        Optional<TrinketComponent> trinketComponent = TrinketsApi.getTrinketComponent(entity);

        if (trinketComponent.isEmpty()) return true;

        List<Pair<SlotReference, ItemStack>> list = trinketComponent.get().getEquipped(ModItems.FAIRY_CHARM);
        if (list.size() > 0) {
            ItemStack stack = list.get(0).getRight();
            if (stack != null) {
                NbtCompound nbt = stack.getOrCreateNbt();
                return nbt.getInt("mode") == 1;
            }
        }

        return true;
    }

    @Override
    public int getAttackCooldown() {
        int cooldown = super.getAttackCooldown();

        switch (getAttackType()) {
            case 0, 7 -> cooldown = 20;
            case 1 -> cooldown = 40;
            case 2 -> cooldown = 60;
            case 3 -> cooldown = 30;
            case 4 -> cooldown = 12;
            case 5 -> cooldown = 35;
            case 6 -> cooldown = 25;
        }

        return cooldown + getCooldownOffset();
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        super.attack(target, pullProgress);

        switch (getAttackType()) {
            case 0 -> patterns.burstAttack(this, target, getWorld());
            case 1 -> patterns.spreadAttack(this, target, getWorld());
            case 2 -> patterns.carpetBombAttack(this, getWorld());
            case 3 -> patterns.arcAttack(this, target, getWorld());
            case 4 -> patterns.crossAttack(this, target, getWorld());
            case 5 -> patterns.ringAttack(this, target, getWorld());
            case 6 -> patterns.tripleAttack(this, target, getWorld());
            case 7 -> patterns.carpetBombAttackQuick(this, getWorld());
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
        getWorld().getEntitiesByClass(BaseFairyEntity.class, box, EntityPredicates.EXCEPT_SPECTATOR).stream()
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
        byte attackType = (byte) getRandom().nextInt(8);

        setAttackType(attackType);

        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public static boolean canSpawn(EntityType<? extends BaseFairyEntity> type, ServerWorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        return BaseFairyEntity.canSpawn(type, world, reason, pos, random) && ArcadianDream.CONFIG.fairyOptions.spawnSunflowerFairies();
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
                new CircleBulletEntity(world, user), new StarBulletEntity(world, user), new PelletBulletEntity(world, user)
        );
    }

    private class AttackPatterns implements MobBulletPatterns {

        private void burstAttack(LivingEntity fairy, LivingEntity target, World world) {
            createSpread(world, fairy, target, getRandomNumberProvider() + 2, 0.5f, 5, 5, 50, getBulletColor());
        }

        private void spreadAttack(LivingEntity fairy, LivingEntity target, World world) {
            createSpread(world, fairy, target, getRandomNumberProvider() + 7, 0.2f, 180, 4, 20, getBulletColor());
        }

        private void carpetBombAttack(LivingEntity fairy, World world) {
            createRain(world, fairy, 15, 1.2f, 8, 5, 100, getBulletColor(), 0.1f);
        }

        private void arcAttack(LivingEntity fairy, LivingEntity target, World world) {
            createArc(world, fairy, target, 5, 0.5f, 0, 6, 50, getBulletColor());
        }

        private void crossAttack(LivingEntity fairy, LivingEntity target, World world) {
            createRing(world, fairy, target, 4, 0.65f, 0, 3, 40, getBulletColor());
        }

        private void ringAttack(LivingEntity fairy, LivingEntity target, World world) {
            createRing(world, fairy, target, 15, 0.45f, 0, 4, 60, getBulletColor());
        }

        private void tripleAttack(LivingEntity fairy, LivingEntity target, World world) {
            createTriple(world, fairy, target, 6, 0.8f, 5, 4, 50, getBulletColor());
        }

        private void carpetBombAttackQuick(LivingEntity fairy, World world) {
            createRain(world, fairy, 15, 1.2f, 8, 5, 100, getBulletColor(), 0.1f);
        }

        @Override
        public @NotNull BaseBulletEntity getBullet(World world, LivingEntity user) {
            return SunflowerFairyEntity.this.availableBullets(world, user).get(getBulletType());
        }
    }
}
