package com.mistersecret312.thaumaturgy.init;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.aspects.Aspect;
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

    public static final RegistryObject<Aspect> ORDO = ASPECTS.register("ordo", () -> new Aspect(List.of(0xD5, 0xD4, 0xEC), ResourceLocation.parse("thaumaturgy:textures/aspect/ordo.png")));
    public static final RegistryObject<Aspect> AER = ASPECTS.register("aer", () -> new Aspect(List.of(0xFF, 0xFF, 0x7E), ResourceLocation.parse("thaumaturgy:textures/aspect/aer.png")));
    public static final RegistryObject<Aspect> IGNIS = ASPECTS.register("ignis", () -> new Aspect(List.of(0xFF, 0x5A, 0x1), ResourceLocation.parse("thaumaturgy:textures/aspect/ignis.png")));
    public static final RegistryObject<Aspect> AQUA = ASPECTS.register("aqua", () -> new Aspect(List.of(0x3C, 0XD4, 0xFC), ResourceLocation.parse("thaumaturgy:textures/aspect/aqua.png")));
    public static final RegistryObject<Aspect> TERRA = ASPECTS.register("terra", () -> new Aspect(List.of(0x56, 0xC0, 0x0), ResourceLocation.parse("thaumaturgy:textures/aspect/terra.png")));
    public static final RegistryObject<Aspect> PERDITIO = ASPECTS.register("perditio", () -> new Aspect(List.of(0x40, 0x40, 0x40), ResourceLocation.parse("thaumaturgy:textures/aspect/perditio.png")));

    public static final RegistryObject<Aspect> VICTUS = ASPECTS.register("victus", () -> new Aspect(List.of(0xDE, 0x0, 0x5), ResourceLocation.parse("thaumaturgy:textures/aspect/victus.png")).withDerivation(AspectInit.TERRA.get(), AspectInit.AQUA.get()));
    public static final RegistryObject<Aspect> MOTUS = ASPECTS.register("motus", () -> new Aspect(List.of(0xCD, 0xCC, 0xF4), ResourceLocation.parse("thaumaturgy:textures/aspect/motus.png")).withDerivation(AspectInit.ORDO.get(), AspectInit.AER.get()));
    public static final RegistryObject<Aspect> HERBA = ASPECTS.register("herba", () -> new Aspect(List.of(0x1, 0xAC, 0x0), ResourceLocation.parse("thaumaturgy:textures/aspect/herba.png")).withDerivation(AspectInit.VICTUS.get(), AspectInit.TERRA.get()));
    public static final RegistryObject<Aspect> BESTIA = ASPECTS.register("bestia", () -> new Aspect(List.of(0x9F, 0x64, 0x9), ResourceLocation.parse("thaumaturgy:textures/aspect/bestia.png")).withDerivation(AspectInit.VICTUS.get(), AspectInit.MOTUS.get()));
    public static final RegistryObject<Aspect> POTENTIA = ASPECTS.register("potentia", () -> new Aspect(List.of(0xC0, 0xFF, 0xFF), ResourceLocation.parse("thaumaturgy:textures/aspect/potentia.png")).withDerivation(AspectInit.IGNIS.get(), AspectInit.ORDO.get()));


    public static final Codec<Aspect> CODEC = ExtraCodecs.lazyInitializedCodec(() -> ASPECT.get().getCodec());

    public static void register(IEventBus eventBus)
    {
        ASPECTS.register(eventBus);
    }

    public static Aspect getAspect(ResourceLocation aspectType)
    {
        return RegistryObject.create(aspectType, ASPECT.get()).get();
    }
}
