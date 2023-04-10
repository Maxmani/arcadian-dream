/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item.custom.danmaku;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.stat.Stats;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.entity.custom.danmaku.BaseBulletEntity;
import net.reimaden.arcadiandream.sound.ModSounds;
import net.reimaden.arcadiandream.util.ColorMap;
import net.reimaden.arcadiandream.util.ModTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

public class BaseShotItem extends Item implements DyeableBullet, BulletPatterns {

    private final Random random = Random.create();

    // Shot properties, aka the initial stats
    private final float power;
    private final float speed;
    private final int duration;
    private final int cooldown;
    private final float gravity;
    private final float divergence;
    private final String pattern;
    private final int density;

    // Max values for the properties
    private final float maxPower;
    private final float maxSpeed;
    private final int maxDuration;
    private final int maxCooldown;
    private final float maxGravity;
    private final float maxDivergence;
    private final int maxDensity;

    // Dear God, please forgive me for this constructor
    public BaseShotItem(Settings settings, float power, float speed, int duration, int cooldown, float gravity,
                        float divergence, String pattern, int density, float maxPower, float maxSpeed, int maxDuration,
                        int maxCooldown, float maxDivergence, int maxDensity) {
        super(settings);
        this.power = power;
        this.speed = speed;
        this.duration = duration;
        this.cooldown = cooldown;
        this.gravity = gravity;
        this.divergence = divergence;
        this.pattern = pattern;
        this.density = density;

        this.maxPower = maxPower;
        this.maxSpeed = maxSpeed;
        this.maxDuration = maxDuration;
        this.maxCooldown = maxCooldown;
        this.maxDivergence = maxDivergence;
        this.maxDensity = maxDensity;

        // Gravity is a special case, because the difference between 0 and 1 is dumb
        this.maxGravity = 1f;
    }

    public static boolean isUsable(ItemStack stack) {
        return stack.getDamage() < stack.getMaxDamage() - 1;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        NbtCompound nbt = stack.getOrCreateNbt();

        if (!isUsable(stack)) {
            return TypedActionResult.pass(stack);
        }

        final int cooldown = nbt.getInt("cooldown") * ArcadianDream.CONFIG.danmakuCooldownMultiplier();

        user.playSound(ModSounds.ENTITY_DANMAKU_FIRE, getSoundVolume(), getSoundPitch(random));
        for (Item item : ModTags.SHOTS) {
            user.getItemCooldownManager().set(item, cooldown);
        }

        final int density = nbt.getInt("density");
        final float speed = nbt.getFloat("speed");
        final float divergence = nbt.getFloat("divergence");
        final float n = speed / density;

        if (!world.isClient()) {
            fireShot(world, user, stack, nbt, density, speed, divergence, n);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode && isUsable(stack)) {
            final int damage = 1 + density / 4;
            final int maxDamage = stack.getMaxDamage();
            final int currentDamage = stack.getDamage();
            stack.damage(damage >= maxDamage - currentDamage ? maxDamage - currentDamage - 1 : damage, user, e -> e.sendToolBreakStatus(hand));
        }

        return TypedActionResult.success(stack, world.isClient());
    }

    private void fireShot(World world, PlayerEntity user, ItemStack stack, NbtCompound nbt, int density, float speed, float divergence, float n) {
        switch (nbt.getString("pattern").toLowerCase()) {
            case "spread" -> createSpread(world, user, stack, density, speed, divergence);
            case "ray" -> createRay(world, user, stack, density, speed, divergence, n);
            case "ring" -> createRing(world, user, stack, density, speed, divergence);
            case "cone" -> createCone(world, user, stack, density, speed, divergence);
            case "double" -> createDouble(world, user, stack, density, speed, divergence, n);
            case "triple" -> createTriple(world, user, stack, density, speed, divergence, n);
            default -> throw new IllegalArgumentException("No valid bullet pattern found!");
        }
    }

    public static float getSoundPitch(Random random) {
        return 1.0f + (random.nextFloat() - 0.5f) * 0.1f;
    }

    public static float getSoundVolume() {
        return 1.0f;
    }

    @Override
    public void postProcessNbt(NbtCompound nbt) {
        super.postProcessNbt(nbt);

        String[] keys = {"power", "speed", "duration", "cooldown", "gravity", "divergence", "pattern", "density"};
        Object[] values = {power, speed, duration, cooldown, gravity, divergence, pattern, density};

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

        super.appendTooltip(stack, world, tooltip, context);
    }

