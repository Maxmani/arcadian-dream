/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.client.renderers;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import net.reimaden.arcadiandream.entity.custom.BaseBulletEntity;
import net.reimaden.arcadiandream.entity.custom.StarBulletEntity;
import net.reimaden.arcadiandream.item.custom.danmaku.BaseShotItem;

public class StarBulletEntityRenderer extends BulletEntityRenderer {

    private float rotationSpeed;

    public StarBulletEntityRenderer(EntityRendererFactory.Context ctx, float scale) {
        super(ctx, scale);
    }

    @Override
    public void render(BaseBulletEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        int age = entity.age % 360;
        ItemStack stack = entity.getStack();
        BaseShotItem item = (BaseShotItem) stack.getItem();
        float speed = item.getSpeed(stack) * 5;
        float rotation = (age + tickDelta) / 25.0F + 6.0F;
        rotationSpeed = rotation * speed;

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    protected void modifyMatrices(MatrixStack matrices, BaseBulletEntity entity, float tickDelta) {
        super.modifyMatrices(matrices, entity, tickDelta);
        matrices.multiply(RotationAxis.POSITIVE_Z.rotation(((StarBulletEntity) entity).getRotationAngle() ? rotationSpeed : -rotationSpeed));
    }
}
