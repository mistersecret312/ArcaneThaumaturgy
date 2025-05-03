package com.mistersecret312.thaumaturgy.world.trunk_placers;

import com.google.common.collect.Lists;
import com.mistersecret312.thaumaturgy.init.FeatureInit;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class SilverwoodTrunkPlacer extends TrunkPlacer {
    public static final Codec<SilverwoodTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("base_height").forGetter(tree -> tree.baseHeight),
            Codec.INT.fieldOf("height_rand_a").forGetter(tree -> tree.heightRandA),
            Codec.INT.fieldOf("height_rand_b").forGetter(tree -> tree.heightRandB)
    ).apply(instance, SilverwoodTrunkPlacer::new));

    public SilverwoodTrunkPlacer(int pBaseHeight, int pHeightRandA, int pHeightRandB)
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
        //Place roots
        for (int i = 0; i < 4; i++) {
            if (pRandom.nextBoolean()) {
                placeRoot(pLevel, pBlockSetter, pRandom, pPos, pConfig, 1 + i);
            }
        }
        //Branches
        int branchAmount = pRandom.nextInt(2, 5);
        for (int i = 0; i < branchAmount; i++) {
            trunkBlocks.addAll(placeBranch(pLevel, pBlockSetter, pRandom, pPos, pConfig, pRandom.nextInt(4, 6), coreHeight));
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
            }

            branchPlacers.add(new FoliagePlacer.FoliageAttachment(pPos.offset(branchLength, branchHeight + 3, 0), -2, false));
        } else if (direction == 2)
        {
            for (int i = 0; i < branchLength; i++) {
                int yOffset = 0;
                if (i > branchLength / 2) {
                    yOffset = 1;
                }
                this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(-i, branchHeight + yOffset, 0), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
            }

            branchPlacers.add(new FoliagePlacer.FoliageAttachment(pPos.offset(-branchLength, branchHeight + 3, 0), -2, false));
        } else if (direction == 3)
        {
            for (int i = 0; i < branchLength; i++) {
                int yOffset = 0;
                if (i > branchLength / 2) {
                    yOffset = 1;
                }
                this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(0, branchHeight + yOffset, i), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z));
            }

            branchPlacers.add(new FoliagePlacer.FoliageAttachment(pPos.offset(0, branchHeight + 3, branchLength), -2, false));
        } else
        {
            for (int i = 0; i < branchLength; i++) {
                int yOffset = 0;
                if (i > branchLength / 2) {
                    yOffset = 1;
                }
                this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(0, branchHeight + yOffset, -i), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z));
            }

            branchPlacers.add(new FoliagePlacer.FoliageAttachment(pPos.offset(0, branchHeight + 3, -branchLength), -2, false));
        }
        return branchPlacers;
    }

    private void placeRoot(LevelSimulatedReader pLevel,
                                                              BiConsumer<BlockPos, BlockState> pBlockSetter,
                                                              RandomSource pRandom, BlockPos pPos,
                                                              TreeConfiguration pConfig, int direction)
    {
        if (direction == 1)
        {
            this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(2, 1, 0), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
            this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(2, 0, 0), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
            this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(3, 0, 0), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
        } else if (direction == 2)
        {
            this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(-2, 1, 0), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
            this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(-2, 0, 0), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
            this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(-3, 0, 0), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
        } else if (direction == 3)
        {
            this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(0, 1, 2), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z));
            this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(0, 0, 2), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z));
            this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(0, 0, 3), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z));
        } else
        {
            this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(0, 1, -2), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z));
            this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(0, 0, -2), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z));
            this.placeLog(pLevel, pBlockSetter, pRandom, pPos.offset(0, 0, -3), pConfig, (state) -> state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z));
        }
    }
}
