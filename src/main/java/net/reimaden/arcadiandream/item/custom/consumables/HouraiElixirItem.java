/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item.custom.consumables;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.sound.ModSounds;
import net.reimaden.arcadiandream.util.DataSaver;
import net.reimaden.arcadiandream.util.IEntityDataSaver;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HouraiElixirItem extends Item {

    public HouraiElixirItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!ArcadianDream.CONFIG.houraiElixirOptions.canDrink()) {
            return TypedActionResult.pass(user.getStackInHand(hand));
        }
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        Hand hand = user.getActiveHand();
        IEntityDataSaver data = (IEntityDataSaver) user;
        byte elixir = data.arcadiandream$getPersistentData().getByte("elixir");
        ServerPlayerEntity player = (ServerPlayerEntity) user;

        if (!world.isClient()) {
            if (elixir < 3) {
                switch (elixir) {
                    case 0 -> user.sendMessage(Text.translatable("item." + ArcadianDream.MOD_ID + ".hourai_elixir.level_1"));
                    case 1 -> user.sendMessage(Text.translatable("item." + ArcadianDream.MOD_ID + ".hourai_elixir.level_2"));
                    case 2 -> user.sendMessage(Text.translatable("item." + ArcadianDream.MOD_ID + ".hourai_elixir.level_3"));
                    default -> throw new IllegalArgumentException("Invalid Elixir level");
                }

                player.playSound(ModSounds.ITEM_HOURAI_ELIXIR_USE, player.getSoundCategory(), 0.5f, world.random.nextFloat() * 0.1f + 0.9f);
            }
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 100));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 100));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100));

            DataSaver.addElixir(data, (byte) 1);
            player.incrementStat(Stats.USED.getOrCreateStat(this));

            stack.damage(1, user, e -> e.sendToolBreakStatus(hand));
        }

        return stack;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (!ArcadianDream.CONFIG.houraiElixirOptions.canDrink()) {
            tooltip.add(Text.translatable("item." + ArcadianDream.MOD_ID + ".hourai_elixir.tooltip_disabled"));
        } else {
            tooltip.add(Text.translatable("item." + ArcadianDream.MOD_ID + ".hourai_elixir.tooltip"));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override // This is necessary to prevent applying Mending through the anvil
    public boolean isDamageable() {
        return false;
    }
}
