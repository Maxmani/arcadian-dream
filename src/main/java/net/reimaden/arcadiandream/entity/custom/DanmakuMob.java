/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.entity.custom.danmaku.BaseBulletEntity;
import net.reimaden.arcadiandream.item.custom.danmaku.BaseShotItem;
import net.reimaden.arcadiandream.sound.ModSounds;

public interface DanmakuMob extends RangedAttackMob {

    BaseBulletEntity availableBullets(World world, LivingEntity user);

    default void getDanmakuSound(Entity entity, Random random) {
        entity.playSound(ModSounds.ENTITY_DANMAKU_FIRE, BaseShotItem.getSoundVolume(), BaseShotItem.getSoundPitch(random));
    }
}
