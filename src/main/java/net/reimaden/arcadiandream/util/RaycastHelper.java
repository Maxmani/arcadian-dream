/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.function.Predicate;

public class RaycastHelper {

    public static EntityHitResult raycast(PlayerEntity user, double distance, Predicate<Entity> filter) {
        Vec3d position = user.getEyePos();
        Vec3d direction = user.getRotationVec(1.0f);
        Vec3d endPosition = position.add(direction.multiply(distance));
        Box box = user.getBoundingBox().expand(distance);

        return ProjectileUtil.raycast(user, position, endPosition, box, filter, distance * distance);
    }
}
