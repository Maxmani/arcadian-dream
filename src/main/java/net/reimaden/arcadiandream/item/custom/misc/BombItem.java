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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.entity.custom.danmaku.BaseBulletEntity;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.sound.ModSounds;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BombItem extends Item {

    public BombItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack itemStack = user.getStackInHand(hand);

        int cancelled = 0;
        int stars = 0;

        if (!world.isClient) {
            for (BaseBulletEntity bulletEntity : world.getNonSpectatingEntities(BaseBulletEntity.class,
                    new Box(user.getX(), user.getY(), user.getZ(), user.getX(), user.getY(), user.getZ()).expand(24.0, 24.0, 24.0))) {

                if (bulletEntity.getOwner() != user) {

                    // Convert bullets into Star Items
                    ItemEntity itemEntity = new ItemEntity(world, user.getX(), user.getZ(), user.getY(), new ItemStack(ModItems.STAR_ITEM));
                    itemEntity.setPos(bulletEntity.getX(), bulletEntity.getY(), bulletEntity.getZ());
                    world.spawnEntity(itemEntity);
                    bulletEntity.kill();
                    bulletEntity.cancelParticle((ServerWorld) world);
                    cancelled++;

                    // Get the ItemStack of the Star Items
                    // and give them to the player
                    ItemStack item = itemEntity.getStack();
                    if (Random.create().nextBoolean()) { // 50% of the time
                        user.sendPickup(itemEntity, 1);
                        user.getInventory().offerOrDrop(item);
                        stars++;
                    }
                    itemEntity.discard();
                }
            }
            if (cancelled > 0) {
                user.sendMessage(Text.translatable("item." + ArcadianDream.MOD_ID + ".bomb_item.cancel", cancelled, stars), true);
                world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.ITEM_BOMB_ITEM_USE, user.getSoundCategory(), 1f, 1f);
            }
        }

        if (cancelled > 0) {
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!user.getAbilities().creativeMode) {
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
