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

    default void createSpread(World world, PlayerEntity user, ItemStack itemStack, int density, float speed, float divergence) {
        for (int i = 0; i < density; i++) {
            ThrownItemEntity bulletEntity = getBullet(world, user);
            bulletEntity.setItem(itemStack);

            bulletEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, speed, divergence + density - 1);
            world.spawnEntity(bulletEntity);
        }
    }

    default void createRay(World world, PlayerEntity user, ItemStack itemStack, float speed, float divergence, float n, int density) {
        for (int i = 0; i < density; i++) {
            ThrownItemEntity bulletEntity = getBullet(world, user);
            bulletEntity.setItem(itemStack);

            bulletEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, speed, divergence);
            world.spawnEntity(bulletEntity);

            speed = speed - n;
        }
    }

    default void createRing(World world, PlayerEntity user, ItemStack itemStack,
                            int density, float speed, float divergence) {
        for (int i = 0; i < density; i++) {
            ThrownItemEntity bulletEntity = getBullet(world, user);
            bulletEntity.setItem(itemStack);

            float angle = (i * (360 / (float) density)) + 180;
            Vec3d bullet = new Vec3d(0, 0, 1);
            bullet = bullet.rotateY((angle) * MathHelper.RADIANS_PER_DEGREE);
            bullet = bullet.rotateX(user.getPitch() * MathHelper.RADIANS_PER_DEGREE);
            bullet = bullet.rotateY((-user.getYaw() + 180) * MathHelper.RADIANS_PER_DEGREE);

            bulletEntity.setVelocity(bullet.getX(),
                    bullet.getY(), bullet.getZ(), speed, divergence);

            world.spawnEntity(bulletEntity);
        }
    }

    default ThrownItemEntity getBullet(World world, LivingEntity user) {
        return null;
    }
}
