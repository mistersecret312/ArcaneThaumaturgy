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

    //Prime Aspects
    public static final RegistryObject<Aspect> ORDO = ASPECTS.register("ordo", () -> new Aspect(List.of(0xD5, 0xD4, 0xEC), ResourceLocation.parse("thaumaturgy:textures/aspect/ordo.png")));
    public static final RegistryObject<Aspect> AER = ASPECTS.register("aer", () -> new Aspect(List.of(0xFF, 0xFF, 0x7E), ResourceLocation.parse("thaumaturgy:textures/aspect/aer.png")));
    public static final RegistryObject<Aspect> IGNIS = ASPECTS.register("ignis", () -> new Aspect(List.of(0xFF, 0x5A, 0x1), ResourceLocation.parse("thaumaturgy:textures/aspect/ignis.png")));
    public static final RegistryObject<Aspect> AQUA = ASPECTS.register("aqua", () -> new Aspect(List.of(0x3C, 0XD4, 0xFC), ResourceLocation.parse("thaumaturgy:textures/aspect/aqua.png")));
    public static final RegistryObject<Aspect> TERRA = ASPECTS.register("terra", () -> new Aspect(List.of(0x56, 0xC0, 0x0), ResourceLocation.parse("thaumaturgy:textures/aspect/terra.png")));
    public static final RegistryObject<Aspect> PERDITIO = ASPECTS.register("perditio", () -> new Aspect(List.of(0x40, 0x40, 0x40), ResourceLocation.parse("thaumaturgy:textures/aspect/perditio.png")));

    //Tier 1 Aspects
    public static final RegistryObject<Aspect> VICTUS = ASPECTS.register("victus", () -> new Aspect(List.of(0xCC, 0x14, 0x33), ResourceLocation.parse("thaumaturgy:textures/aspect/victus.png")).withCompound(AspectInit.TERRA.get(), AspectInit.AQUA.get()));
    public static final RegistryObject<Aspect> MOTUS = ASPECTS.register("motus", () -> new Aspect(List.of(0xCD, 0xCC, 0xF4), ResourceLocation.parse("thaumaturgy:textures/aspect/motus.png")).withCompound(AspectInit.ORDO.get(), AspectInit.AER.get()));
    public static final RegistryObject<Aspect> POTENTIA = ASPECTS.register("potentia", () -> new Aspect(List.of(0xC0, 0xFF, 0xFF), ResourceLocation.parse("thaumaturgy:textures/aspect/potentia.png")).withCompound(AspectInit.IGNIS.get(), AspectInit.ORDO.get()));
    public static final RegistryObject<Aspect> PERMUTATIO = ASPECTS.register("permutatio", () -> new Aspect(List.of(0xCC, 0x59, 0x33), ResourceLocation.parse("thaumaturgy:textures/aspect/permutatio.png")).withCompound(AspectInit.ORDO.get(), AspectInit.PERDITIO.get()));
    public static final RegistryObject<Aspect> VACUOS = ASPECTS.register("vacuos", () -> new Aspect(List.of(0x50, 0x55, 0x73), ResourceLocation.parse("thaumaturgy:textures/aspect/vacuos.png")).withCompound(AspectInit.PERDITIO.get(), AspectInit.AER.get()));
    public static final RegistryObject<Aspect> VITREUS = ASPECTS.register("vitreus", () -> new Aspect(List.of(0xB8, 0xE5, 0xDF), ResourceLocation.parse("thaumaturgy:textures/aspect/vitreus.png")).withCompound(AspectInit.TERRA.get(), AspectInit.ORDO.get()));
    public static final RegistryObject<Aspect> VENEUM = ASPECTS.register("veneum", () -> new Aspect(List.of(0x40, 0x80, 0x4A), ResourceLocation.parse("thaumaturgy:textures/aspect/veneum.png")).withCompound(AspectInit.AQUA.get(), AspectInit.PERDITIO.get()));

    //Other Aspects
    public static final RegistryObject<Aspect> HERBA = ASPECTS.register("herba", () -> new Aspect(List.of(0x1, 0xAC, 0x0), ResourceLocation.parse("thaumaturgy:textures/aspect/herba.png")).withCompound(AspectInit.VICTUS.get(), AspectInit.TERRA.get()));
    public static final RegistryObject<Aspect> CORPUS = ASPECTS.register("corpus", () -> new Aspect(List.of(0xCC, 0x77, 0x66), ResourceLocation.parse("thaumaturgy:textures/aspect/corpus.png")).withCompound(AspectInit.VICTUS.get(), AspectInit.POTENTIA.get()));
    public static final RegistryObject<Aspect> BESTIA = ASPECTS.register("bestia", () -> new Aspect(List.of(0x9F, 0x64, 0x9), ResourceLocation.parse("thaumaturgy:textures/aspect/bestia.png")).withCompound(AspectInit.CORPUS.get(), AspectInit.MOTUS.get()));
    public static final RegistryObject<Aspect> LUX = ASPECTS.register("lux", () -> new Aspect(List.of(0xFF, 0xF6, 0x63), ResourceLocation.parse("thaumaturgy:textures/aspect/lux.png")).withCompound(AspectInit.AER.get(), AspectInit.IGNIS.get()));
    public static final RegistryObject<Aspect> METALLUM = ASPECTS.register("metallum", () -> new Aspect(List.of(0xD8, 0xD8, 0xD8), ResourceLocation.parse("thaumaturgy:textures/aspect/metallum.png")).withCompound(AspectInit.VITREUS.get(), AspectInit.TERRA.get()));
    public static final RegistryObject<Aspect> PRAECANTATIO = ASPECTS.register("praecantatio", () -> new Aspect(List.of(0xE5, 0x17, 0xD4), ResourceLocation.parse("thaumaturgy:textures/aspect/praecantatio.png")).withCompound(AspectInit.POTENTIA.get(), AspectInit.VACUOS.get()));
    public static final RegistryObject<Aspect> VITIUM = ASPECTS.register("vitium", () -> new Aspect(List.of(0xA9, 0x5A, 0xA0), ResourceLocation.parse("thaumaturgy:textures/aspect/vitium.png")).withCompound(AspectInit.PRAECANTATIO.get(), AspectInit.PERDITIO.get()));
    public static final RegistryObject<Aspect> ARBOR = ASPECTS.register("arbor", () -> new Aspect(List.of(0xB3, 0x68, 0x59), ResourceLocation.parse("thaumaturgy:textures/aspect/arbor.png")).withCompound(AspectInit.HERBA.get(), AspectInit.AER.get()));
    public static final RegistryObject<Aspect> TEMPESTAS = ASPECTS.register("tempestas", () -> new Aspect(List.of(0x86, 0x98, 0xB3), ResourceLocation.parse("thaumaturgy:textures/aspect/tempestas.png")).withCompound(AspectInit.AQUA.get(), AspectInit.LUX.get()));
    public static final RegistryObject<Aspect> SENSUS = ASPECTS.register("sensus", () -> new Aspect(List.of(0x80, 0xEA, 0xFF), ResourceLocation.parse("thaumaturgy:textures/aspect/sensus.png")).withCompound(AspectInit.VICTUS.get(), AspectInit.VACUOS.get()));
    public static final RegistryObject<Aspect> SPIRITUS = ASPECTS.register("spiritus", () -> new Aspect(List.of(0xF3, 0xFF, 0xFF), ResourceLocation.parse("thaumaturgy:textures/aspect/spiritus.png")).withCompound(AspectInit.SENSUS.get(), AspectInit.MOTUS.get()));
    public static final RegistryObject<Aspect> COGNITO = ASPECTS.register("cognito", () -> new Aspect(List.of(0xD3, 0xFB, 0xD2), ResourceLocation.parse("thaumaturgy:textures/aspect/cognito.png")).withCompound(AspectInit.SPIRITUS.get(), AspectInit.ORDO.get()));
    public static final RegistryObject<Aspect> HUMANUS = ASPECTS.register("humanus", () -> new Aspect(List.of(0x66, 0x44, 0x33), ResourceLocation.parse("thaumaturgy:textures/aspect/humanus.png")).withCompound(AspectInit.BESTIA.get(), AspectInit.COGNITO.get()));
    public static final RegistryObject<Aspect> MORTUUS = ASPECTS.register("mortuus", () -> new Aspect(List.of(0x64, 0x4D, 0x6A), ResourceLocation.parse("thaumaturgy:textures/aspect/mortuus.png")).withCompound(AspectInit.VICTUS.get(), AspectInit.PERDITIO.get()));
    public static final RegistryObject<Aspect> EXANIMUS = ASPECTS.register("exanimus", () -> new Aspect(List.of(0xCC, 0x59, 0x33), ResourceLocation.parse("thaumaturgy:textures/aspect/exanimus.png")).withCompound(AspectInit.MORTUUS.get(), AspectInit.VICTUS.get()));
    public static final RegistryObject<Aspect> TENEBRAE = ASPECTS.register("tenebrae", () -> new Aspect(List.of(0x7A, 0x41, 0x80), ResourceLocation.parse("thaumaturgy:textures/aspect/tenebrae.png")).withCompound(AspectInit.LUX.get(), AspectInit.VACUOS.get()));
    public static final RegistryObject<Aspect> FAMES = ASPECTS.register("fames", () -> new Aspect(List.of(0xB3, 0x30, 0x30), ResourceLocation.parse("thaumaturgy:textures/aspect/fames.png")).withCompound(AspectInit.BESTIA.get(), AspectInit.VACUOS.get()));
    public static final RegistryObject<Aspect> FACERE = ASPECTS.register("facere", () -> new Aspect(List.of(0xFA, 0xFB, 0xD4), ResourceLocation.parse("thaumaturgy:textures/aspect/facere.png")).withCompound(AspectInit.HUMANUS.get(), AspectInit.POTENTIA.get()));
    public static final RegistryObject<Aspect> LUCRUM = ASPECTS.register("lucrum", () -> new Aspect(List.of(0xF4, 0xF6, 0x2D), ResourceLocation.parse("thaumaturgy:textures/aspect/lucrum.png")).withCompound(AspectInit.HUMANUS.get(), AspectInit.FAMES.get()));
    public static final RegistryObject<Aspect> MACHINA = ASPECTS.register("machina", () -> new Aspect(List.of(0xCF, 0x43, 0x8F), ResourceLocation.parse("thaumaturgy:textures/aspect/machina.png")).withCompound(AspectInit.FACERE.get(), AspectInit.MOTUS.get()));
    public static final RegistryObject<Aspect> PROHIBITUM = ASPECTS.register("prohibitum", () -> new Aspect(List.of(0xCC, 0x33, 0x33), ResourceLocation.parse("thaumaturgy:textures/aspect/prohibitum.png")).withCompound(AspectInit.PRAECANTATIO.get(), AspectInit.TENEBRAE.get()));
    public static final RegistryObject<Aspect> ITER = ASPECTS.register("iter", () -> new Aspect(List.of(0xE8, 0xD2, 0xA0), ResourceLocation.parse("thaumaturgy:textures/aspect/iter.png")).withCompound(AspectInit.MOTUS.get(), AspectInit.TERRA.get()));
    public static final RegistryObject<Aspect> FRIGUS = ASPECTS.register("frigus", () -> new Aspect(List.of(0x17, 0xE5, 0xE5), ResourceLocation.parse("thaumaturgy:textures/aspect/frigus.png")).withCompound(AspectInit.AQUA.get(), AspectInit.VITREUS.get()));
    public static final RegistryObject<Aspect> VINCULUM = ASPECTS.register("vinculum", () -> new Aspect(List.of(0x4C, 0x66, 0x99), ResourceLocation.parse("thaumaturgy:textures/aspect/vinculum.png")).withCompound(AspectInit.MOTUS.get(), AspectInit.PERDITIO.get()));
    public static final RegistryObject<Aspect> VOLATUS = ASPECTS.register("volatus", () -> new Aspect(List.of(0xFF, 0xFF, 0xFF), ResourceLocation.parse("thaumaturgy:textures/aspect/volatus.png")).withCompound(AspectInit.MOTUS.get(), AspectInit.AER.get()));
    public static final RegistryObject<Aspect> MESSIS = ASPECTS.register("messis", () -> new Aspect(List.of(0xCC, 0xCC, 0x52), ResourceLocation.parse("thaumaturgy:textures/aspect/messis.png")).withCompound(AspectInit.HUMANUS.get(), AspectInit.HERBA.get()));
    public static final RegistryObject<Aspect> LIMUS = ASPECTS.register("limus", () -> new Aspect(List.of(0xC9, 0xF1, 0x81), ResourceLocation.parse("thaumaturgy:textures/aspect/limus.png")).withCompound(AspectInit.VICTUS.get(), AspectInit.AQUA.get()));
    public static final RegistryObject<Aspect> PANNUS = ASPECTS.register("pannus", () -> new Aspect(List.of(0xD1, 0xC4, 0x84), ResourceLocation.parse("thaumaturgy:textures/aspect/pannus.png")).withCompound(AspectInit.HUMANUS.get(), AspectInit.BESTIA.get()));

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
