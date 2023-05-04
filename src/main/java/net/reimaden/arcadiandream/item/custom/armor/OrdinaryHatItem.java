/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item.custom.armor;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.ArcadianDream;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OrdinaryHatItem extends OrdinaryArmorItem {

    public OrdinaryHatItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item." + ArcadianDream.MOD_ID + ".ordinary_hat.tooltip"));

        super.appendTooltip(stack, world, tooltip, context);
    }
}