package net.reimaden.arcadiandream.item.custom;

import net.minecraft.util.math.Vec3d;
import net.reimaden.arcadiandream.entity.custom.OrbBulletEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

@SuppressWarnings({"ConstantConditions", "ParameterCanBeLocal"})
public class OrbBulletItem extends BaseBulletItem {

    public OrbBulletItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        int d = itemStack.getNbt().getInt("Density");
        int k = itemStack.getNbt().getInt("Stack");
        int m = itemStack.getNbt().getInt("Modifier");
        float s = itemStack.getNbt().getFloat("Speed");
        float angle = 0;
        float x = 0;
        float y = 0;
        float speed = 0;
        float n = s / k;

        if (!world.isClient) {
            if (itemStack.getNbt().getString("Pattern").equals("None")) {
                for (int i = 0; i < itemStack.getNbt().getInt("Density"); i++) {
                    OrbBulletEntity orbBulletEntity = new OrbBulletEntity(world, user);
                    orbBulletEntity.setItem(itemStack);
                    orbBulletEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, itemStack.getNbt().getFloat("Speed"), 0.0f + (itemStack.getNbt().getInt("Density") - 1));
                    world.spawnEntity(orbBulletEntity);
                }
            } else if (itemStack.getNbt().getString("Pattern").equals("Ring")) {
                createRing(world, user, itemStack, d, k, angle, s, speed, m, x, y, n);
            }
        }

        return super.use(world, user, hand);
    }

    private void createRing(World world, PlayerEntity user, ItemStack itemStack,
                            int d, int k, float angle, float s, float speed, int m, float x, float y, float n) {
        for (int i = 0; i < k; i++) {
            for (int h = 0; h < d; h++) {
                OrbBulletEntity orbBulletEntity = new OrbBulletEntity(world, user);
                orbBulletEntity.setItem(itemStack);

                angle = (h * (360 / (float)d)) + 180;
                speed = s;
                if (m != 0) {
                    m = 1 + m;
                    x = MathHelper.cos(angle);
                    y = MathHelper.sin(angle) / 2; //replace 2 with m when modifier is correctly programmed in.
                    angle = (float)MathHelper.atan2(y, x) * MathHelper.DEGREES_PER_RADIAN;
                    speed = MathHelper.sqrt(MathHelper.square(x) + MathHelper.square(y)) * s;
                }
                Vec3d bullet = new Vec3d(0, 0, 1);
                bullet = bullet.rotateY((angle) * MathHelper.RADIANS_PER_DEGREE);
                bullet = bullet.rotateX(user.getPitch() * MathHelper.RADIANS_PER_DEGREE);
                bullet = bullet.rotateY((-user.getYaw() + 180) * MathHelper.RADIANS_PER_DEGREE);

                orbBulletEntity.setVelocity(bullet.getX(),
                        bullet.getY(), bullet.getZ(), speed, 0.0f);

                world.spawnEntity(orbBulletEntity);
            }

            s = s - n;
        }
    }

    @Override
    public float getSpeed() {
        return 1.0f;
    }

    @Override
    public int getPower() {
        return 3;
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
