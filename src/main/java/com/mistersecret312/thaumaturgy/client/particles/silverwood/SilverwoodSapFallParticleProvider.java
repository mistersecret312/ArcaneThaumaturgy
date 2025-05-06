package com.mistersecret312.thaumaturgy.client.particles.silverwood;

import com.mistersecret312.thaumaturgy.init.ParticleInit;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class SilverwoodSapFallParticleProvider implements ParticleProvider<SimpleParticleType>
{
    public SpriteSet set;

    public SilverwoodSapFallParticleProvider(SpriteSet set)
    {
        this.set = set;
    }

    @Override
    public @Nullable Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY,
                                             double pZ, double pXSpeed, double pYSpeed, double pZSpeed)
    {
        SilverwoodSapParticle particle = new SilverwoodSapParticle.HoneyFallAndLandParticleSilverwood(pLevel, pX, pY, pZ, ParticleInit.SILVERWOOD_SAP_LANDING.get());
        particle.setGravity(0.01F);
        particle.setColor(1F, 1F, 1F);
        particle.pickSprite(set);
        return particle;
    }
}
