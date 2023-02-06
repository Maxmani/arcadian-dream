/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item.custom.tools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.entity.custom.BaseBulletEntity;
import net.reimaden.arcadiandream.sound.ModSounds;
import net.reimaden.arcadiandream.util.RaycastHelper;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

public class MiracleMalletItem extends ModHammerItem {

    private static final Set<ScaleType> scaleTypes = new HashSet<>();
    private static final Set<ScaleType> bulletScaleTypes = new HashSet<>();
    private static final int defaultScale = 1;
    private static final float scale = 0.25f;
    private static final int hitboxScale = 2;
    private static final int maxScale = 4;

    public MiracleMalletItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    static {
        scaleTypes.add(ScaleTypes.WIDTH);
        scaleTypes.add(ScaleTypes.HEIGHT);
        scaleTypes.add(ScaleTypes.VISIBILITY);
        scaleTypes.add(ScaleTypes.DROPS);

        bulletScaleTypes.add(ScaleTypes.WIDTH);
        bulletScaleTypes.add(ScaleTypes.HEIGHT);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient()) {
            Predicate<Entity> filter = e -> e instanceof BaseBulletEntity;
            EntityHitResult result = RaycastHelper.raycast(user, 16, filter);

            if (result != null && result.getType() == HitResult.Type.ENTITY) {
                // Scale bullet
                Entity entity = result.getEntity();

                ScaleData scale = ScaleTypes.WIDTH.getScaleData(entity);
                float pitch = scale.getScale();

                bulletScaleTypes.forEach(scaleType -> {
                    ScaleData data = scaleType.getScaleData(entity);
                    if (data.getScale() < maxScale) {
                        data.setScale(data.getScale() + 0.75f);
                    }
                });

                if (pitch < maxScale) {
                    playSound(world, user, 1f + pitch * 0.1f - 0.15f);
                    incrementStatAndDamageStack(user, hand, stack);
                    return TypedActionResult.success(stack);
                }

                return TypedActionResult.pass(stack);
            } else if (user.isSneaking()) {
                // Scale self
                ScaleData hitbox = ScaleTypes.HITBOX_WIDTH.getScaleData(user);
                AtomicBoolean isScaled = new AtomicBoolean();

                scaleTypes.forEach(scaleType -> {
                    ScaleData data = scaleType.getScaleData(user);
                    if (data.getScale() != scale) {
                        data.setScale(scale);
                        hitbox.setScale(hitboxScale);
                        isScaled.set(true);
                    } else if (data.getScale() != defaultScale) {
                        data.setScale(defaultScale);
                        hitbox.setScale(defaultScale);
                        isScaled.set(false);
                    }
                });

                playSound(world, user, isScaled.get() ? 0.8f : 1f);
                incrementStatAndDamageStack(user, hand, stack);
                user.getItemCooldownManager().set(this, 60);
                return TypedActionResult.success(stack);
            }
        }

        return TypedActionResult.pass(stack);
    }

    private static void playSound(World world, PlayerEntity user, float isScaled) {
        world.playSound(null, user.getBlockPos(), ModSounds.ITEM_MIRACLE_MALLET_USE,
                user.getSoundCategory(), 0.5f, isScaled);
    }

    private static void incrementStatAndDamageStack(PlayerEntity user, Hand hand, ItemStack stack) {
        user.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
        stack.damage(1, user, e -> e.sendToolBreakStatus(hand));
    }
}
