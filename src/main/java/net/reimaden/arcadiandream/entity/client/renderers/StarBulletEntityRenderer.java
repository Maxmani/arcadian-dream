/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.client.renderers;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.reimaden.arcadiandream.entity.custom.BaseBulletEntity;

public class StarBulletEntityRenderer extends BulletEntityRenderer {

    public StarBulletEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(BaseBulletEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.scale(2.0f, 2.0f, 2.0f);
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    // TODO: Rework the bullet entity renderer
}
