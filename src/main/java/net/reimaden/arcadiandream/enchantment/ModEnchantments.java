/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;

public class ModEnchantments {

    public static final Enchantment DANMAKU_PROTECTION = registerEnchantment("danmaku_protection",
            new DanmakuProtectionEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.ARMOR,
                    EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET));
    public static final Enchantment YOUKAI_BUSTER = registerEnchantment("youkai_buster",
            new YoukaiBusterEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.WEAPON,
                    EquipmentSlot.MAINHAND));

    private static Enchantment registerEnchantment(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(ArcadianDream.MOD_ID, name), enchantment);
    }

    public static void register() {
        ArcadianDream.LOGGER.debug("Registering enchantments for " + ArcadianDream.MOD_ID);
    }
}
