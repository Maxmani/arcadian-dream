/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.client.entities;

import com.google.common.collect.Maps;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.entity.client.entities.layers.FairyWingsLayer;
import net.reimaden.arcadiandream.entity.client.models.FairyEntityModel;
import net.reimaden.arcadiandream.entity.custom.FairyEntity;
import net.reimaden.arcadiandream.entity.variant.FairyVariant;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class FairyEntityRenderer extends GeoEntityRenderer<FairyEntity> {

    public static final Map<FairyVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(FairyVariant.class), (map) -> {
                map.put(FairyVariant.BLUE, new Identifier(ArcadianDream.MOD_ID, "textures/entity/fairy/fairy_blue.png"));
                map.put(FairyVariant.RED, new Identifier(ArcadianDream.MOD_ID, "textures/entity/fairy/fairy_red.png"));
                map.put(FairyVariant.GREEN, new Identifier(ArcadianDream.MOD_ID, "textures/entity/fairy/fairy_green.png"));
                map.put(FairyVariant.YELLOW, new Identifier(ArcadianDream.MOD_ID, "textures/entity/fairy/fairy_yellow.png"));
            });

    public FairyEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new FairyEntityModel());
        shadowRadius = 0.45f;
        addRenderLayer(new FairyWingsLayer(this));
    }

    @Override
    public Identifier getTextureLocation(FairyEntity animatable) {
        return LOCATION_BY_VARIANT.get(animatable.getVariant());
    }
}
