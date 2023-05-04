/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.util;

import net.minecraft.entity.damage.DamageSource;

public interface IDamageSource {

    default boolean isDanmaku() {
        return false;
    }

    default DamageSource setDanmaku() {
        return null;
    }
}
