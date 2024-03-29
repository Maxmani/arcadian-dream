/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.particle.custom.*;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;

public class ModParticles {

    public static final DefaultParticleType BULLET_DESPAWN = registerParticle("bullet_despawn");
    public static final DefaultParticleType BULLET_CANCEL = registerParticle("bullet_cancel");
    public static final DefaultParticleType EXTEND = registerParticle("extend");
    public static final DefaultParticleType RITUAL = registerParticle("ritual");

    private static DefaultParticleType registerParticle(String name) {
        return Registry.register(Registries.PARTICLE_TYPE, new Identifier(ArcadianDream.MOD_ID, name), FabricParticleTypes.simple());
    }

    public static void register() {
        ArcadianDream.LOGGER.debug("Registering particles for " + ArcadianDream.MOD_ID);
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        ParticleFactoryRegistry.getInstance().register(ModParticles.BULLET_DESPAWN, BulletDespawnParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.BULLET_CANCEL, BulletCancelParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.EXTEND, ExtendParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.RITUAL, RitualParticle.Factory::new);
    }
}
