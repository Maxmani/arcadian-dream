/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.model.trinket;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.model.ModEntityModelLayers;

@SuppressWarnings("unchecked")
public class ModTrinketRenderers {

    public static void register() {
        TrinketRendererRegistry.registerRenderer(ModItems.MAGATAMA_NECKLACE, new TrinketRenderer() {
            private static final Identifier TEXTURE = new Identifier(ArcadianDream.MOD_ID, "textures/models/trinket/magatama_necklace.png");
            private static Model model;

            @Override
            public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel,
                               MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity,
                               float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {

                if (model == null) {
                    model = new MagatamaNecklaceModel(getPart(ModEntityModelLayers.MAGATAMA_NECKLACE));
                }
                TrinketRenderer.translateToChest(matrices, (PlayerEntityModel<AbstractClientPlayerEntity>) contextModel, (AbstractClientPlayerEntity) entity);
                matrices.scale(0.5f, 0.5f, 0.5f);
                matrices.translate(0.0f, -0.8f, 0.225f);
                model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(TEXTURE)), light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
            }
        });

        TrinketRendererRegistry.registerRenderer(ModItems.FAIRY_CHARM, (stack, slotReference, contextModel, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch) -> {
            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
            TrinketRenderer.translateToChest(matrices, ((PlayerEntityModel<AbstractClientPlayerEntity>) contextModel), (AbstractClientPlayerEntity) entity);
            matrices.scale(0.20f, 0.20f, 0.20f);
            matrices.translate(-0.5f, -1.0f, 0.12f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
            itemRenderer.renderItem(stack, ModelTransformationMode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), entity.getId());
        });
    }

    @SuppressWarnings("SameParameterValue")
    private static ModelPart getPart(EntityModelLayer layer) {
        return MinecraftClient.getInstance().getEntityModelLoader().getModelPart(layer);
    }
}
