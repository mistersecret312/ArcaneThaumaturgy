package com.mistersecret312.thaumaturgy.world.foliage_placers;

import com.mistersecret312.thaumaturgy.init.FeatureInit;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
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
        return FeatureInit.GREATWOOD_FOLIAGE.get();
    }

    @Override
    protected void createFoliage(LevelSimulatedReader pLevel, FoliageSetter pBlockSetter, RandomSource pRandom,
                                 TreeConfiguration pConfig, int pMaxFreeTreeHeight, FoliageAttachment pAttachment,
                                 int pFoliageHeight, int pFoliageRadius, int pOffset)
    {
        //this.placeLeavesRowWithHangingLeavesBelow(pLevel, pBlockSetter, pRandom, pConfig, pAttachment.pos().above(), pFoliageHeight + 2, -1, false, 0.5f, 0.5f);
        //this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, pAttachment.pos(), pFoliageRadius, pOffset, false);

        pFoliageHeight = 8;

/*        for(int i = 0; i < pFoliageHeight; ++i) {
            if (i == 0 || i == pFoliageHeight - 1) {
                this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, pAttachment.pos(), (pFoliageRadius / 3) * 2, -pFoliageHeight / 2 + i, false);
            } else {
                this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, pAttachment.pos(), pFoliageRadius, -pFoliageHeight / 2 + i, false);
            }
        }*/

        //Top blob
        this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, pAttachment.pos(), 3, pOffset + 1, false);
        this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, pAttachment.pos(), 4, pOffset, false);
        this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, pAttachment.pos(), 4, pOffset - 1, false);
        this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, pAttachment.pos(), 4, pOffset - 2, false);
        this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, pAttachment.pos(), 3, pOffset - 3, false);

        //Lower blob
/*        this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, pAttachment.pos(), 4, pOffset - 4, false);
        this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, pAttachment.pos(), 5, pOffset - 5, false);
        this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, pAttachment.pos(), 5, pOffset - 6, false);
        this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, pAttachment.pos(), 4, pOffset - 7, false);*/
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
        return Mth.square((float) pLocalX + 0.5F) + Mth.square((float) pLocalZ + 0.5F) > (float) (pRange * pRange);
    }
}
