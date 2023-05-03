/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.effect.ModEffects;
import org.jetbrains.annotations.Nullable;

public class EtherealEvent implements AttackBlockCallback, AttackEntityCallback, ServerLivingEntityEvents.AllowDamage {

    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockPos pos, Direction direction) {
        if (isEthereal(player)) {
            return ActionResult.FAIL;
        }
        return ActionResult.PASS;
    }

    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult hitResult) {
        if (isEthereal(player)) {
            return ActionResult.FAIL;
        }
        return ActionResult.PASS;
    }

    @Override
    public boolean allowDamage(LivingEntity entity, DamageSource source, float amount) {
        return !isEthereal(entity) && (!(source.getAttacker() instanceof LivingEntity livingEntity) || !isEthereal(livingEntity));
    }

    private static boolean isEthereal(LivingEntity entity) {
        return entity.hasStatusEffect(ModEffects.ETHEREAL);
    }
}
