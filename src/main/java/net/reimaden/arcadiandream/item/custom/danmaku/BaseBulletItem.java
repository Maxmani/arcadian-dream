/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item.custom.danmaku;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.entity.custom.BaseBulletEntity;
import net.reimaden.arcadiandream.sound.ModSounds;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseBulletItem extends Item implements DyeableBullet, BulletPatterns {

    public BaseBulletItem(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        NbtCompound nbt = itemStack.getNbt();

        world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.ENTITY_DANMAKU_FIRE, SoundCategory.PLAYERS, 1f, 1f);
        user.getItemCooldownManager().set(this, nbt.getInt("cooldown") * ArcadianDream.CONFIG.danmakuCooldownMultiplier());

        int density = nbt.getInt("density");
        int stack = nbt.getInt("stack");
        int modifier = nbt.getInt("modifier");
        float speed = nbt.getFloat("speed");
        float divergence = nbt.getFloat("divergence");
        float angle = 0;
        float x = 0;
        float y = 0;
        float s = 0;
        float n = speed / stack;

        if (!world.isClient) {
            switch (nbt.getString("pattern").toLowerCase()) {
                case "spread" -> createSpread(world, user, itemStack, density, speed, divergence);
                case "ring" -> createRing(world, user, itemStack, density, stack, angle, speed, s, modifier, x, y, n, divergence);
                default -> throw new IllegalArgumentException("No valid bullet pattern found!");
            }
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient);
    }

    @Override // TODO: Initialize NBT in a better way -> Item#getDefaultStack
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!stack.hasNbt()) {
            NbtCompound nbt = stack.getOrCreateNbt();

            nbt.putInt("power", getPower());
            nbt.putFloat("speed", getSpeed());
            nbt.putInt("duration", getMaxAge());
            nbt.putInt("cooldown", getCooldown());
            nbt.putFloat("gravity", getGravity());
            nbt.putFloat("divergence", getDivergence());
            nbt.putString("pattern", getPattern());
            nbt.putInt("density", getDensity());
            nbt.putInt("stack", getStack());

            // Hide the "dyed" line
            stack.addHideFlag(ItemStack.TooltipSection.DYE);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasNbt()) {
            if (Screen.hasShiftDown()) {
                nbtTooltip(stack, tooltip);
            } else {
                tooltip.add(Text.translatable("item." + ArcadianDream.MOD_ID + ".bullet.tooltip"));
            }
        }
    }

    @SuppressWarnings("DataFlowIssue")
    private void nbtTooltip(ItemStack stack, List<Text> tooltip) {
        NbtCompound nbt = stack.getNbt();

        int currentPower = nbt.getInt("power");
        float currentSpeed = nbt.getFloat("speed");
        int currentMaxAge = nbt.getInt("duration");
        int currentCooldown = nbt.getInt("cooldown");
        float currentGravity = nbt.getFloat("gravity");
        float currentDivergence = nbt.getFloat("divergence");
        String currentPattern = nbt.getString("pattern");
        int currentDensity = nbt.getInt("density");
        int currentStack = nbt.getInt("stack");

        tooltip.add(Text.translatable("item." + ArcadianDream.MOD_ID + ".bullet.tooltip_power", currentPower));
        tooltip.add(Text.translatable("item." + ArcadianDream.MOD_ID + ".bullet.tooltip_speed", currentSpeed));
        tooltip.add(Text.translatable("item." + ArcadianDream.MOD_ID + ".bullet.tooltip_duration", (float)currentMaxAge / 20));
        tooltip.add(Text.translatable("item." + ArcadianDream.MOD_ID + ".bullet.tooltip_cooldown",
                ((float)currentCooldown / 20) * ArcadianDream.CONFIG.danmakuCooldownMultiplier()));
        tooltip.add(Text.translatable("item." + ArcadianDream.MOD_ID + ".bullet.tooltip_gravity", currentGravity));
        tooltip.add(Text.translatable("item." + ArcadianDream.MOD_ID + ".bullet.tooltip_divergence", currentDivergence));
        tooltip.add(Text.translatable("item." + ArcadianDream.MOD_ID + ".bullet.tooltip_pattern", currentPattern));
        tooltip.add(Text.translatable("item." + ArcadianDream.MOD_ID + ".bullet.tooltip_density", currentDensity));
        tooltip.add(Text.translatable("item." + ArcadianDream.MOD_ID + ".bullet.tooltip_stack", currentStack));
        tooltip.add(Text.translatable("item." + ArcadianDream.MOD_ID + ".bullet.tooltip_amount", currentDensity * currentStack));
    }

    @Override
    public ThrownItemEntity getBullet(World world, LivingEntity user) {
        return new BaseBulletEntity(world, user);
    }

    public int getPower() {
        return 0;
    }

    public float getSpeed() {
        return 0.0f;
    }

    public int getMaxAge() {
        return 0;
    }

    public int getCooldown() {
        return 0;
    }

    public float getGravity() {
        return 0.0f;
    }

    public float getDivergence() {
        return 0.0f;
    }

    public String getPattern() {
        return "spread";
    }

    public int getDensity() {
        return 0;
    }

    public int getStack() {
        return 0;
    }
}
