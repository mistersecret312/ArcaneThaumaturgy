package com.mistersecret312.thaumaturgy.init;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.world.foliage_placers.GreatwoodFoliagePlacer;
import com.mistersecret312.thaumaturgy.world.trunk_placers.GreatwoodTrunkPlacer;
import com.mistersecret312.thaumaturgy.world.trunk_placers.SilverwoodTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FeatureInit {

    public static final DeferredRegister<TrunkPlacerType<?>> TRUNKS = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, ArcaneThaumaturgyMod.MODID);
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGES = DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, ArcaneThaumaturgyMod.MODID);

    public static final RegistryObject<TrunkPlacerType<GreatwoodTrunkPlacer>> GREATWOOD_TRUNK = TRUNKS.register("greatwood_trunk_placer", () -> new TrunkPlacerType<>(GreatwoodTrunkPlacer.CODEC));
    public static final RegistryObject<TrunkPlacerType<SilverwoodTrunkPlacer>> SILVERWOOD_TRUNK = TRUNKS.register("silverwood_trunk_placer", () -> new TrunkPlacerType<>(SilverwoodTrunkPlacer.CODEC));
    public static final RegistryObject<FoliagePlacerType<?>> GREATWOOD_FOLIAGE = FOLIAGES.register("greatwood_foliage_placer", () -> new FoliagePlacerType<>(GreatwoodFoliagePlacer.CODEC));

    public static void register(IEventBus bus)
    {
        TRUNKS.register(bus);
        FOLIAGES.register(bus);
    }

    public static final ResourceKey<ConfiguredFeature<?, ?>> GREATWOOD_TREE = registerKey("tree/greatwood_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SILVERWOOD_TREE = registerKey("tree/silverwood_tree");

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(ArcaneThaumaturgyMod.MODID, name));
    }


}
