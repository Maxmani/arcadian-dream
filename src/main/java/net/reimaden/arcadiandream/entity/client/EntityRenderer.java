/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.client;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.reimaden.arcadiandream.entity.ModEntities;
import net.reimaden.arcadiandream.entity.client.renderers.BulletEntityRenderer;
import net.reimaden.arcadiandream.entity.client.renderers.FairyEntityRenderer;
import net.reimaden.arcadiandream.entity.client.renderers.BubbleBulletEntityRenderer;
import net.reimaden.arcadiandream.entity.client.renderers.FlatBulletEntityRenderer;

public class EntityRenderer {

    public static void register() {
        // Danmaku
        EntityRendererRegistry.register(ModEntities.CIRCLE_BULLET, BulletEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.BUBBLE_BULLET, BubbleBulletEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.AMULET_BULLET, FlatBulletEntityRenderer::new);

        // Mobs
        EntityRendererRegistry.register(ModEntities.FAIRY, FairyEntityRenderer::new);
    }
}
