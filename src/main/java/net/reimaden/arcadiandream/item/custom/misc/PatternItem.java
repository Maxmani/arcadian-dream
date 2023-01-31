/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item.custom.misc;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.ArcadianDream;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PatternItem extends PatternTemplateItem {

    public PatternItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (patternName == null) {
            String itemName = getTranslationKey();
            int underscoreIndex = itemName.indexOf("_");
            patternName = itemName.substring(itemName.lastIndexOf(".") + 1, underscoreIndex);
        }

        tooltip.add(Text.translatable("item." + ArcadianDream.MOD_ID + "." + patternName + "_pattern" + ".tooltip"));
    }
}
