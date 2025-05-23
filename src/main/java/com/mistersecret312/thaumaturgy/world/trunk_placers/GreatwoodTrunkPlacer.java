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
        int branchAmount = pRandom.nextInt(3, 5);
        for (int i = 0; i < branchAmount; i++) {
            trunkBlocks.addAll(placeBranch(pLevel, pBlockSetter, pRandom, pPos, pConfig, pRandom.nextInt(4, 7), coreHeight));
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

    private List<FoliagePlacer.FoliageAttachment> placeBranch(LevelSimulatedReader pLevel,
                             BiConsumer<BlockPos, BlockState> pBlockSetter,
                             RandomSource pRandom, BlockPos pPos,
                             TreeConfiguration pConfig, int branchLength, int coreHeight)
    {
        int direction = pRandom.nextInt(1, 4);
        int branchHeight = pRandom.nextInt(coreHeight / 2, coreHeight - 3);

        List<FoliagePlacer.FoliageAttachment> branchPlacers = Lists.newArrayList();

        if (direction == 1)
        {
            for (int i = 0; i < branchLength; i++) {
                int yOffset = 0;
                if (i > branchLength / 2) {
                    yOffset = 1;
                }
                this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(i, branchHeight + yOffset, 0), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
                this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(0, branchHeight + yOffset, i), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z));
            }

            branchPlacers.add(new FoliagePlacer.FoliageAttachment(pPos.offset(branchLength, branchHeight + 3, 0), -2, false));
            branchPlacers.add(new FoliagePlacer.FoliageAttachment(pPos.offset(0, branchHeight + 4, branchLength), -2, false));
        } else if (direction == 2)
        {
            for (int i = 0; i < branchLength; i++) {
                int yOffset = 0;
                if (i > branchLength / 2) {
                    yOffset = 1;
                }
                this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(-i, branchHeight + yOffset, 0), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
                this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(0, branchHeight + yOffset, -i), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z));
            }

            branchPlacers.add(new FoliagePlacer.FoliageAttachment(pPos.offset(-branchLength, branchHeight + 3, 0), -2, false));
            branchPlacers.add(new FoliagePlacer.FoliageAttachment(pPos.offset(0, branchHeight + 4, -branchLength), -2, false));
        } else if (direction == 3)
        {
            for (int i = 0; i < branchLength; i++) {
                int yOffset = 0;
                if (i > branchLength / 2) {
                    yOffset = 1;
                }
                this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(i, branchHeight + yOffset, 0), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
                this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(0, branchHeight + yOffset, -i), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z));
            }

            branchPlacers.add(new FoliagePlacer.FoliageAttachment(pPos.offset(branchLength, branchHeight + 3, 0), -2, false));
            branchPlacers.add(new FoliagePlacer.FoliageAttachment(pPos.offset(0, branchHeight + 4, -branchLength), -2, false));
        } else
        {
            for (int i = 0; i < branchLength; i++) {
                int yOffset = 0;
                if (i > branchLength / 2) {
                    yOffset = 1;
                }
                this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(-i, branchHeight + yOffset, 0), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
                this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(0, branchHeight + yOffset, i), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z));
            }

            branchPlacers.add(new FoliagePlacer.FoliageAttachment(pPos.offset(-branchLength, branchHeight + 3, 0), -2, false));
            branchPlacers.add(new FoliagePlacer.FoliageAttachment(pPos.offset(0, branchHeight + 4, branchLength), -2, false));
        }
        return branchPlacers;
    }
}