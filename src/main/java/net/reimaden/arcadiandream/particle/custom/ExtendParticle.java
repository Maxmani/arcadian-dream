package net.reimaden.arcadiandream.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class ExtendParticle extends AnimatedParticle {

    protected ExtendParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, spriteProvider, 1.25f);
        this.velocityMultiplier = 0.6f;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.scale *= 0.75f;
        this.maxAge = 60 + this.random.nextInt(12);
        this.setSpriteForAge(spriteProvider);
        if (this.random.nextInt(4) == 0) {
            this.setColor(0.81f + this.random.nextFloat() * 0.1f, 0.09f + this.random.nextFloat() * 0.1f, 0.94f);
        } else {
            this.setColor(0.84f + this.random.nextFloat() * 0.1f, 0.19f + this.random.nextFloat() * 0.1f, 0.94f);
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new ExtendParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
