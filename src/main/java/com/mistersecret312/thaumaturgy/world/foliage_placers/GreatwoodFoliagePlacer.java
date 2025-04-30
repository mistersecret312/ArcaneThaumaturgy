package com.mistersecret312.thaumaturgy.world.foliage_placers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class GreatwoodFoliagePlacer extends FoliagePlacer
{
    public static final Codec<GreatwoodFoliagePlacer> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            IntProvider.CODEC.fieldOf("radius").forGetter(foliage -> foliage.radius),
            IntProvider.CODEC.fieldOf("offset").forGetter(foliage -> foliage.offset)
    ).apply(instance, GreatwoodFoliagePlacer::new));

    public GreatwoodFoliagePlacer(IntProvider pRadius, IntProvider pOffset)
    {
        super(pRadius, pOffset);
    }

    @Override
    protected FoliagePlacerType<?> type()
    {
        return null;
    }

    @Override
    protected void createFoliage(LevelSimulatedReader pLevel, FoliageSetter pBlockSetter, RandomSource pRandom,
                                 TreeConfiguration pConfig, int pMaxFreeTreeHeight, FoliageAttachment pAttachment,
                                 int pFoliageHeight, int pFoliageRadius, int pOffset)
    {

    }

    @Override
    public int foliageHeight(RandomSource pRandom, int pHeight, TreeConfiguration pConfig)
    {
        return 0;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource pRandom, int pLocalX, int pLocalY, int pLocalZ, int pRange,
                                         boolean pLarge)
    {
        return false;
    }
}
