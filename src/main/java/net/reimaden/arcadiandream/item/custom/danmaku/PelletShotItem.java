/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item.custom.danmaku;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.entity.custom.danmaku.BaseBulletEntity;
import net.reimaden.arcadiandream.entity.custom.danmaku.PelletBulletEntity;
import org.jetbrains.annotations.NotNull;

public class PelletShotItem extends BaseShotItem {

    public PelletShotItem(Settings settings, float power, float speed, int duration, int cooldown, float gravity,
                          float divergence, String pattern, int density, float maxPower, float maxSpeed, int maxDuration,
                          int maxCooldown, float maxDivergence, int maxDensity) {
        super(settings, power, speed, duration, cooldown, gravity, divergence, pattern,
                density, maxPower, maxSpeed, maxDuration, maxCooldown, maxDivergence, maxDensity);
    }

    @Override
    public @NotNull BaseBulletEntity getBullet(World world, LivingEntity user) {
        return new PelletBulletEntity(world, user);
    }
}
