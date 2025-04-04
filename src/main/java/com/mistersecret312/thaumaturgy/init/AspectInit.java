package com.mistersecret312.thaumaturgy.init;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.aspects.Aspect;
import com.mistersecret312.thaumaturgy.aspects.DerivedAspect;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class AspectInit
{
    public static final DeferredRegister<Aspect> ASPECTS = DeferredRegister.create(Aspect.ASPECT_LOCATION, ArcaneThaumaturgyMod.MODID);
    public static final Supplier<IForgeRegistry<Aspect>> ASPECT = ASPECTS.makeRegistry(RegistryBuilder::new);

    public static final RegistryObject<Aspect> ORDO = ASPECTS.register("ordo", () -> new Aspect(List.of(255, 255, 255), ResourceLocation.parse("thaumaturgy:textures/aspect/ordo.png")));
    public static final RegistryObject<Aspect> AER = ASPECTS.register("aer", () -> new Aspect(List.of(255, 255, 85), ResourceLocation.parse("thaumaturgy:textures/aspect/aer.png")));
    public static final RegistryObject<Aspect> IGNIS = ASPECTS.register("ignis", () -> new Aspect(List.of(255, 85, 85), ResourceLocation.parse("thaumaturgy:textures/aspect/ignis.png")));
    public static final RegistryObject<Aspect> AQUA = ASPECTS.register("aqua", () -> new Aspect(List.of(85, 255, 255), ResourceLocation.parse("thaumaturgy:textures/aspect/aqua.png")));
    public static final RegistryObject<Aspect> TERRA = ASPECTS.register("terra", () -> new Aspect(List.of(85, 255, 85), ResourceLocation.parse("thaumaturgy:textures/aspect/terra.png")));
    public static final RegistryObject<Aspect> PERDITIO = ASPECTS.register("perditio", () -> new Aspect(List.of(85, 85, 85), ResourceLocation.parse("thaumaturgy:textures/aspect/perditio.png")));

    public static final RegistryObject<Aspect> VICTUS = ASPECTS.register("victus", () -> new Aspect(List.of(255, 85, 85), ResourceLocation.parse("thaumaturgy:textures/aspect/victus.png")).withDerivation(AspectInit.TERRA.get(), AspectInit.AQUA.get()));
    public static final RegistryObject<Aspect> MOTUS = ASPECTS.register("motus", () -> new Aspect(List.of(255, 255, 255), ResourceLocation.parse("thaumaturgy:textures/aspect/motus.png")).withDerivation(AspectInit.ORDO.get(), AspectInit.AER.get()));
    public static final RegistryObject<Aspect> HERBA = ASPECTS.register("herba", () -> new Aspect(List.of(255, 255, 255), ResourceLocation.parse("thaumaturgy:textures/aspect/herba.png")).withDerivation(AspectInit.VICTUS.get(), AspectInit.TERRA.get()));
    public static final RegistryObject<Aspect> BESTIA = ASPECTS.register("bestia", () -> new Aspect(List.of(255, 255, 255), ResourceLocation.parse("thaumaturgy:textures/aspect/bestia.png")).withDerivation(AspectInit.VICTUS.get(), AspectInit.MOTUS.get()));
    public static final RegistryObject<Aspect> POTENTIA = ASPECTS.register("potentia", () -> new Aspect(List.of(255, 255, 255), ResourceLocation.parse("thaumaturgy:textures/aspect/potentia.png")).withDerivation(AspectInit.IGNIS.get(), AspectInit.ORDO.get()));


    public static final Codec<Aspect> CODEC = ExtraCodecs.lazyInitializedCodec(() -> ASPECT.get().getCodec());

    public static void register(IEventBus eventBus)
    {
        ASPECTS.register(eventBus);
    }

    public static Aspect getAspect(ResourceLocation galaxyType)
    {
        return RegistryObject.create(galaxyType, ASPECT.get()).get();
    }
}
