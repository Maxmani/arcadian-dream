/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.enchantment;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalEnchantmentTags;
import net.fabricmc.fabric.api.tag.convention.v1.TagUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.reimaden.arcadiandream.util.ModTags;

public class DanmakuProtectionEnchantment extends Enchantment {

    public DanmakuProtectionEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot... slots) {
        super(weight, type, slots);
    }

    @Override
    public int getProtectionAmount(int level, DamageSource source) {
        if (source.isIn(ModTags.DamageTypes.IS_DANMAKU)) {
            return level * 2;
        }

        return super.getProtectionAmount(level, source);
    }

    @Override
    public int getMinPower(int level) {
        return level * 12;
    }

    @Override
    public int getMaxPower(int level) {
        return getMinPower(level) + 24;
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        if ((other instanceof ProtectionEnchantment && (((ProtectionEnchantment) other).protectionType != ProtectionEnchantment.Type.FALL))
                || (TagUtil.isIn(ConventionalEnchantmentTags.ENTITY_DEFENSE_ENHANCEMENT, other) && (other == Enchantments.FEATHER_FALLING && other == Enchantments.RESPIRATION))) {
            return false;
        }

        return super.canAccept(other);
    }
}
