/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item.custom.tools;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.sound.ModSounds;

import java.util.List;

public interface HihiirokaneToolItem {

    static TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand, Item item) {
        ItemStack stack = user.getStackInHand(hand);

        if (!user.isSneaking() || !user.getOffHandStack().isEmpty()) return TypedActionResult.pass(stack);

        user.getItemCooldownManager().set(item, 20);

        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putBoolean("autosmelt", nbt.contains("autosmelt") && !nbt.getBoolean("autosmelt"));

        if (!world.isClient()) {
            user.playSound(nbt.getBoolean("autosmelt") ? ModSounds.ITEM_HIHIIROKANE_AUTOSMELT_ON : ModSounds.ITEM_HIHIIROKANE_AUTOSMELT_OFF,
                    user.getSoundCategory(), 0.5f, 1.2f);

            String translationKey = Util.createTranslationKey("item", new Identifier(ArcadianDream.MOD_ID,
                    nbt.getBoolean("autosmelt") ? "hihiirokane.tooltip.autosmelt_on" : "hihiirokane.tooltip.autosmelt_off"));
            user.sendMessage(Text.translatable(translationKey), true);
        }

        return TypedActionResult.success(stack, world.isClient());
    }

    static void appendTooltip(ItemStack stack, List<Text> tooltip) {
        NbtCompound nbt = stack.getOrCreateNbt();
        boolean autosmeltEnabled = !nbt.contains("autosmelt") || nbt.getBoolean("autosmelt");
        String translationKey = Util.createTranslationKey("item", new Identifier(ArcadianDream.MOD_ID,
                autosmeltEnabled ? "hihiirokane.tooltip.autosmelt_on" : "hihiirokane.tooltip.autosmelt_off"));
        tooltip.add(Text.translatable(translationKey));
    }
}
