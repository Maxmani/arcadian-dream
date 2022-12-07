package net.reimaden.arcadiandream.item.custom;

import net.reimaden.arcadiandream.entity.custom.OrbBulletEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

@SuppressWarnings("ConstantConditions")
public class OrbBulletItem extends BaseBulletItem {

    public OrbBulletItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (!world.isClient) {
            if (itemStack.getNbt().getString("Pattern").equals("None")) {
                for (int i = 0; i < itemStack.getNbt().getInt("Density"); i++) {
                    OrbBulletEntity orbBulletEntity = new OrbBulletEntity(world, user);
                    orbBulletEntity.setItem(itemStack);
                    orbBulletEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, itemStack.getNbt().getFloat("Speed"), 0.0f + (itemStack.getNbt().getInt("Density") - 1));
                    world.spawnEntity(orbBulletEntity);
                }
            } else if (itemStack.getNbt().getString("Pattern").equals("Ring")) {
                float d = itemStack.getNbt().getInt("Density");
                float L = user.getPitch();
                for (int i = 0; i < d; i++) {
                    OrbBulletEntity orbBulletEntity = new OrbBulletEntity(world, user);
                    orbBulletEntity.setItem(itemStack);
                    orbBulletEntity.setVelocity(user, L * MathHelper.cos(i * (360 / d)),
                            user.getYaw() + (i * (360 / d)), 0.0f, itemStack.getNbt().getFloat("Speed"), 0.0f);
                    world.spawnEntity(orbBulletEntity);
                }
            } else if (itemStack.getNbt().getString("Pattern").equals("Ray")) {
                int x = itemStack.getNbt().getInt("Density");
                float j = itemStack.getNbt().getFloat("Speed");
                float k = itemStack.getNbt().getFloat("Speed") / x;
                for (int i = 0; i < itemStack.getNbt().getInt("Density"); i++) {
                    OrbBulletEntity orbBulletEntity = new OrbBulletEntity(world, user);
                    orbBulletEntity.setItem(itemStack);
                    orbBulletEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, j, 0.0f);
                    world.spawnEntity(orbBulletEntity);
                    j = j - k;
                }
            }
            else if (itemStack.getNbt().getString("Pattern").equals("Stack Ring")) {
                int d = itemStack.getNbt().getInt("Density");
                int e = itemStack.getNbt().getInt("Stack");
                float j = itemStack.getNbt().getFloat("Speed");
                float k = itemStack.getNbt().getFloat("Speed") / e;
                for (int f = 0; f < e; f++) {
                    for (int i = 0; i < d; i++) {
                        OrbBulletEntity orbBulletEntity = new OrbBulletEntity(world, user);
                        orbBulletEntity.setItem(itemStack);
                        orbBulletEntity.setVelocity(user, 0.0f,
                                user.getYaw() + (i * (360 / (float) d)), 0.0f, j, 0.0f);
                        world.spawnEntity(orbBulletEntity);
                    }
                    j = j - k;
                }
            }
        }

        return super.use(world, user, hand);
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
