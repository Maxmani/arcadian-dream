/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.enchantment;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalEnchantmentTags;
import net.fabricmc.fabric.api.tag.convention.v1.TagUtil;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.reimaden.arcadiandream.entity.ModEntities;

public class YoukaiBusterEnchantment extends Enchantment {

    public YoukaiBusterEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot... slots) {
        super(weight, target, slots);
    }

    @Override
    public int getMinPower(int level) {
        return 5 + (level - 1) * 8;
    }

    @Override
    public int getMaxPower(int level) {
        return getMinPower(level) + 20;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public float getAttackDamage(int level, EntityGroup group) {
        if (group == ModEntities.EntityGroups.YOUKAI) {
            return (float)level * 2.5f;
        }
        return 0.0f;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return super.canAccept(other) && !(other instanceof DamageEnchantment || TagUtil.isIn(ConventionalEnchantmentTags.WEAPON_DAMAGE_ENHANCEMENT, other));
    }
}
