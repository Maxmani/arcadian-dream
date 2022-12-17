/*
 * Copyright (c) 2022 Maxmani and contributors.
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

@SuppressWarnings("ParameterCanBeLocal")
public interface BulletPatterns {

    default void createSpread(World world, PlayerEntity user, ItemStack itemStack, int density, float speed, float divergence) {
        for (int i = 0; i < density; i++) {
            ThrownItemEntity bulletEntity = getBullet(world, user);
            bulletEntity.setItem(itemStack);
            bulletEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, speed, divergence + density - 1);
            world.spawnEntity(bulletEntity);
        }
    }

    default void createRing(World world, PlayerEntity user, ItemStack itemStack,
                            int density, int stack, float angle, float speed, float s, int modifier, float x, float y, float n, float divergence) {
        for (int i = 0; i < stack; i++) {
            for (int h = 0; h < density; h++) {
                ThrownItemEntity bulletEntity = getBullet(world, user);
                bulletEntity.setItem(itemStack);

                angle = (h * (360 / (float)density)) + 180;
                s = speed;
                if (modifier != 0) {
                    modifier = 1 + modifier;
                    x = MathHelper.cos(angle);
                    y = MathHelper.sin(angle) / 2; //replace 2 with modifier when modifier is correctly programmed in.
                    angle = (float)MathHelper.atan2(y, x) * MathHelper.DEGREES_PER_RADIAN;
                    s = MathHelper.sqrt(MathHelper.square(x) + MathHelper.square(y)) * speed;
                }
                Vec3d bullet = new Vec3d(0, 0, 1);
                bullet = bullet.rotateY((angle) * MathHelper.RADIANS_PER_DEGREE);
                bullet = bullet.rotateX(user.getPitch() * MathHelper.RADIANS_PER_DEGREE);
                bullet = bullet.rotateY((-user.getYaw() + 180) * MathHelper.RADIANS_PER_DEGREE);

                bulletEntity.setVelocity(bullet.getX(),
                        bullet.getY(), bullet.getZ(), s, divergence);

                world.spawnEntity(bulletEntity);
            }

            speed = speed - n;
        }
    }

    default ThrownItemEntity getBullet(World world, LivingEntity user) {
        return null;
    }
}
