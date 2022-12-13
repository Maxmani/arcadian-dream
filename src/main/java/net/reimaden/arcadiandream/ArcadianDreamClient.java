package net.reimaden.arcadiandream;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.reimaden.arcadiandream.block.entity.ModBlockEntities;
import net.reimaden.arcadiandream.block.entity.client.OnbashiraBlockEntityRenderer;
import net.reimaden.arcadiandream.block.entity.client.RitualShrineBlockEntityRenderer;
import net.reimaden.arcadiandream.entity.client.EntityRenderer;
import net.reimaden.arcadiandream.entity.client.armor.OrdinaryArmorRenderer;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.networking.ModMessages;
import net.reimaden.arcadiandream.particle.ModParticles;
import net.reimaden.arcadiandream.util.ModModelPredicateProvider;
import net.reimaden.arcadiandream.util.client.ModColorProviders;
import net.reimaden.arcadiandream.util.client.ModModels;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class ArcadianDreamClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModModels.register();
        EntityRenderer.register();
        ModParticles.registerClient();
        ModColorProviders.register();
        ModMessages.registerS2CPackets();
        ModModelPredicateProvider.register();

        GeoArmorRenderer.registerArmorRenderer(new OrdinaryArmorRenderer(), ModItems.ORDINARY_HAT);

        BlockEntityRendererRegistry.register(ModBlockEntities.ONBASHIRA, OnbashiraBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.RITUAL_SHRINE, RitualShrineBlockEntityRenderer::new);
    }
}
