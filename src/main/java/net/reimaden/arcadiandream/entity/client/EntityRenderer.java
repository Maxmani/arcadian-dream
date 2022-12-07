package net.reimaden.arcadiandream.entity.client;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.reimaden.arcadiandream.entity.ModEntities;
import net.reimaden.arcadiandream.entity.client.entities.BulletEntityRenderer;

public class EntityRenderer {

    public static void register() {
        EntityRendererRegistry.register(ModEntities.ORB_BULLET, BulletEntityRenderer::new);
    }
}
