package net.reimaden.arcadiandream.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.particle.custom.*;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModParticles {

    public static final DefaultParticleType BULLET_DESPAWN = registerParticle("bullet_despawn");
    public static final DefaultParticleType BULLET_SPAWN = registerParticle("bullet_spawn");
    public static final DefaultParticleType BULLET_CANCEL = registerParticle("bullet_cancel");
    public static final DefaultParticleType EXTEND = registerParticle("extend");
    public static final DefaultParticleType RITUAL = registerParticle("ritual");

    private static DefaultParticleType registerParticle(String name) {
        return Registry.register(Registry.PARTICLE_TYPE, new Identifier(ArcadianDream.MOD_ID, name), FabricParticleTypes.simple());
    }

    public static void register() {
        ArcadianDream.LOGGER.debug("Registering particles for " + ArcadianDream.MOD_ID);
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        ParticleFactoryRegistry.getInstance().register(ModParticles.BULLET_DESPAWN, BulletDespawnParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.BULLET_SPAWN, BulletSpawnParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.BULLET_CANCEL, BulletCancelParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.EXTEND, ExtendParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.RITUAL, RitualParticle.Factory::new);
    }
}
