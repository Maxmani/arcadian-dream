/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.model;

import net.minecraft.client.model.*;

public class OrdinaryArmorModel {

    public final ModelPart head;

    public OrdinaryArmorModel(ModelPart root) {
        this.head = root.getChild("head");
    }

    public static ModelData getModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        root.addChild("hat", ModelPartBuilder.create(), ModelTransform.NONE);
        root.addChild("body", ModelPartBuilder.create(), ModelTransform.NONE);
        root.addChild("left_arm", ModelPartBuilder.create(), ModelTransform.NONE);
        root.addChild("right_arm", ModelPartBuilder.create(), ModelTransform.NONE);
        root.addChild("left_leg", ModelPartBuilder.create(), ModelTransform.NONE);
        root.addChild("right_leg", ModelPartBuilder.create(), ModelTransform.NONE);

        ModelPartData head = root.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 1.0F, 0.0F));

        // abandon hope all ye who enter here

        head.addChild("hat", ModelPartBuilder.create().uv(26, 36).cuboid(-2.0F, -36.25F, -4.25F, 4.0F, 2.0F, 4.0F, Dilation.NONE)
                .uv(10, 35).cuboid(-1.5F, -37.25F, -4.0F, 3.0F, 1.0F, 5.0F, Dilation.NONE)
                .uv(34, 25).cuboid(-3.0F, -34.75F, -5.25F, 6.0F, 2.0F, 6.0F, Dilation.NONE)
                .uv(42, 17).cuboid(-3.0F, -30.75F, 1.75F, 6.0F, 1.0F, 1.0F, Dilation.NONE)
                .uv(42, 19).cuboid(-3.0F, -30.75F, -7.25F, 6.0F, 1.0F, 1.0F, Dilation.NONE)
                .uv(20, 16).cuboid(-5.0F, -30.75F, -5.25F, 1.0F, 1.0F, 6.0F, Dilation.NONE)
                .uv(16, 25).cuboid(4.0F, -30.75F, -5.25F, 1.0F, 1.0F, 6.0F, Dilation.NONE)
                .uv(0, 24).cuboid(-4.0F, -32.75F, -6.25F, 8.0F, 3.0F, 8.0F, Dilation.NONE)
                .uv(42, 13).cuboid(-4.0F, -29.75F, -8.25F, 8.0F, 1.0F, 1.0F, Dilation.NONE)
                .uv(42, 15).cuboid(-4.0F, -29.75F, 2.75F, 8.0F, 1.0F, 1.0F, Dilation.NONE)
                .uv(0, 35).cuboid(-6.0F, -29.75F, -6.25F, 1.0F, 1.0F, 8.0F, Dilation.NONE)
                .uv(36, 0).cuboid(5.0F, -29.75F, -6.25F, 1.0F, 1.0F, 8.0F, Dilation.NONE)
                .uv(0, 13).cuboid(-5.0F, -29.75F, -7.25F, 10.0F, 1.0F, 10.0F, Dilation.NONE)
                .uv(22, 25).cuboid(-7.0F, -28.75F, -7.25F, 1.0F, 1.0F, 10.0F, Dilation.NONE)
                .uv(34, 33).cuboid(-5.0F, -28.75F, 3.75F, 10.0F, 1.0F, 1.0F, Dilation.NONE)
                .uv(36, 9).cuboid(-5.0F, -28.75F, -9.25F, 10.0F, 1.0F, 1.0F, Dilation.NONE)
                .uv(0, 0).cuboid(-6.0F, -28.75F, -8.25F, 12.0F, 1.0F, 12.0F, Dilation.NONE)
                .uv(30, 14).cuboid(6.0F, -28.75F, -7.25F, 1.0F, 1.0F, 10.0F, Dilation.NONE),
                ModelTransform.of(0.0F, 23.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

        head.addChild("bow", ModelPartBuilder.create().uv(8, 0).cuboid(3.0F, -9.0F, -5.5F, 1.0F, 2.0F, 1.0F, Dilation.NONE)
                .uv(8, 3).cuboid(-4.0F, -9.0F, -5.5F, 1.0F, 2.0F, 1.0F, Dilation.NONE)
                .uv(8, 6).cuboid(1.75F, -9.25F, -5.5F, 1.0F, 2.0F, 1.0F, Dilation.NONE)
                .uv(0, 9).cuboid(2.75F, -9.75F, -5.501F, 1.0F, 2.0F, 1.0F, Dilation.NONE)
                .uv(4, 9).cuboid(-2.75F, -9.25F, -5.5F, 1.0F, 2.0F, 1.0F, Dilation.NONE)
                .uv(8, 9).cuboid(-3.75F, -9.75F, -5.501F, 1.0F, 2.0F, 1.0F, Dilation.NONE)
                .uv(0, 3).cuboid(0.75F, -8.75F, -5.5015F, 3.0F, 2.0F, 1.0F, Dilation.NONE)
                .uv(0, 6).cuboid(-3.75F, -8.75F, -5.5015F, 3.0F, 2.0F, 1.0F, Dilation.NONE)
                .uv(0, 0).cuboid(-1.0F, -8.25F, -5.75F, 2.0F, 1.0F, 2.0F, Dilation.NONE),
                ModelTransform.of(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

        root.createPart(64, 64);

        return data;
    }
}
