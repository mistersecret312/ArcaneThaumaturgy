package com.mistersecret312.thaumaturgy.client.particles.greatwood;

import com.mistersecret312.thaumaturgy.init.ParticleInit;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class GreatwoodSapHangParticleProvider implements ParticleProvider<SimpleParticleType>
{
    public SpriteSet set;

    public GreatwoodSapHangParticleProvider(SpriteSet set)
    {
        this.set = set;
    }

    @Override
    public @Nullable Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY,
                                             double pZ, double pXSpeed, double pYSpeed, double pZSpeed)
    {
        GreatwoodSapParticle particle = new GreatwoodSapParticle.DripHangParticleGreatwood(pLevel, pX, pY, pZ, ParticleInit.GREATWOOD_SAP_FALLING.get());
        particle.setLifetime(100);
        particle.setGravity(particle.getGravity()*0.01F);
        particle.setColor(0.90f,0.58f,0.27f);
        particle.pickSprite(set);
        return particle;
    }
}
