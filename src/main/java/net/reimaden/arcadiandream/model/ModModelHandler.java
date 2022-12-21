/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.model;

import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.mixin.client.EntityModelLayersAccessor;

import java.util.function.BiConsumer;

public class ModModelHandler {

    public static final EntityModelLayer ORDINARY = model("ordinary_armor");

    public static void register(BiConsumer<EntityModelLayer, TexturedModelData> consumer) {
        consumer.accept(ORDINARY, TexturedModelData.of(OrdinaryArmorModel.getModelData(), 64, 64));
    }

    public static EntityModelLayer model(String name, String layer) {
        EntityModelLayer result = new EntityModelLayer(new Identifier(ArcadianDream.MOD_ID, name), layer);
        EntityModelLayersAccessor.getLAYERS().add(result);
        return result;
    }

    public static EntityModelLayer model(String name) {
        return model(name, "main");
    }
}
