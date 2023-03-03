/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.client.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.reimaden.arcadiandream.entity.custom.BaseBulletEntity;

public class BubbleBulletEntityRenderer extends BulletEntityRenderer {

    public BubbleBulletEntityRenderer(EntityRendererFactory.Context ctx, float scale) {
        super(ctx, scale);
    }

    @Override
    protected void modifyMatrices(MatrixStack matrices, BaseBulletEntity entity, float tickDelta) {
        super.modifyMatrices(matrices, entity, tickDelta);
        matrices.translate(0.0f, -0.0315f, 0.0f);
    }
}
