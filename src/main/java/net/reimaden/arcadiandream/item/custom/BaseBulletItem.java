package net.reimaden.arcadiandream.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.entity.custom.BaseBulletEntity;
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

@SuppressWarnings({"ConstantConditions", "ParameterCanBeLocal"})
public class BaseBulletItem extends Item implements DyeableItem{

    public BaseBulletItem(Settings settings) {
        super(settings);
    }

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
        float angle = 0;
        float x = 0;
        float y = 0;
        float s = 0;
        float n = speed / stack;

        if (!world.isClient) {
            switch (nbt.getString("pattern")) {
                case "none" -> createSpread(world, user, itemStack, density, speed);
                case "ring" -> createRing(world, user, itemStack, density, stack, angle, speed, s, modifier, x, y, n);
                default -> throw new IllegalArgumentException("No valid bullet pattern found!");
            }
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient);
    }

    private void createSpread(World world, PlayerEntity user, ItemStack itemStack, int density, float speed) {
        for (int i = 0; i < density; i++) {
            ThrownItemEntity bulletEntity = getBullet(world, user);
            bulletEntity.setItem(itemStack);
            bulletEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, speed, 0.0f + density - 1);
            world.spawnEntity(bulletEntity);
        }
    }

    private void createRing(World world, PlayerEntity user, ItemStack itemStack,
                            int density, int stack, float angle, float speed, float s, int modifier, float x, float y, float n) {
        for (int i = 0; i < stack; i++) {
            for (int h = 0; h < density; h++) {
                ThrownItemEntity bulletEntity = getBullet(world, user);
                bulletEntity.setItem(itemStack);

                angle = (h * (360 / (float)density)) + 180;
                s = speed;
                if (modifier != 0) {
                    modifier = 1 + modifier;
                    x = MathHelper.cos(angle);
                    y = MathHelper.sin(angle) / 2; //replace 2 with modifier when modifier is correctly programmed in.
                    angle = (float)MathHelper.atan2(y, x) * MathHelper.DEGREES_PER_RADIAN;
                    s = MathHelper.sqrt(MathHelper.square(x) + MathHelper.square(y)) * speed;
                }
                Vec3d bullet = new Vec3d(0, 0, 1);
                bullet = bullet.rotateY((angle) * MathHelper.RADIANS_PER_DEGREE);
                bullet = bullet.rotateX(user.getPitch() * MathHelper.RADIANS_PER_DEGREE);
                bullet = bullet.rotateY((-user.getYaw() + 180) * MathHelper.RADIANS_PER_DEGREE);

                bulletEntity.setVelocity(bullet.getX(),
                        bullet.getY(), bullet.getZ(), s, 0.0f);

                world.spawnEntity(bulletEntity);
            }

            speed = speed - n;
        }
    }

    @Override //TODO: Initialize NBT in a better way
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!stack.hasNbt()) {
            stack.getOrCreateNbt().putFloat("speed", getSpeed());
            stack.getOrCreateNbt().putInt("power", getPower());
            stack.getOrCreateNbt().putInt("duration", getMaxAge());
            stack.getOrCreateNbt().putInt("cooldown", getCooldown());
            stack.getOrCreateNbt().putFloat("gravity", getGravity());
            stack.getOrCreateNbt().putInt("density", getDensity());
            stack.getOrCreateNbt().putString("pattern", getPattern());
            stack.getOrCreateNbt().putInt("stack", getStack());
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
        float currentSpeed = stack.getNbt().getFloat("speed");
        int currentPower = stack.getNbt().getInt("power");
        int currentMaxAge = stack.getNbt().getInt("duration");
        int currentCooldown = stack.getNbt().getInt("cooldown");
        float currentGravity = stack.getNbt().getFloat("gravity");
        int currentDensity = stack.getNbt().getInt("density");
        String currentPattern = stack.getNbt().getString("pattern");
        int currentStack = stack.getNbt().getInt("stack");
        tooltip.add(Text.translatable("item.arcadiandream.bullet.tooltip_speed", currentSpeed));
        tooltip.add(Text.translatable("item.arcadiandream.bullet.tooltip_power", currentPower));
        tooltip.add(Text.translatable("item.arcadiandream.bullet.tooltip_duration", (float)currentMaxAge / 20));
        tooltip.add(Text.translatable("item.arcadiandream.bullet.tooltip_cooldown", ((float)currentCooldown / 20) * ArcadianDream.CONFIG.danmakuCooldownMultiplier()));
        tooltip.add(Text.translatable("item.arcadiandream.bullet.tooltip_gravity", currentGravity));
        tooltip.add(Text.translatable("item.arcadiandream.bullet.tooltip_density", currentDensity));
        tooltip.add(Text.translatable("item.arcadiandream.bullet.tooltip_pattern", currentPattern));
        tooltip.add(Text.translatable("item.arcadiandream.bullet.tooltip_stack", currentStack));
        tooltip.add(Text.translatable("item.arcadiandream.bullet.tooltip_amount", currentDensity * currentStack));
    }

    public ThrownItemEntity getBullet(World world, LivingEntity user) {
        return new BaseBulletEntity(world, user);
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
        return "none";
    }

    public int getStack() {
        return 0;
    }
}
