package com.mistersecret312.thaumaturgy.init;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

public class ParticleInit
{
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ArcaneThaumaturgyMod.MODID);

    public static final RegistryObject<SimpleParticleType> SILVERWOOD_SAP_HANGING = PARTICLES.register("silverwood_sap_hanging", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SILVERWOOD_SAP_FALLING = PARTICLES.register("silverwood_sap_falling", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SILVERWOOD_SAP_LANDING = PARTICLES.register("silverwood_sap_landing", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> GREATWOOD_SAP_HANGING = PARTICLES.register("greatwood_sap_hanging", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> GREATWOOD_SAP_FALLING = PARTICLES.register("greatwood_sap_falling", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> GREATWOOD_SAP_LANDING = PARTICLES.register("greatwood_sap_landing", () -> new SimpleParticleType(false));


    public static void register(IEventBus bus)
    {
        PARTICLES.register(bus);
    }
}
