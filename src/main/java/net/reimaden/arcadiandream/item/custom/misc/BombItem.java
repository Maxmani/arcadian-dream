/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item.custom.misc;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.advancement.ModCriteria;
import net.reimaden.arcadiandream.entity.custom.danmaku.BaseBulletEntity;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.sound.ModSounds;
import net.reimaden.arcadiandream.statistic.ModStats;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BombItem extends Item {

    public BombItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (world.isClient()) return TypedActionResult.pass(itemStack);

        int cancelled = 0;

        for (BaseBulletEntity bulletEntity : world.getNonSpectatingEntities(BaseBulletEntity.class,
                user.getBoundingBox().expand(24.0, 24.0, 24.0))) {

            if (bulletEntity.getOwner() != user) {

                // Convert bullets into Star Items
                ItemEntity itemEntity = new ItemEntity(world, user.getX(), user.getZ(), user.getY(), new ItemStack(ModItems.STAR_ITEM));
                itemEntity.setPos(bulletEntity.getX(), bulletEntity.getY(), bulletEntity.getZ());
                world.spawnEntity(itemEntity);
                bulletEntity.discard();
                bulletEntity.cancelParticle((ServerWorld) world);
                cancelled++;

                // Get the ItemStack of the Star Items and give them to the player
                ItemStack item = itemEntity.getStack();
                user.sendPickup(itemEntity, 1);
                user.getInventory().offerOrDrop(item);
                itemEntity.discard();
            }
        }

        if (cancelled > 0) {
            user.sendMessage(Text.translatable("item." + ArcadianDream.MOD_ID + ".bomb_item.cancel", cancelled), true);
            world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.ITEM_BOMB_ITEM_USE, user.getSoundCategory(), 1f, 1f);
            ModCriteria.BULLETS_CANCELLED.trigger((ServerPlayerEntity) user, cancelled, false);
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            user.increaseStat(ModStats.BULLETS_CANCELLED, cancelled);

            if (!user.isCreative()) {
                itemStack.decrement(1);
            }

            user.getItemCooldownManager().set(this, 100);
            return TypedActionResult.success(itemStack);
        }

        // Pass if there are no bullets to cancel
        return TypedActionResult.pass(itemStack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item." + ArcadianDream.MOD_ID + ".bomb_item.tooltip"));

        super.appendTooltip(stack, world, tooltip, context);
    }
}
