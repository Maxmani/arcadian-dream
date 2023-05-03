/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.mixin.client;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.reimaden.arcadiandream.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {

    @Redirect(method = "renderArm", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V", ordinal = 0))
    private void arcadiandream$makeArmTranslucent(ModelPart modelPart, MatrixStack matrices, VertexConsumer vertices, int light, int overlay, MatrixStack matrices2, VertexConsumerProvider vertexConsumers, int light2, AbstractClientPlayerEntity player) {
        if (player.hasStatusEffect(ModEffects.ETHEREAL)) {
            final float alpha = 0.75f;
            modelPart.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(player.getSkinTexture())), light, overlay, 1.0f, 1.0f, 1.0f, alpha);
            return;
        }

        modelPart.render(matrices, vertices, light, overlay);
    }

    @Redirect(method = "renderArm", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V", ordinal = 1))
    private void arcadiandream$makeSleeveTranslucent(ModelPart modelPart, MatrixStack matrices, VertexConsumer vertices, int light, int overlay, MatrixStack matrices2, VertexConsumerProvider vertexConsumers, int light2, AbstractClientPlayerEntity player) {
        if (player.hasStatusEffect(ModEffects.ETHEREAL)) {
            final float alpha = 0.75f;
            modelPart.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(player.getSkinTexture())), light, overlay, 1.0f, 1.0f, 1.0f, alpha);
            return;
        }

        modelPart.render(matrices, vertices, light, overlay);
    }
}
