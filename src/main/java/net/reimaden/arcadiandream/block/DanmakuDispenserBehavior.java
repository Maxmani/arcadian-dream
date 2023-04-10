/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.block;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.reimaden.arcadiandream.item.custom.danmaku.BaseShotItem;
import net.reimaden.arcadiandream.sound.ModSounds;

/**
 * Does not currently support bullet patterns.
 */
public abstract class DanmakuDispenserBehavior extends FallibleItemDispenserBehavior {

    @Override
    public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        ServerWorld world = pointer.getWorld();

        if (!world.isClient()) {
            NbtCompound nbt = stack.getNbt();
            Position position = DispenserBlock.getOutputLocation(pointer);
            Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
            setSuccess(BaseShotItem.isUsable(stack));

            if (isSuccess()) {
                ProjectileEntity projectileEntity = createProjectile(world, position, stack);
                projectileEntity.setVelocity(direction.getOffsetX(), (float) direction.getOffsetY(), direction.getOffsetZ(),
                        nbt != null ? nbt.getFloat("speed") : getForce(),
                        nbt != null ? nbt.getFloat("divergence") : getVariation());
                world.spawnEntity(projectileEntity);
                stack.damage(1, world.getRandom(), null);
            }
        }

        return stack;
    }

    @Override
    protected void playSound(BlockPointer pointer) {
        World world = pointer.getWorld();
        BlockPos pos = pointer.getPos();

        if (isSuccess()) {
            world.playSound(null, pos, ModSounds.ENTITY_DANMAKU_FIRE, SoundCategory.BLOCKS, 1f, 1f);
        } else {
            world.syncWorldEvent(WorldEvents.DISPENSER_FAILS, pos, 0);
        }
    }

    protected abstract ProjectileEntity createProjectile(World var1, Position var2, ItemStack var3);

    protected float getVariation() {
        return 0.0f;
    }

    protected float getForce() {
        return 1.0f;
    }
}
