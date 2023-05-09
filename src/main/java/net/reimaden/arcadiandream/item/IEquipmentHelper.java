/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.event.GameEvent;

/**
 * Workaround for trinkets not having an equip sound without implementing {@link net.minecraft.item.Equipment},
 * causing a crash when attempting to quick move to an occupied slot.
 */
public interface IEquipmentHelper {

    default void playEquipSound(ItemStack stack, LivingEntity entity) {
        if (!stack.isEmpty() && entity.age > 1) {
            entity.emitGameEvent(GameEvent.EQUIP);
            entity.playSound(getEquipSound(), 1.0f, 1.0f);
        }
    }

    default SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
    }
}
