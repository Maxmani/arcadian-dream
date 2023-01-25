/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item.custom.danmaku;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.reimaden.arcadiandream.entity.custom.OrbBulletEntity;
import net.minecraft.world.World;

public class OrbBulletItem extends BaseBulletItem {

    public OrbBulletItem(Settings settings, int power, float speed, int maxAge, int cooldown, float gravity,
                         float divergence, String pattern, int density) {
        super(settings, power, speed, maxAge, cooldown, gravity, divergence, pattern, density);
    }

    @Override
    public ThrownItemEntity getBullet(World world, LivingEntity user) {
        return new OrbBulletEntity(world, user);
    }
}
