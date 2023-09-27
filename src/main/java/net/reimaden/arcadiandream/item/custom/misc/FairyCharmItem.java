/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item.custom.misc;

import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Equipment;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FairyCharmItem extends TrinketItem implements Equipment {

    public FairyCharmItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (user.isSneaking()) {
            user.getItemCooldownManager().set(this, 20);

            NbtCompound nbt = stack.getOrCreateNbt();
            int currentMode = nbt.getInt("mode");
            int newMode;

            switch (currentMode) {
                default -> newMode = 1;
                case 1 -> newMode = 2;
                case 2 -> newMode = 0;
            }

            nbt.putInt("mode", newMode);
            String translationKey;

            switch (newMode) {
                default -> translationKey = "mode_pacify";
                case 1 -> translationKey = "mode_prevent";
                case 2 -> translationKey = "mode_both";
            }

            if (!world.isClient()) {
                user.playSound(ModSounds.ITEM_FAIRY_CHARM_USE, user.getSoundCategory(), 0.5f, (user.getRandom().nextFloat() - user.getRandom().nextFloat()) * 0.35f + 0.9f);
                user.sendMessage(Text.translatable(Util.createTranslationKey("item", new Identifier(ArcadianDream.MOD_ID, "fairy_charm.tooltip." + translationKey))), true);
            }

            return TypedActionResult.success(stack, world.isClient());
        }

        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = stack.getOrCreateNbt();

        int mode = nbt.getInt("mode");
        String translationKey;

        switch (mode) {
            default -> translationKey = "mode_pacify";
            case 1 -> translationKey = "mode_prevent";
            case 2 -> translationKey = "mode_both";
        }
        tooltip.add(Text.translatable(Util.createTranslationKey("item", new Identifier(ArcadianDream.MOD_ID, "fairy_charm.tooltip." + translationKey))));

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.MAINHAND;
    }
}
