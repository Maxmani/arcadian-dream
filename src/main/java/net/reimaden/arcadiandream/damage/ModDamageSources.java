/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.damage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.reimaden.arcadiandream.damage.custom.DanmakuDamageSource;
import org.jetbrains.annotations.Nullable;

public class ModDamageSources {

    public static DamageSource danmaku(Entity projectile, @Nullable Entity attacker) {
        return new DanmakuDamageSource("danmaku", projectile, attacker).setDanmaku();
    }
}
