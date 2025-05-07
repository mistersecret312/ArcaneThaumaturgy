package com.mistersecret312.thaumaturgy.client.particles.silverwood;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class SilverwoodSapLandParticleProvider implements ParticleProvider<SimpleParticleType>
{
    public SpriteSet set;

    public SilverwoodSapLandParticleProvider(SpriteSet set)
    {
        this.set = set;
    }

    @Override
    public @Nullable Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY,
                                             double pZ, double pXSpeed, double pYSpeed, double pZSpeed)
    {
        SilverwoodSapParticle particle = new SilverwoodSapParticle.DripLandParticleSilverwood(pLevel, pX, pY, pZ);
        particle.setLifetime((int)(128.0D / (Math.random() * 0.8D + 0.2D)));
        particle.setColor(0.9f, 0.9f, 0.9f);
        particle.pickSprite(set);
        return particle;
    }


}
