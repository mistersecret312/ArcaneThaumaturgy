package com.mistersecret312.thaumaturgy.init;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.world.TreeConfigurations;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;

public class FeatureInit {
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREATWOOD_TREE = registerKey("tree/twilight_oak_tree");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        context.register(GREATWOOD_TREE, new ConfiguredFeature<>(Feature.TREE, TreeConfigurations.GREATWOOD));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(ArcaneThaumaturgyMod.MODID));
    }
}
