package net.reimaden.arcadiandream.entity.custom;

import net.reimaden.arcadiandream.entity.ModEntities;
import net.reimaden.arcadiandream.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

@SuppressWarnings("ConstantConditions")
public class OrbBulletEntity extends BaseBulletEntity {

    public OrbBulletEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public OrbBulletEntity(World world, LivingEntity owner) {
        super(ModEntities.ORB_BULLET, owner, world);
    }

    public OrbBulletEntity(World world, double x, double y, double z) {
        super(ModEntities.ORB_BULLET, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.ORB_BULLET;
    }

    @Override
    public int getPower() {
        if (getStack().hasNbt()) {
            return this.getStack().getNbt().getInt("Power");
        } else {
            return 3;
        }
    }

    @Override
    public int getMaxAge() {
        if (getStack().hasNbt()) {
            return this.getStack().getNbt().getInt("Duration");
        } else {
            return 200;
        }
    }
}
