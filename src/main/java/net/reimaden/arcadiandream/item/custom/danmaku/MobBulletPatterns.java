/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item.custom.danmaku;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.entity.custom.danmaku.BaseBulletEntity;
import org.jetbrains.annotations.NotNull;

public interface MobBulletPatterns {

    default void createRay(World world, LivingEntity user, LivingEntity target, int density, float speed, float divergence, float power, int duration, int color) {
        float pitch = user.getPitch();
        float yaw = user.getHeadYaw();
        float targetHitbox = (target.getHeight() / 2) * 5;
        float n = speed / density;

        ItemStack stack = getBullet(world, user).getStack();
        BaseShotItem item = (BaseShotItem) stack.getItem();
        item.setPower(stack, power);
        item.setDuration(stack, duration);
        item.setColor(stack, color);

        for (int i = 0; i < density; i++) {
            BaseBulletEntity bulletEntity = getBullet(world, user);
            bulletEntity.setItem(stack);

            bulletEntity.setVelocity(user, pitch + targetHitbox, yaw, 0.0f, speed, divergence);
            world.spawnEntity(bulletEntity);

            speed = speed - n;
        }
    }

    default void createSpread(World world, LivingEntity user, LivingEntity target, int density, float speed, float divergence, float power, int duration, int color) {
        float pitch = user.getPitch();
        float yaw = user.getHeadYaw();
        float targetHitbox = (target.getHeight() / 2) * 5;

        ItemStack stack = getBullet(world, user).getStack();
        BaseShotItem item = (BaseShotItem) stack.getItem();
        item.setPower(stack, power);
        item.setDuration(stack, duration);
        item.setColor(stack, color);

        for (int i = 0; i < density; i++) {
            BaseBulletEntity bulletEntity = getBullet(world, user);
            bulletEntity.setItem(stack);

            bulletEntity.setVelocity(user, pitch + targetHitbox, yaw, 0.0f, speed, divergence + density - 1);
            world.spawnEntity(bulletEntity);
        }
    }

    default void createRing(World world, LivingEntity user, LivingEntity target, int density, float speed, float divergence, float power, int duration, int color) {
        float pitch = user.getPitch();
        float yaw = user.getHeadYaw();
        float targetHitbox = (target.getHeight() / 2) * 5;

        ItemStack stack = getBullet(world, user).getStack();
        BaseShotItem item = (BaseShotItem) stack.getItem();
        item.setPower(stack, power);
        item.setDuration(stack, duration);
        item.setColor(stack, color);

        for (int i = 0; i < density; i++) {
            BaseBulletEntity bulletEntity = getBullet(world, user);
            bulletEntity.setItem(stack);

            float angle = (i * (360f / density)) + 180;
            Vec3d bullet = new Vec3d(0, 0, 1);

            bullet = bullet.rotateY(angle * MathHelper.RADIANS_PER_DEGREE);
            bullet = bullet.rotateX((pitch + targetHitbox) * MathHelper.RADIANS_PER_DEGREE);
            bullet = bullet.rotateY((-yaw + 180) * MathHelper.RADIANS_PER_DEGREE);

            bulletEntity.setVelocity(bullet.getX(), bullet.getY(), bullet.getZ(), speed, divergence);
            world.spawnEntity(bulletEntity);
        }
    }

    default void createDouble(World world, LivingEntity user, LivingEntity target, int density, float speed, float divergence, float power, int duration, int color) {
        float pitch = user.getPitch();
        float yaw = user.getHeadYaw();
        final float spread = 22.5f;
        final int rays = Math.min(density, 2);
        float targetHitbox = (target.getHeight() / 2) * 5;
        float n = speed / density;

        ItemStack stack = getBullet(world, user).getStack();
        BaseShotItem item = (BaseShotItem) stack.getItem();
        item.setPower(stack, power);
        item.setDuration(stack, duration);
        item.setColor(stack, color);

        for (int j = 0; j < rays; j++) {
            float s = speed;

            for (int i = 0; i < (density <= 2 ? Math.ceil((float) density / 2) : Math.floor((float) density / 2)); i++) {
                BaseBulletEntity bulletEntity = getBullet(world, user);
                bulletEntity.setItem(stack);

                float angle = (spread * j) - spread / 2;
                Vec3d bullet = new Vec3d(0, 0, 1);

                bullet = bullet.rotateY((angle + 180) * MathHelper.RADIANS_PER_DEGREE);
                bullet = bullet.rotateX((pitch + targetHitbox) * MathHelper.RADIANS_PER_DEGREE);
                bullet = bullet.rotateY((-yaw + 180) * MathHelper.RADIANS_PER_DEGREE);

                bulletEntity.setVelocity(bullet.getX(), bullet.getY(), bullet.getZ(), s, divergence);
                world.spawnEntity(bulletEntity);

                s = s - n;
            }
        }
    }

    default void createTriple(World world, LivingEntity user, LivingEntity target, int density, float speed, float divergence, float power, int duration, int color) {
        float pitch = user.getPitch();
        float yaw = user.getHeadYaw();
        final float spread = 15f;
        final int rays = Math.min(density, 3);
        float targetHitbox = (target.getHeight() / 2) * 5;
        float n = speed / density;

        ItemStack stack = getBullet(world, user).getStack();
        BaseShotItem item = (BaseShotItem) stack.getItem();
        item.setPower(stack, power);
        item.setDuration(stack, duration);
        item.setColor(stack, color);

        for (int j = 0; j < rays; j++) {
            float s = speed;

            for (int i = 0; i < (density <= 3 ? Math.ceil((float) density / 3) : Math.floor((float) density / 3)); i++) {
                BaseBulletEntity bulletEntity = getBullet(world, user);
                bulletEntity.setItem(stack);

                float angle = (spread * j) - spread;
                Vec3d bullet = new Vec3d(0, 0, 1);

                bullet = bullet.rotateY((angle + 180) * MathHelper.RADIANS_PER_DEGREE);
                bullet = bullet.rotateX((pitch + targetHitbox) * MathHelper.RADIANS_PER_DEGREE);
                bullet = bullet.rotateY((-yaw + 180) * MathHelper.RADIANS_PER_DEGREE);

                bulletEntity.setVelocity(bullet.getX(), bullet.getY(), bullet.getZ(), s, divergence);
                world.spawnEntity(bulletEntity);

                s = s - n;
            }
        }
    }

    @NotNull
    BaseBulletEntity getBullet(World world, LivingEntity user);
}
