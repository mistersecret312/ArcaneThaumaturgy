package com.mistersecret312.thaumaturgy.world.trunk_placers;

import com.google.common.collect.Lists;
import com.mistersecret312.thaumaturgy.init.FeatureInit;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.function.BiConsumer;

public class GreatwoodTrunkPlacer extends TrunkPlacer
{
    public static final Codec<GreatwoodTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("base_height").forGetter(tree -> tree.baseHeight),
            Codec.INT.fieldOf("height_rand_a").forGetter(tree -> tree.heightRandA),
            Codec.INT.fieldOf("height_rand_b").forGetter(tree -> tree.heightRandB)
    ).apply(instance, GreatwoodTrunkPlacer::new));

    public GreatwoodTrunkPlacer(int pBaseHeight, int pHeightRandA, int pHeightRandB)
    {
        super(pBaseHeight, pHeightRandA, pHeightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type()
    {
        return FeatureInit.GREATWOOD_TRUNK.get();
    }

/*    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader pLevel,
                                                            BiConsumer<BlockPos, BlockState> pBlockSetter,
                                                            RandomSource pRandom, int pFreeTreeHeight, BlockPos pPos,
                                                            TreeConfiguration pConfig)
    {
        List<FoliagePlacer.FoliageAttachment> trunkBlocks = Lists.newArrayList();

        for(int i = pFreeTreeHeight - 2 - pRandom.nextInt(4); i > pFreeTreeHeight / 2; i -= 2 + pRandom.nextInt(4)) {
            float randomOffset = pRandom.nextFloat() * ((float) Math.PI * 2F);
            int var1 = 0;
            int var2 = 0;

            for(int ii = 0; ii < 5; ++ii) {
                var1 = (int) (1.5F + Mth.cos(randomOffset) * (float) ii);
                var2 = (int) (1.5F + Mth.sin(randomOffset) * (float) ii);
                BlockPos logPos = pPos.offset(var1, i - 3 + ii / 2, var2);
                this.placeLog(pLevel, pBlockSetter, pRandom, logPos, pConfig);
            }

            trunkBlocks.add(new FoliagePlacer.FoliageAttachment(pPos.offset(var1, i, var2), -2, false));
        }

        return trunkBlocks;
    }*/

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader pLevel,
                                                            BiConsumer<BlockPos, BlockState> pBlockSetter,
                                                            RandomSource pRandom, int pFreeTreeHeight, BlockPos pPos,
                                                            TreeConfiguration pConfig)
    {
        List<FoliagePlacer.FoliageAttachment> trunkBlocks = Lists.newArrayList();

        int coreHeight = pFreeTreeHeight - pRandom.nextInt(this.heightRandA, this.heightRandB);

        trunkBlocks.add(placeCoreTrunk(pLevel, pBlockSetter, pRandom, pPos, pConfig, coreHeight));
        //Place crest
        placeTrunkLine(pLevel, pBlockSetter, pRandom, pPos.offset(1, 0, 0), pConfig, (pFreeTreeHeight / 3) * 2);
        placeTrunkLine(pLevel, pBlockSetter, pRandom, pPos.offset(-1, 0, 0), pConfig, (pFreeTreeHeight / 3) * 2);
        placeTrunkLine(pLevel, pBlockSetter, pRandom, pPos.offset(0, 0, 1), pConfig, (pFreeTreeHeight / 3) * 2);
        placeTrunkLine(pLevel, pBlockSetter, pRandom, pPos.offset(0, 0, -1), pConfig, (pFreeTreeHeight / 3) * 2);
        //Place cross
        placeTrunkLine(pLevel, pBlockSetter, pRandom, pPos.offset(1, 0, -1), pConfig, pFreeTreeHeight / 3);
        placeTrunkLine(pLevel, pBlockSetter, pRandom, pPos.offset(-1, 0, 1), pConfig, pFreeTreeHeight / 3);
        placeTrunkLine(pLevel, pBlockSetter, pRandom, pPos.offset(1, 0, 1), pConfig, pFreeTreeHeight / 3);
        placeTrunkLine(pLevel, pBlockSetter, pRandom, pPos.offset(-1, 0, -1), pConfig, pFreeTreeHeight / 3);
        //Place crest roots
        placeTrunkLine(pLevel, pBlockSetter, pRandom, pPos.offset(2, 0, 0), pConfig, pFreeTreeHeight / 5);
        placeTrunkLine(pLevel, pBlockSetter, pRandom, pPos.offset(-2, 0, 0), pConfig, pFreeTreeHeight / 5);
        placeTrunkLine(pLevel, pBlockSetter, pRandom, pPos.offset(0, 0, 2), pConfig, pFreeTreeHeight / 5);
        placeTrunkLine(pLevel, pBlockSetter, pRandom, pPos.offset(0, 0, -2), pConfig, pFreeTreeHeight / 5);
        //Branches
        int branchAmount = pRandom.nextInt(2, 4);
        for (int i = 0; i < branchAmount; i++) {
            trunkBlocks.add(placeBranch(pLevel, pBlockSetter, pRandom, pPos, pConfig, pRandom.nextInt(4, 7), coreHeight));
        }

        return trunkBlocks;
    }

    private void placeTrunkLine(LevelSimulatedReader pLevel,
                                BiConsumer<BlockPos, BlockState> pBlockSetter,
                                RandomSource pRandom, BlockPos pPos,
                                TreeConfiguration pConfig, int lineHeight)
    {
        int randomOffset = pRandom.nextInt(this.heightRandA, this.heightRandB);

        for (int currentLog = 0; currentLog < lineHeight - randomOffset; currentLog++)
        {
            this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(0, currentLog, 0), pConfig);
        }
    }

    private FoliagePlacer.FoliageAttachment placeCoreTrunk(LevelSimulatedReader pLevel,
                                BiConsumer<BlockPos, BlockState> pBlockSetter,
                                RandomSource pRandom, BlockPos pPos,
                                TreeConfiguration pConfig, int lineHeight)
    {
        for (int currentLog = 0; currentLog < lineHeight; currentLog++)
        {
            this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(0, currentLog, 0), pConfig);
        }

        return new FoliagePlacer.FoliageAttachment(pPos.offset(0, lineHeight, 0), -2, false);
    }

    private FoliagePlacer.FoliageAttachment placeBranch(LevelSimulatedReader pLevel,
                             BiConsumer<BlockPos, BlockState> pBlockSetter,
                             RandomSource pRandom, BlockPos pPos,
                             TreeConfiguration pConfig, int branchLength, int coreHeight)
    {
        int direction = pRandom.nextInt(1, 4);
        int branchHeight = pRandom.nextInt(coreHeight / 4, (coreHeight / 5) * 4);

        if (direction == 1)
        {
            for (int i = 0; i < branchLength; i++) {
                int yOffset = 0;
                if (i > branchLength / 2) {
                    yOffset = 1;
                }
                this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(i, branchHeight + yOffset, 0), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
            }

            return new FoliagePlacer.FoliageAttachment(pPos.offset(branchLength, branchHeight + 3, 0), -2, false);
        } else if (direction == 2)
        {
            for (int i = 0; i < branchLength; i++) {
                int yOffset = 0;
                if (i > branchLength / 2) {
                    yOffset = 1;
                }
                this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(-i, branchHeight + yOffset, 0), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
            }

            return new FoliagePlacer.FoliageAttachment(pPos.offset(-branchLength, branchHeight + 3, 0), -2, false);
        } else if (direction == 3)
        {
            for (int i = 0; i < branchLength; i++) {
                int yOffset = 0;
                if (i > branchLength / 2) {
                    yOffset = 1;
                }
                this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(0, branchHeight + yOffset, i), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z));
            }

            return new FoliagePlacer.FoliageAttachment(pPos.offset(0, branchHeight + 3, branchLength), -2, false);
        } else
        {
            for (int i = 0; i < branchLength; i++) {
                int yOffset = 0;
                if (i > branchLength / 2) {
                    yOffset = 1;
                }
                this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(0, branchHeight + yOffset, -i), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z));
            }

            return new FoliagePlacer.FoliageAttachment(pPos.offset(0, branchHeight + 3, -branchLength), -2, false);
        }
    }
}