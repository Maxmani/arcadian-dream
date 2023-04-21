/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;
import net.reimaden.arcadiandream.entity.custom.DanmakuMob;
import net.reimaden.arcadiandream.entity.custom.mob.BaseFairyEntity;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class DanmakuGoal extends Goal {

    private final MobEntity mob;
    private final DanmakuMob owner;
    @Nullable
    private LivingEntity target;
    private int updateCountdownTicks = -1;
    private final double mobSpeed;
    private int seenTargetTicks;
    private int minIntervalTicks;
    private int maxIntervalTicks;
    private final float maxShootRange;
    private final float squaredMaxShootRange;

    public DanmakuGoal(DanmakuMob mob, double mobSpeed, int intervalTicks, float maxShootRange) {
        this(mob, mobSpeed, intervalTicks, intervalTicks, maxShootRange);
    }

    public DanmakuGoal(DanmakuMob mob, double mobSpeed, int minIntervalTicks, int maxIntervalTicks, float maxShootRange) {
        if (!(mob instanceof LivingEntity)) {
            throw new IllegalArgumentException("DanmakuGoal requires Mob implements DanmakuMob");
        }
        this.owner = mob;
        this.mob = (MobEntity) mob;
        this.mobSpeed = mobSpeed;
        this.minIntervalTicks = minIntervalTicks;
        this.maxIntervalTicks = maxIntervalTicks;
        this.maxShootRange = maxShootRange;
        this.squaredMaxShootRange = maxShootRange * maxShootRange;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        LivingEntity livingEntity = mob.getTarget();
        if (livingEntity == null || !livingEntity.isAlive()) {
            return false;
        }
        target = livingEntity;
        return true;
    }

    @Override
    public boolean shouldContinue() {
        return canStart();
    }

    @Override
    public void stop() {
        this.target = null;
        this.seenTargetTicks = 0;
        this.updateCountdownTicks = -1;
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        //noinspection DataFlowIssue
        double d = mob.squaredDistanceTo(target.getX(), target.getY(), target.getZ());
        boolean bl = mob.getVisibilityCache().canSee(target);
        seenTargetTicks = bl ? ++seenTargetTicks : 0;
        if (d > (double)squaredMaxShootRange || seenTargetTicks < 5) {
            mob.getNavigation().startMovingTo(target, mobSpeed);
        } else {
            mob.getNavigation().stop();
        }
        mob.getLookControl().lookAt(target, 30.0f, 180.0f);
        if (--updateCountdownTicks == 0) {
            if (!bl) {
                return;
            }
            float f = (float)Math.sqrt(d) / maxShootRange;
            float g = MathHelper.clamp(f, 0.1f, 1.0f);
            owner.attack(target, g);
            if (mob instanceof BaseFairyEntity fairy) {
                setMinIntervalTicks(fairy.getAttackCooldown());
                setMaxIntervalTicks(fairy.getAttackCooldown());
            }
            updateCountdownTicks = MathHelper.floor(f * (float)(maxIntervalTicks - minIntervalTicks) + (float)minIntervalTicks);
        } else if (updateCountdownTicks < 0) {
            updateCountdownTicks = MathHelper.floor(MathHelper.lerp(Math.sqrt(d) / (double)maxShootRange, minIntervalTicks, maxIntervalTicks));
        }
    }

    private void setMinIntervalTicks(int minIntervalTicks) {
        this.minIntervalTicks = minIntervalTicks;
    }

    private void setMaxIntervalTicks(int maxIntervalTicks) {
        this.maxIntervalTicks = maxIntervalTicks;
    }
}
