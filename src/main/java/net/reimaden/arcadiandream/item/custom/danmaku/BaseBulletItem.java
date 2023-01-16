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

    private final int power;
    private final float speed;
    private final int maxAge;
    private final int cooldown;
    private final float gravity;
    private final float divergence;
    private final String pattern;
    private final int density;
    private final int stack;

    public BaseBulletItem(Settings settings, int power, float speed, int maxAge, int cooldown, float gravity,
                          float divergence, String pattern, int density, int stack) {
        super(settings);
        this.power = power;
        this.speed = speed;
        this.maxAge = maxAge;
        this.cooldown = cooldown;
        this.gravity = gravity;
        this.divergence = divergence;
        this.pattern = pattern;
        this.density = density;
        this.stack = stack;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        NbtCompound nbt = itemStack.getOrCreateNbt();

        world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.ENTITY_DANMAKU_FIRE, SoundCategory.PLAYERS, 1f, 1f);
        user.getItemCooldownManager().set(this, nbt.getInt("cooldown") * ArcadianDream.CONFIG.danmakuCooldownMultiplier());

        int density = nbt.getInt("density");
        int stack = nbt.getInt("stack");
        int modifier = nbt.getInt("modifier");
        float speed = nbt.getFloat("speed");
        float divergence = nbt.getFloat("divergence");
        float n = speed / stack;

        if (!world.isClient) {
            switch (nbt.getString("pattern").toLowerCase()) {
                case "spread" -> createSpread(world, user, itemStack, density, speed, divergence);
                case "ray" -> createRay(world, user, itemStack, speed, divergence, n, stack);
                case "ring" -> createRing(world, user, itemStack, density, stack, speed, modifier, n, divergence);
                default -> throw new IllegalArgumentException("No valid bullet pattern found!");
            }
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient);
    }

    @Override
    public void postProcessNbt(NbtCompound nbt) {
        super.postProcessNbt(nbt);

        // Set default values
        nbt.putInt("power", power);
        nbt.putFloat("speed", speed);
        nbt.putInt("duration", maxAge);
        nbt.putInt("cooldown", cooldown);
        nbt.putFloat("gravity", gravity);
        nbt.putFloat("divergence", divergence);
        nbt.putString("pattern", pattern);
        nbt.putInt("density", density);
        nbt.putInt("stack", stack);
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
}
