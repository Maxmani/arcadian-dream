/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item.custom.danmaku;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.entity.custom.BaseBulletEntity;
import net.reimaden.arcadiandream.sound.ModSounds;
import net.reimaden.arcadiandream.util.ColorMap;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseShotItem extends Item implements DyeableBullet, BulletPatterns {

    private final int power;
    private final float speed;
    private final int maxAge;
    private final int cooldown;
    private final float gravity;
    private final float divergence;
    private final String pattern;
    private final int density;

    public BaseShotItem(Settings settings, int power, float speed, int maxAge, int cooldown, float gravity,
                        float divergence, String pattern, int density) {
        super(settings);
        this.power = power;
        this.speed = speed;
        this.maxAge = maxAge;
        this.cooldown = cooldown;
        this.gravity = gravity;
        this.divergence = divergence;
        this.pattern = pattern;
        this.density = density;
    }

    private static boolean isUsable(ItemStack stack) {
        return stack.getDamage() < stack.getMaxDamage() - 1;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        NbtCompound nbt = stack.getOrCreateNbt();

        if (!isUsable(stack)) {
            return TypedActionResult.pass(stack);
        }

        world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.ENTITY_DANMAKU_FIRE, SoundCategory.PLAYERS, 1f, 1f);
        user.getItemCooldownManager().set(this, nbt.getInt("cooldown") * ArcadianDream.CONFIG.danmakuCooldownMultiplier());

        int density = nbt.getInt("density");
        float speed = nbt.getFloat("speed");
        float divergence = nbt.getFloat("divergence");
        float n = speed / density;

        if (!world.isClient()) {
            switch (nbt.getString("pattern").toLowerCase()) {
                case "spread" -> createSpread(world, user, stack, density, speed, divergence);
                case "ray" -> createRay(world, user, stack, speed, divergence, n, density);
                case "ring" -> createRing(world, user, stack, density, speed, divergence);
                default -> throw new IllegalArgumentException("No valid bullet pattern found!");
            }
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode && isUsable(stack)) {
            stack.damage(1, user, e -> e.sendToolBreakStatus(hand));
        }

        return TypedActionResult.success(stack, world.isClient());
    }

    @Override
    public void postProcessNbt(NbtCompound nbt) {
        super.postProcessNbt(nbt);

        String[] keys = {"power", "speed", "duration", "cooldown", "gravity", "divergence", "pattern", "density"};
        Object[] values = {power, speed, maxAge, cooldown, gravity, divergence, pattern, density};

        // Set default values
        for (int i = 0; i < keys.length; i++) {
            if (!nbt.contains(keys[i])) {
                if (values[i] instanceof Integer) {
                    nbt.putInt(keys[i], (int) values[i]);
                } else if (values[i] instanceof Float) {
                    nbt.putFloat(keys[i], (float) values[i]);
                } else if (values[i] instanceof String) {
                    nbt.putString(keys[i], (String) values[i]);
                }
            }
        }

        hideDyeSection(nbt);
    }

    private void hideDyeSection(NbtCompound nbt) {
        nbt.putInt("HideFlags", nbt.getInt("HideFlags") | ItemStack.TooltipSection.DYE.getFlag());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasNbt()) {
            if (Screen.hasShiftDown()) {
                nbtTooltip(stack, tooltip);
            } else {
                tooltip.add(Text.translatable("item." + ArcadianDream.MOD_ID + ".shot.tooltip"));
            }
        }
    }

    private void nbtTooltip(ItemStack stack, List<Text> tooltip) {
        NbtCompound nbt = stack.getNbt();

        if (nbt != null) {
            int power = nbt.getInt("power");
            float speed = nbt.getFloat("speed");
            int maxAge = nbt.getInt("duration");
            int cooldown = nbt.getInt("cooldown");
            float gravity = nbt.getFloat("gravity");
            float divergence = nbt.getFloat("divergence");
            String pattern = nbt.getString("pattern");
            int density = nbt.getInt("density");

            String keyPrefix = "item." + ArcadianDream.MOD_ID + ".shot.tooltip_";
            tooltip.add(Text.translatable(keyPrefix + "power", power));
            tooltip.add(Text.translatable(keyPrefix + "speed", speed));
            tooltip.add(Text.translatable(keyPrefix + "duration", (float) maxAge / 20));
            tooltip.add(Text.translatable(keyPrefix + "cooldown", ((float) cooldown / 20) * ArcadianDream.CONFIG.danmakuCooldownMultiplier()));
            tooltip.add(Text.translatable(keyPrefix + "gravity", gravity));
            tooltip.add(Text.translatable(keyPrefix + "divergence", divergence));
            tooltip.add(Text.translatable(keyPrefix + "pattern", Text.translatable("item." + ArcadianDream.MOD_ID + ".bullet.pattern_" + pattern.toLowerCase())));
            tooltip.add(Text.translatable(keyPrefix + "density", density));
            tooltip.add(Text.translatable(keyPrefix + "color", getColorName(stack).getString()).setStyle(Style.EMPTY.withColor(getColor(stack))));
        }
    }

    private MutableText getColorName(ItemStack stack) {
        int color = getColor(stack);
        return ColorMap.getTranslationKey(color);
    }

    @Override
    public ThrownItemEntity getBullet(World world, LivingEntity user) {
        return new BaseBulletEntity(world, user);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override // Like the Hourai Elixir, this is necessary to prevent applying Mending through the anvil
    public boolean isDamageable() {
        return false;
    }
}
