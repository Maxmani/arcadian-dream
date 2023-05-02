/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.model.trinket;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class MagatamaNecklaceModel extends EntityModel<Entity> {

    private final ModelPart magatamaNecklace;

    public MagatamaNecklaceModel(ModelPart root) {
        magatamaNecklace = root.getChild("magatamaNecklace");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();
        root.addChild("magatamaNecklace", ModelPartBuilder.create().uv(0, 7).cuboid(-11.5F, -8.0F, 0.0F, 7.0F, 9.0F, 0.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-3.5F, -9.0F, 0.0F, 1.0F, 0.0F, 7.0F, new Dilation(0.0F))
                .uv(2, 0).cuboid(-13.5F, -9.0F, 0.0F, 1.0F, 0.0F, 7.0F, new Dilation(0.0F))
                .uv(10, 0).cuboid(-12.5F, -9.0F, 7.0F, 9.0F, 0.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 2).cuboid(-4.5F, -9.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-12.5F, -9.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(8.0F, 9.0F, -2.5F));
        return TexturedModelData.of(data, 32, 32);
    }

    @Override
    public void setAngles(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {}

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        magatamaNecklace.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
