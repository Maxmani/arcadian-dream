/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.client.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.reimaden.arcadiandream.entity.custom.danmaku.BaseBulletEntity;

public class FlatBulletEntityRenderer extends BulletEntityRenderer {

    public FlatBulletEntityRenderer(EntityRendererFactory.Context ctx, float scale) {
        super(ctx, scale);
    }

    @Override
    protected void modifyMatrices(MatrixStack matrices, BaseBulletEntity entity, float tickDelta) {
        matrices.push();
        matrices.scale(scale, scale, scale);

        // Rotate the model based on the direction
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 90.0f));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch())));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0f));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90.0f));
    }
}
