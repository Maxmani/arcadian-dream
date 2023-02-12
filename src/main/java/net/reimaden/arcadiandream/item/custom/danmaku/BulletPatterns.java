/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item.custom.danmaku;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface BulletPatterns {

    default void createSpread(World world, PlayerEntity user, ItemStack stack, int density, float speed, float divergence) {
        for (int i = 0; i < density; i++) {
            ThrownItemEntity bulletEntity = getBullet(world, user);
            bulletEntity.setItem(stack);

            bulletEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, speed, divergence + density - 1);
            world.spawnEntity(bulletEntity);
        }
    }

    default void createRay(World world, PlayerEntity user, ItemStack stack, int density, float speed, float divergence, float n) {
        for (int i = 0; i < density; i++) {
            ThrownItemEntity bulletEntity = getBullet(world, user);
            bulletEntity.setItem(stack);

            bulletEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, speed, divergence);
            world.spawnEntity(bulletEntity);

            speed = speed - n;
        }
    }

    default void createRing(World world, PlayerEntity user, ItemStack stack, int density, float speed, float divergence) {
        for (int i = 0; i < density; i++) {
            ThrownItemEntity bulletEntity = getBullet(world, user);
            bulletEntity.setItem(stack);

            float angle = (i * (360f / density)) + 180;
            Vec3d bullet = new Vec3d(0, 0, 1);

            bullet = bullet.rotateY((angle) * MathHelper.RADIANS_PER_DEGREE);
            bullet = bullet.rotateX(user.getPitch() * MathHelper.RADIANS_PER_DEGREE);
            bullet = bullet.rotateY((-user.getYaw() + 180) * MathHelper.RADIANS_PER_DEGREE);

            bulletEntity.setVelocity(bullet.getX(), bullet.getY(), bullet.getZ(), speed, divergence);

            world.spawnEntity(bulletEntity);
        }
    }

    default void createCone(World world, PlayerEntity user, ItemStack stack, int density, float speed, float divergence) {
        final float cone = 45f;

        for (int i = 0; i < density; i++) {
            ThrownItemEntity bulletEntity = getBullet(world, user);
            bulletEntity.setItem(stack);

            float angle = (i * (cone / (density - 1)));
            Vec3d bullet = new Vec3d(0, 0, 1);

            bullet = bullet.rotateY((angle + (180 - (cone / 2))) * MathHelper.RADIANS_PER_DEGREE);
            bullet = bullet.rotateX(user.getPitch() * MathHelper.RADIANS_PER_DEGREE);
            bullet = bullet.rotateY((-user.getYaw() + 180) * MathHelper.RADIANS_PER_DEGREE);

            bulletEntity.setVelocity(bullet.getX(), bullet.getY(), bullet.getZ(), speed, divergence);

            world.spawnEntity(bulletEntity);
        }
    }

    default void createDouble(World world, PlayerEntity user, ItemStack stack, int density, float speed, float divergence, float n) {
        float s = speed;
        final float spread = 22.5f;
        final int rays = Math.min(density, 2);

        for (int j = 0; j < rays; j++) {
            for (int i = 0; i < (density <= 2 ? Math.ceil((float) density / 2) : Math.floor((float) density / 2)); i++) {
                ThrownItemEntity bulletEntity = getBullet(world, user);
                bulletEntity.setItem(stack);

                float angle = (spread * j) - spread / 2;
                Vec3d bullet = new Vec3d(0, 0, 1);

                bullet = bullet.rotateY((angle + 180) * MathHelper.RADIANS_PER_DEGREE);
                bullet = bullet.rotateX(user.getPitch() * MathHelper.RADIANS_PER_DEGREE);
                bullet = bullet.rotateY((-user.getYaw() + 180) * MathHelper.RADIANS_PER_DEGREE);

                bulletEntity.setVelocity(bullet.getX(), bullet.getY(), bullet.getZ(), s, divergence);
                world.spawnEntity(bulletEntity);

                s = s - n;
            }

            s = speed;
        }
    }

    default void createTriple(World world, PlayerEntity user, ItemStack stack, int density, float speed, float divergence, float n) {
        float s = speed;
        final float spread = 15f;
        final int rays = Math.min(density, 3);

        for (int j = 0; j < rays; j++) {
            for (int i = 0; i < (density <= 3 ? Math.ceil((float) density / 3) : Math.floor((float) density / 3)); i++) {
                ThrownItemEntity bulletEntity = getBullet(world, user);
                bulletEntity.setItem(stack);

                float angle = (spread * j) - spread;
                Vec3d bullet = new Vec3d(0, 0, 1);

                bullet = bullet.rotateY((angle + 180) * MathHelper.RADIANS_PER_DEGREE);
                bullet = bullet.rotateX(user.getPitch() * MathHelper.RADIANS_PER_DEGREE);
                bullet = bullet.rotateY((-user.getYaw() + 180) * MathHelper.RADIANS_PER_DEGREE);

                bulletEntity.setVelocity(bullet.getX(), bullet.getY(), bullet.getZ(), s, divergence);
                world.spawnEntity(bulletEntity);

                s = s - n;
            }

            s = speed;
        }
    }

    default ThrownItemEntity getBullet(World world, LivingEntity user) {
        return null;
    }
}
