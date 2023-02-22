/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.client.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.util.math.BlockPos;
import net.reimaden.arcadiandream.entity.custom.BaseBulletEntity;

public class BulletEntityRenderer extends FlyingItemEntityRenderer<BaseBulletEntity> {

    protected final ItemRenderer itemRenderer;
    protected final float scale;

    public BulletEntityRenderer(EntityRendererFactory.Context ctx, float scale) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
        this.scale = scale;
    }

    public BulletEntityRenderer(EntityRendererFactory.Context ctx) {
        this(ctx, 1.0f);
    }

    @Override
    protected int getBlockLight(BaseBulletEntity entity, BlockPos pos) {
        return 15;
    }
}
