package net.reimaden.arcadiandream.entity.client.entities;

import net.reimaden.arcadiandream.entity.custom.BaseBulletEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.util.math.BlockPos;

public class BulletEntityRenderer extends FlyingItemEntityRenderer<BaseBulletEntity> {

    public BulletEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    protected int getBlockLight(BaseBulletEntity entity, BlockPos pos) {
        return 15;
    }
}
