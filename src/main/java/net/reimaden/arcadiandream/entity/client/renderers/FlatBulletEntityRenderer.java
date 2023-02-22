/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.client.renderers;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.reimaden.arcadiandream.entity.custom.BaseBulletEntity;

public class FlatBulletEntityRenderer extends BulletEntityRenderer {

    public FlatBulletEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(BaseBulletEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (entity.age < 2 && dispatcher.camera.getFocusedEntity().squaredDistanceTo(entity) < 12.25) {
            return;
        }
        matrices.push();
        matrices.scale(scale, scale, scale);

        // Rotate the model based on the direction
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 90.0f));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch())));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0f));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90.0f));

        itemRenderer.renderItem(((FlyingItemEntity)entity).getStack(), ModelTransformation.Mode.GROUND, light,
                OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getId());
        matrices.pop();

        // Super from EntityRenderer
        if (!hasLabel(entity)) {
            return;
        }
        renderLabelIfPresent(entity, entity.getDisplayName(), matrices, vertexConsumers, light);
    }
}