    private void nbtTooltip(ItemStack stack, List<Text> tooltip) {
        NbtCompound nbt = stack.getNbt();

        if (nbt != null) {
            NbtCompound nbtDisplay = nbt.getCompound(DISPLAY_KEY);

            float power = nbt.getFloat("power");
            float speed = nbt.getFloat("speed");
            int duration = nbt.getInt("duration");
            int cooldown = nbt.getInt("cooldown");
            float gravity = nbt.getFloat("gravity");
            float divergence = nbt.getFloat("divergence");
            String pattern = nbt.getString("pattern");
            int density = nbt.getInt("density");

            String keyPrefix = "item." + ArcadianDream.MOD_ID + ".shot.tooltip_";
            String formattedPower = formatFloatValue(power);
            String formattedSpeed = formatFloatValue(speed);
            String formattedGravity = formatFloatValue(gravity);
            String formattedDivergence = formatFloatValue(divergence);
            String formattedColor = ColorMap.matchesMap(getColor(stack)) ? getColorName(stack).getString() : String.format(Locale.ROOT, "#%06X", nbtDisplay.getInt(COLOR_KEY));

            tooltip.add(Text.translatable(keyPrefix + "power", formattedPower));
            tooltip.add(Text.translatable(keyPrefix + "speed", formattedSpeed));
            tooltip.add(Text.translatable(keyPrefix + "duration", (float) duration / 20));
            tooltip.add(Text.translatable(keyPrefix + "cooldown", ((float) cooldown / 20) * ArcadianDream.CONFIG.danmakuCooldownMultiplier()));
            tooltip.add(Text.translatable(keyPrefix + "gravity", formattedGravity));
            tooltip.add(Text.translatable(keyPrefix + "divergence", formattedDivergence));
            tooltip.add(Text.translatable(keyPrefix + "pattern", Text.translatable("item." + ArcadianDream.MOD_ID + ".bullet.pattern_" + pattern.toLowerCase())));
            tooltip.add(Text.translatable(keyPrefix + "density", density));
            tooltip.add(Text.translatable(keyPrefix + "color", formattedColor).setStyle(Style.EMPTY.withColor(getColor(stack))));
        }
    }

    private static String formatFloatValue(float value) {
        int intValue = (int) value;
        return intValue == value ? Integer.toString(intValue) : String.format("%.2f", value);
    }

    private MutableText getColorName(ItemStack stack) {
        int color = getColor(stack);
        return ColorMap.getTranslationKey(color);
    }

    @Override
    public @NotNull BaseBulletEntity getBullet(World world, LivingEntity user) {
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

    /* These aren't traditional getters and setters, but they're used to set the values of the NBT tags */

    private void setParamFloat(ItemStack stack, String key, float value, float maxValue) {
        stack.getOrCreateNbt().putFloat(key, Math.min(value, maxValue));
    }

    private void setParamInt(ItemStack stack, String key, int value, int maxValue) {
        stack.getOrCreateNbt().putInt(key, Math.min(value, maxValue));
    }

    public void setPower(ItemStack stack, float power) {
        setParamFloat(stack, "power", power, maxPower);
    }

    public void setSpeed(ItemStack stack, float speed) {
        setParamFloat(stack, "speed", speed, maxSpeed);
    }

    public void setDuration(ItemStack stack, int duration) {
        setParamInt(stack, "duration", duration, maxDuration);
    }

    public void setCooldown(ItemStack stack, int cooldown) {
        setParamInt(stack, "cooldown", cooldown, maxCooldown);
    }

    @SuppressWarnings("unused")
    public void setGravity(ItemStack stack, float gravity) {
        setParamFloat(stack, "gravity", gravity, maxGravity);
    }

    @SuppressWarnings("unused")
    public void setDivergence(ItemStack stack, float divergence) {
        setParamFloat(stack, "divergence", divergence, maxDivergence);
    }

    public void setPattern(ItemStack stack, String pattern) {
        stack.getOrCreateNbt().putString("pattern", pattern);
    }

    public void setDensity(ItemStack stack, int density) {
        setParamInt(stack, "density", density, maxDensity);
    }

    private float getParamFloat(ItemStack stack, String key) {
        return stack.getOrCreateNbt().getFloat(key);
    }

    private int getParamInt(ItemStack stack, String key) {
        return stack.getOrCreateNbt().getInt(key);
    }

    public float getPower(ItemStack stack) {
        return getParamFloat(stack, "power");
    }

    public float getSpeed(ItemStack stack) {
        return getParamFloat(stack, "speed");
    }

    public int getDuration(ItemStack stack) {
        return getParamInt(stack, "duration");
    }

    public int getCooldown(ItemStack stack) {
        return getParamInt(stack, "cooldown");
    }

    @SuppressWarnings("unused")
    public float getGravity(ItemStack stack) {
        return getParamFloat(stack, "gravity");
    }

    @SuppressWarnings("unused")
    public float getDivergence(ItemStack stack) {
        return getParamFloat(stack, "divergence");
    }

    public int getDensity(ItemStack stack) {
        return getParamInt(stack, "density");
    }

    public float getMaxPower() {
        return maxPower;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public int getMaxDuration() {
        return maxDuration;
    }

    @SuppressWarnings("unused")
    public float getMaxGravity() {
        return maxGravity;
    }

    @SuppressWarnings("unused")
    public float getMaxDivergence() {
        return maxDivergence;
    }

    public int getMaxDensity() {
        return maxDensity;
    }
}
