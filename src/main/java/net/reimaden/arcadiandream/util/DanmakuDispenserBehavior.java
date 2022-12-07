package net.reimaden.arcadiandream.util;

import net.reimaden.arcadiandream.sound.ModSounds;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

public abstract class DanmakuDispenserBehavior extends ItemDispenserBehavior {

    @Override
    public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        ServerWorld world = pointer.getWorld();
        Position position = DispenserBlock.getOutputLocation(pointer);
        Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
        ProjectileEntity projectileEntity = this.createProjectile(world, position, stack);
        projectileEntity.setVelocity(direction.getOffsetX(), (float)direction.getOffsetY(), direction.getOffsetZ(), this.getForce(), this.getVariation());
        world.spawnEntity(projectileEntity);
        stack.decrement(1);
        return stack;
    }

    @Override
    protected void playSound(BlockPointer pointer) {
        pointer.getWorld().playSound(null, pointer.getPos(), ModSounds.ENTITY_DANMAKU_FIRE, SoundCategory.BLOCKS, 1f, 1f);
    }

    protected abstract ProjectileEntity createProjectile(World var1, Position var2, ItemStack var3);

    protected float getVariation() {
        return 0.0f;
    }

    protected float getForce() {
        return 1.0f;
    }
}
