/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item.custom.misc;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.sound.ModSounds;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IbukiGourdItem extends Item {

    public IbukiGourdItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if ((stack.hasNbt() && !(stack.getNbt() != null && stack.getNbt().getBoolean("filled"))) || !stack.hasNbt()) {
            tooltip.add(Text.translatable("item." + ArcadianDream.MOD_ID + ".ibuki_gourd.tooltip"));
        }

        super.appendTooltip(stack, world, tooltip, context);
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
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (stack.hasNbt() && (stack.getNbt() != null && stack.getNbt().getBoolean("filled"))) {
            return ItemUsage.consumeHeldItem(world, user, hand);
        }

        BlockHitResult hitResult = IbukiGourdItem.raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (((HitResult)hitResult).getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(stack);
        }
        if (((HitResult)hitResult).getType() == HitResult.Type.BLOCK) {
            BlockPos pos = hitResult.getBlockPos();
            if (!world.canPlayerModifyAt(user, pos)) {
                return TypedActionResult.pass(stack);
            }
            if (world.getFluidState(pos).isIn(FluidTags.WATER)) {
                world.playSound(user, user.getX(), user.getY(), user.getZ(), ModSounds.ITEM_IBUKI_GOURD_FILL, SoundCategory.NEUTRAL, 1.0f, 0.9f);
                world.emitGameEvent(user, GameEvent.FLUID_PICKUP, pos);
                stack.getOrCreateNbt().putBoolean("filled", true);
                return TypedActionResult.success(stack);
            }
        }

        return TypedActionResult.pass(stack);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient()) {
            if (user instanceof PlayerEntity playerEntity && !playerEntity.isCreative()) {
                stack.getOrCreateNbt().putBoolean("filled", false);
            }
            user.emitGameEvent(GameEvent.DRINK);
            effects(user);
        }
        if (user instanceof ServerPlayerEntity serverPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }
        return stack;
    }

    private static void effects(LivingEntity user) {
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 400));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 600));
    }
}
