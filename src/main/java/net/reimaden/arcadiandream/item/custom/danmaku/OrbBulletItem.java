package net.reimaden.arcadiandream.item.custom.danmaku;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.reimaden.arcadiandream.entity.custom.OrbBulletEntity;
import net.minecraft.world.World;

public class OrbBulletItem extends BaseBulletItem {

    public OrbBulletItem(Settings settings) {
        super(settings);
    }

    @Override
    public ThrownItemEntity getBullet(World world, LivingEntity user) {
        return new OrbBulletEntity(world, user);
    }

    @Override
    public int getPower() {
        return 3;
    }

    @Override
    public float getSpeed() {
        return 1.0f;
    }

    @Override
    public int getMaxAge() {
        return 200;
    }

    @Override
    public int getCooldown() {
        return 2;
    }

    @Override
    public float getGravity() {
        return 0.0f;
    }

    @Override
    public int getDensity() {
        return 1;
    }

    @Override
    public int getStack() {
        return 1;
    }
}
