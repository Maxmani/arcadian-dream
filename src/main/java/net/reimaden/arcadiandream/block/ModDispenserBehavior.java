package net.reimaden.arcadiandream.block;

import net.reimaden.arcadiandream.entity.custom.OrbBulletEntity;
import net.reimaden.arcadiandream.item.ModItems;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

public class ModDispenserBehavior {

    public static void register() {
        DispenserBlock.registerBehavior(ModItems.ORB_BULLET, new DanmakuDispenserBehavior() {

            @Override
            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                return Util.make(new OrbBulletEntity(world, position.getX(), position.getY(), position.getZ()), entity -> entity.setItem(stack));
            }
        });
    }
}
