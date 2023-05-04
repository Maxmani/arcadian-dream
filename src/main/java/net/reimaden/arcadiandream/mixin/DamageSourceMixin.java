/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.reimaden.arcadiandream.util.IDamageSource;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DamageSource.class)
public class DamageSourceMixin implements IDamageSource {

    private boolean danmaku;

    @Override
    public boolean isDanmaku() {
        return this.danmaku;
    }

    @Override
    public DamageSource setDanmaku() {
        this.danmaku = true;
        return (DamageSource) (Object) this;
    }
}