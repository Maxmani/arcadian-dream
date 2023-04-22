/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item.custom.tools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.sound.ModSounds;
import net.reimaden.arcadiandream.util.RaycastHelper;

import java.util.Objects;
import java.util.function.Predicate;

public class DeathScytheItem extends SwordItem {

    private static final float PITCH = 1.1f;
    private static final float VOLUME = 1.0f;

    public DeathScytheItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        Hand hand = user.getActiveHand();

        if (!world.isClient() && user instanceof PlayerEntity player) {
            if (remainingUseTicks < getMaxUseTime(stack) - 20) {
                Predicate<Entity> filter = e -> e != player && e instanceof LivingEntity;
                EntityHitResult result = RaycastHelper.raycast(player, 16, filter);

                if (result != null && result.getType() == HitResult.Type.ENTITY) {
                    Entity entity = result.getEntity();
                    Vec3d entityPos = entity.getPos();

                    if (entity instanceof PlayerEntity playerEntity && (playerEntity.isCreative()
                            || !Objects.requireNonNull(playerEntity.getServer()).isPvpEnabled())) {
                        return;
                    }
                    swapPositions(world, player, entity, entityPos);
                    player.incrementStat(Stats.USED.getOrCreateStat(this));

                    stack.damage(1, player, e -> e.sendToolBreakStatus(hand));

                    player.getItemCooldownManager().set(this, 100);
                }
            }
        }
    }

    private static void swapPositions(World world, PlayerEntity user, Entity target, Vec3d targetPos) {

        if (target instanceof PlayerEntity) {
            target.setPosition(user.getX(), user.getY(), user.getZ());
            target.refreshPositionAfterTeleport(target.getPos());
            playEffects(world, target, ModSounds.ITEM_DEATH_SCYTHE_TELEPORT, target.getSoundCategory());
        } else {
            target.refreshPositionAndAngles(user.getX(), user.getY(), user.getZ(), target.getYaw(), target.getPitch());
            playEffects(world, target, ModSounds.ITEM_DEATH_SCYTHE_TELEPORT_GENERIC, target.getSoundCategory());
        }

        user.setPosition(targetPos.getX(), targetPos.getY(), targetPos.getZ());
        user.refreshPositionAfterTeleport(user.getPos());
        playEffects(world, user, ModSounds.ITEM_DEATH_SCYTHE_TELEPORT, user.getSoundCategory());
    }

    private static void playEffects(World world, Entity entity, SoundEvent soundEvent, SoundCategory soundCategory) {
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), soundEvent, soundCategory, VOLUME, PITCH);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }
}
