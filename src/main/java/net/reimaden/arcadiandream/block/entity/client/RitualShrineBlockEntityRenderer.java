/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.block.entity.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.block.entity.RitualShrineBlockEntity;

import java.util.Objects;

public class RitualShrineBlockEntityRenderer implements BlockEntityRenderer<RitualShrineBlockEntity> {

    public RitualShrineBlockEntityRenderer(@SuppressWarnings("unused") BlockEntityRendererFactory.Context context) {
        super();
    }

    @Override
    public void render(RitualShrineBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        MinecraftClient minecraft = MinecraftClient.getInstance();
        ItemRenderer itemRenderer = minecraft.getItemRenderer();
        ItemStack itemStack = entity.getRenderStack();

        int age = 0;
        if (minecraft.world != null) {
            age = (int) (minecraft.world.getTime() % 314);
        }
        float altitude = MathHelper.sin(-(age + tickDelta) / 10.0f) * 0.1f + 1.165f;
        float rotation = (age + tickDelta) / 25.0F + 6.0F;

        matrices.push();
        matrices.translate(0.5, altitude, 0.5);
        matrices.scale(0.5f, 0.5f, 0.5f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotation(-rotation));

        itemRenderer.renderItem(itemStack, ModelTransformation.Mode.FIXED, getLightLevel(Objects.requireNonNull(entity.getWorld()), entity.getPos()),
                OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 1);
        matrices.pop();
    }

    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}
