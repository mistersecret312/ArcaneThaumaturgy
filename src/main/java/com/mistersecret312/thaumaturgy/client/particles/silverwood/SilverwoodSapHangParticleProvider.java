package com.mistersecret312.thaumaturgy.client.particles.silverwood;

import com.mistersecret312.thaumaturgy.init.ParticleInit;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class SilverwoodSapHangParticleProvider implements ParticleProvider<SimpleParticleType>
{
    public SpriteSet set;

    public SilverwoodSapHangParticleProvider(SpriteSet set)
    {
        this.set = set;
    }

    @Override
    public @Nullable Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY,
                                             double pZ, double pXSpeed, double pYSpeed, double pZSpeed)
    {
        SilverwoodSapParticle particle = new SilverwoodSapParticle.DripHangParticleSilverwood(pLevel, pX, pY, pZ, ParticleInit.SILVERWOOD_SAP_FALLING.get());
        particle.setLifetime(100);
        particle.setGravity(particle.getGravity()*0.01F);
        particle.setColor(0.9f, 0.9f, 0.9f);
        particle.pickSprite(set);
        return particle;
    }
}
