/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.client.renderers;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.reimaden.arcadiandream.entity.custom.danmaku.BaseBulletEntity;

public class BulletEntityRenderer extends FlyingItemEntityRenderer<BaseBulletEntity> {

    protected static final float MIN_DISTANCE = 12.25f;

    protected final ItemRenderer itemRenderer;
    protected final float scale;

    public BulletEntityRenderer(EntityRendererFactory.Context ctx, float scale) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
        this.scale = scale / 2;
    }

    @Override
    protected int getBlockLight(BaseBulletEntity entity, BlockPos pos) {
        return 15;
    }

    @Override
    public void render(BaseBulletEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (entity.age < 2 && dispatcher.camera.getFocusedEntity().squaredDistanceTo(entity) < MIN_DISTANCE) {
            return;
        }
        modifyMatrices(matrices, entity, tickDelta);
        itemRenderer.renderItem(entity.getStack(), ModelTransformation.Mode.FIXED, light,
                OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getId());
        matrices.pop();
    }

    protected void modifyMatrices(MatrixStack matrices, BaseBulletEntity entity, float tickDelta) {
        matrices.push();
        matrices.scale(scale, scale, scale);
        matrices.translate(0.0f, 0.25f, 0.0f);
        matrices.multiply(dispatcher.getRotation());
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f));
    }
}
