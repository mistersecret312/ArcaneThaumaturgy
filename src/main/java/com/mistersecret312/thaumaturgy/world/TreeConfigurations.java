package com.mistersecret312.thaumaturgy.world;

import com.mistersecret312.thaumaturgy.init.BlockInit;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class TreeConfigurations {
    public static final TreeConfiguration GREATWOOD = new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(BlockInit.GREATWOOD_LOG.get()),
            new StraightTrunkPlacer(4, 2, 0),
            BlockStateProvider.simple(BlockInit.GREATWOOD_LEAVES.get()),
            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
            new TwoLayersFeatureSize(1, 0, 1)
    )
            .build();
}
