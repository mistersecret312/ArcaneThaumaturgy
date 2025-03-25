package com.mistersecret312.thaumaturgy.blocks;

import com.mistersecret312.thaumaturgy.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

import static net.minecraft.world.level.block.BuddingAmethystBlock.canClusterGrowAtState;

public class BuddingVisCrystalBlock extends AmethystBlock
{
    public static final int GROWTH_CHANCE = 5;
    private static final Direction[] DIRECTIONS = Direction.values();
    private final CrystalAspect aspect;

    public BuddingVisCrystalBlock(Properties pProperties, CrystalAspect aspect)
    {
        super(pProperties);
        this.aspect = aspect;
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        if (pRandom.nextInt(5) == 0) {
            Direction direction = DIRECTIONS[pRandom.nextInt(DIRECTIONS.length)];
            BlockPos blockpos = pPos.relative(direction);
            BlockState blockstate = pLevel.getBlockState(blockpos);
            Block block = typeToBud(aspect, blockstate, direction);

            if (block != null) {
                BlockState blockstate1 = block.defaultBlockState().setValue(AmethystClusterBlock.FACING, direction).setValue(AmethystClusterBlock.WATERLOGGED, Boolean.valueOf(blockstate.getFluidState().getType() == Fluids.WATER));
                pLevel.setBlockAndUpdate(blockpos, blockstate1);
            }

        }
    }

    public Block typeToBud(CrystalAspect aspect, BlockState blockstate, Direction direction)
    {
        switch (aspect)
        {
            case TERRA:
                if (canClusterGrowAtState(blockstate))
                {
                    return BlockInit.TERRA_SMALL_VIS_CRYSTAL_BUD.get();
                } else if (blockstate.is(BlockInit.TERRA_SMALL_VIS_CRYSTAL_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.TERRA_MEDIUM_VIS_CRYSTAL_BUD.get();
                } else if (blockstate.is(BlockInit.TERRA_MEDIUM_VIS_CRYSTAL_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.TERRA_LARGE_VIS_CRYSTAL_BUD.get();
                } else if (blockstate.is(BlockInit.TERRA_LARGE_VIS_CRYSTAL_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.TERRA_VIS_CRYSTAL_CLUSTER.get();
                }
            case AQUA:
                if (canClusterGrowAtState(blockstate))
                {
                    return BlockInit.AQUA_SMALL_VIS_CRYSTAL_BUD.get();
                } else if (blockstate.is(BlockInit.AQUA_SMALL_VIS_CRYSTAL_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.AQUA_MEDIUM_VIS_CRYSTAL_BUD.get();
                } else if (blockstate.is(BlockInit.AQUA_MEDIUM_VIS_CRYSTAL_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.AQUA_LARGE_VIS_CRYSTAL_BUD.get();
                } else if (blockstate.is(BlockInit.AQUA_LARGE_VIS_CRYSTAL_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.AQUA_VIS_CRYSTAL_CLUSTER.get();
                }
            case ORDO:
                if (canClusterGrowAtState(blockstate))
                {
                    return BlockInit.ORDO_SMALL_VIS_CRYSTAL_BUD.get();
                } else if (blockstate.is(BlockInit.ORDO_SMALL_VIS_CRYSTAL_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.ORDO_MEDIUM_VIS_CRYSTAL_BUD.get();
                } else if (blockstate.is(BlockInit.ORDO_MEDIUM_VIS_CRYSTAL_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.ORDO_LARGE_VIS_CRYSTAL_BUD.get();
                } else if (blockstate.is(BlockInit.ORDO_LARGE_VIS_CRYSTAL_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.ORDO_VIS_CRYSTAL_CLUSTER.get();
                }
            case IGNIS:
                if (canClusterGrowAtState(blockstate))
                {
                    return BlockInit.IGNIS_SMALL_VIS_CRYSTAL_BUD.get();
                } else if (blockstate.is(BlockInit.IGNIS_SMALL_VIS_CRYSTAL_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.IGNIS_MEDIUM_VIS_CRYSTAL_BUD.get();
                } else if (blockstate.is(BlockInit.IGNIS_MEDIUM_VIS_CRYSTAL_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.IGNIS_LARGE_VIS_CRYSTAL_BUD.get();
                } else if (blockstate.is(BlockInit.IGNIS_LARGE_VIS_CRYSTAL_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.IGNIS_VIS_CRYSTAL_CLUSTER.get();
                }
            case PERDITIO:
                if (canClusterGrowAtState(blockstate))
                {
                    return BlockInit.PERDITIO_SMALL_VIS_CRYSTAL_BUD.get();
                } else if (blockstate.is(BlockInit.PERDITIO_SMALL_VIS_CRYSTAL_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.PERDITIO_MEDIUM_VIS_CRYSTAL_BUD.get();
                } else if (blockstate.is(BlockInit.PERDITIO_MEDIUM_VIS_CRYSTAL_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.PERDITIO_LARGE_VIS_CRYSTAL_BUD.get();
                } else if (blockstate.is(BlockInit.PERDITIO_LARGE_VIS_CRYSTAL_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.PERDITIO_VIS_CRYSTAL_CLUSTER.get();
                }
            case AER:
                if (canClusterGrowAtState(blockstate))
                {
                    return BlockInit.AER_SMALL_VIS_CRYSTAL_BUD.get();
                } else if (blockstate.is(BlockInit.AER_SMALL_VIS_CRYSTAL_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.AER_MEDIUM_VIS_CRYSTAL_BUD.get();
                } else if (blockstate.is(BlockInit.AER_MEDIUM_VIS_CRYSTAL_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.AER_LARGE_VIS_CRYSTAL_BUD.get();
                } else if (blockstate.is(BlockInit.AER_LARGE_VIS_CRYSTAL_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.AER_VIS_CRYSTAL_CLUSTER.get();
                }
        }
        return null;
    }

    public enum CrystalAspect implements StringRepresentable
    {
        TERRA("terra"),
        AQUA("aqua"),
        ORDO("ordo"),
        IGNIS("ignis"),
        PERDITIO("perditio"),
        AER("aer");

        private final String name;
        CrystalAspect(String name)
        {
            this.name = name;
        }

        @Override
        public String getSerializedName()
        {
            return name;
        }
    }
}
