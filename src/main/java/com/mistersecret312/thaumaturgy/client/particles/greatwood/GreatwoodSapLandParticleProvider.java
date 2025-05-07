package com.mistersecret312.thaumaturgy.client.particles.greatwood;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class GreatwoodSapLandParticleProvider implements ParticleProvider<SimpleParticleType>
{
    public SpriteSet set;

    public GreatwoodSapLandParticleProvider(SpriteSet set)
    {
        this.set = set;
    }

    @Override
    public @Nullable Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY,
                                             double pZ, double pXSpeed, double pYSpeed, double pZSpeed)
    {
        GreatwoodSapParticle particle = new GreatwoodSapParticle.DripLandParticleGreatwood(pLevel, pX, pY, pZ);
        particle.setLifetime((int)(128.0D / (Math.random() * 0.8D + 0.2D)));
        particle.setColor(0.90f,0.58f,0.27f);
        particle.pickSprite(set);
        return particle;
    }


}
