package net.reimaden.arcadiandream.item.custom;

import net.reimaden.arcadiandream.sound.ModSounds;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("ConstantConditions")
public class BaseBulletItem extends Item implements DyeableItem{

    public BaseBulletItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.ENTITY_DANMAKU_FIRE, SoundCategory.PLAYERS, 1f, 1f);
        user.getItemCooldownManager().set(this, itemStack.getNbt().getInt("Cooldown"));

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient);
    }

    @Override //TODO: Initialize NBT in a better way
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!stack.hasNbt()) {
            stack.getOrCreateNbt().putFloat("Speed", getSpeed());
            stack.getOrCreateNbt().putInt("Power", getPower());
            stack.getOrCreateNbt().putInt("Duration", getMaxAge());
            stack.getOrCreateNbt().putInt("Cooldown", getCooldown());
            stack.getOrCreateNbt().putFloat("Gravity", getGravity());
            stack.getOrCreateNbt().putInt("Density", getDensity());
            stack.getOrCreateNbt().putString("Pattern", getPattern());
            stack.getOrCreateNbt().putInt("Stack", getStack());
            stack.getOrCreateSubNbt(DISPLAY_KEY).putInt(COLOR_KEY, 11546150);

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
                tooltip.add(Text.translatable("item.arcadiandream.bullet.tooltip"));
            }
        }
    }

    @Override
    public int getColor(ItemStack stack) {
        NbtCompound nbtCompound = stack.getSubNbt(DISPLAY_KEY);
        if (nbtCompound != null && nbtCompound.contains(COLOR_KEY, NbtElement.NUMBER_TYPE)) {
            return nbtCompound.getInt(COLOR_KEY);
        }
        return 11546150;
    }

    private void nbtTooltip(ItemStack stack, List<Text> tooltip) {
        float currentSpeed = stack.getNbt().getFloat("Speed");
        int currentPower = stack.getNbt().getInt("Power");
        int currentMaxAge = stack.getNbt().getInt("Duration");
        int currentCooldown = stack.getNbt().getInt("Cooldown");
        float currentGravity = stack.getNbt().getFloat("Gravity");
        int currentDensity = stack.getNbt().getInt("Density");
        String currentPattern = stack.getNbt().getString("Pattern");
        int currentStack = stack.getNbt().getInt("Stack");
        tooltip.add(Text.translatable("item.arcadiandream.bullet.tooltip_speed", currentSpeed));
        tooltip.add(Text.translatable("item.arcadiandream.bullet.tooltip_power", currentPower));
        tooltip.add(Text.translatable("item.arcadiandream.bullet.tooltip_duration", ((float)currentMaxAge / 20)));
        tooltip.add(Text.translatable("item.arcadiandream.bullet.tooltip_cooldown", ((float)currentCooldown / 20)));
        tooltip.add(Text.translatable("item.arcadiandream.bullet.tooltip_gravity", currentGravity));
        tooltip.add(Text.translatable("item.arcadiandream.bullet.tooltip_density", currentDensity));
        tooltip.add(Text.translatable("item.arcadiandream.bullet.tooltip_pattern", currentPattern));
        tooltip.add(Text.translatable("item.arcadiandream.bullet.tooltip_stack", currentStack));
        tooltip.add(Text.translatable("item.arcadiandream.bullet.tooltip_amount", currentDensity * currentStack));
    }

    public float getSpeed() {
        return 0.0f;
    }

    public int getPower() {
        return 0;
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

    public int getDensity() {
        return 0;
    }

    public String getPattern() {
        return "None";
    }

    public int getStack() {
        return 0;
    }
}
