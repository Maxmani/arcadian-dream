/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.reimaden.arcadiandream.item.ModItems;

public class IcicleSwordEvent implements ServerLivingEntityEvents.AllowDamage {

    @Override
    public boolean allowDamage(LivingEntity entity, DamageSource source, float amount) {
        if (source.getAttacker() == null || !(source.getAttacker() instanceof LivingEntity livingEntity)) return true;

        if (livingEntity.getMainHandStack().isOf(ModItems.ICICLE_SWORD)) {
            int frozenTicks = entity.getFrozenTicks();
            entity.setFrozenTicks((int) Math.min(entity.getMinFreezeDamageTicks() * 4, frozenTicks + 10 * amount));
        }

        return true;
    }
}
