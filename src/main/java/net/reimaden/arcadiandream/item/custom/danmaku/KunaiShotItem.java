/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item.custom.danmaku;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.entity.custom.danmaku.BaseBulletEntity;
import net.reimaden.arcadiandream.entity.custom.danmaku.KunaiBulletEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class KunaiShotItem extends BaseShotItem {

    public KunaiShotItem(Settings settings, float power, float speed, int duration, int cooldown, float gravity,
                         float divergence, String pattern, int density, float maxPower, float maxSpeed, int maxDuration,
                         int maxCooldown, float maxDivergence, int maxDensity) {
        super(settings, power, speed, duration, cooldown, gravity, divergence, pattern,
                density, maxPower, maxSpeed, maxDuration, maxCooldown, maxDivergence, maxDensity);
    }

    @Override
    public @NotNull BaseBulletEntity getBullet(World world, LivingEntity user) {
        return new KunaiBulletEntity(world, user);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        if (!stack.hasNbt()) {
            return;
        }

        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("item." + ArcadianDream.MOD_ID + ".shot.tooltip_piercing"));
        }
    }
}
