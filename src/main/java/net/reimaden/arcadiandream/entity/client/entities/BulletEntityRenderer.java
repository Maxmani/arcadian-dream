/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.client.entities;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.util.math.BlockPos;
import net.reimaden.arcadiandream.entity.custom.BaseBulletEntity;

public class BulletEntityRenderer extends FlyingItemEntityRenderer<BaseBulletEntity> {

    public BulletEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    protected int getBlockLight(BaseBulletEntity entity, BlockPos pos) {
        return 15;
    }
}
