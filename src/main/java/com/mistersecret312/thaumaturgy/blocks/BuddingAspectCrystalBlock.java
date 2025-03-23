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

public class BuddingAspectCrystalBlock extends AmethystBlock
{
    public static final int GROWTH_CHANCE = 5;
    private static final Direction[] DIRECTIONS = Direction.values();
    private final CrystalAspect aspect;

    public BuddingAspectCrystalBlock(Properties pProperties, CrystalAspect aspect)
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
                    return BlockInit.SMALL_CRYSTALLIZED_TERRA_BUD.get();
                } else if (blockstate.is(BlockInit.SMALL_CRYSTALLIZED_TERRA_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.MEDIUM_CRYSTALLIZED_TERRA_BUD.get();
                } else if (blockstate.is(BlockInit.MEDIUM_CRYSTALLIZED_TERRA_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.LARGE_CRYSTALLIZED_TERRA_BUD.get();
                } else if (blockstate.is(BlockInit.LARGE_CRYSTALLIZED_TERRA_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.CRYSTALLIZED_TERRA_CLUSTER.get();
                }
            case AQUA:
                if (canClusterGrowAtState(blockstate))
                {
                    return BlockInit.SMALL_CRYSTALLIZED_AQUA_BUD.get();
                } else if (blockstate.is(BlockInit.SMALL_CRYSTALLIZED_AQUA_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.MEDIUM_CRYSTALLIZED_AQUA_BUD.get();
                } else if (blockstate.is(BlockInit.MEDIUM_CRYSTALLIZED_AQUA_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.LARGE_CRYSTALLIZED_AQUA_BUD.get();
                } else if (blockstate.is(BlockInit.LARGE_CRYSTALLIZED_AQUA_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.CRYSTALLIZED_AQUA_CLUSTER.get();
                }
            case ORDO:
                if (canClusterGrowAtState(blockstate))
                {
                    return BlockInit.SMALL_CRYSTALLIZED_ORDO_BUD.get();
                } else if (blockstate.is(BlockInit.SMALL_CRYSTALLIZED_ORDO_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.MEDIUM_CRYSTALLIZED_ORDO_BUD.get();
                } else if (blockstate.is(BlockInit.MEDIUM_CRYSTALLIZED_ORDO_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.LARGE_CRYSTALLIZED_ORDO_BUD.get();
                } else if (blockstate.is(BlockInit.LARGE_CRYSTALLIZED_ORDO_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.CRYSTALLIZED_ORDO_CLUSTER.get();
                }
            case IGNIS:
                if (canClusterGrowAtState(blockstate))
                {
                    return BlockInit.SMALL_CRYSTALLIZED_IGNIS_BUD.get();
                } else if (blockstate.is(BlockInit.SMALL_CRYSTALLIZED_IGNIS_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.MEDIUM_CRYSTALLIZED_IGNIS_BUD.get();
                } else if (blockstate.is(BlockInit.MEDIUM_CRYSTALLIZED_IGNIS_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.LARGE_CRYSTALLIZED_IGNIS_BUD.get();
                } else if (blockstate.is(BlockInit.LARGE_CRYSTALLIZED_IGNIS_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.CRYSTALLIZED_IGNIS_CLUSTER.get();
                }
            case PERDITIO:
                if (canClusterGrowAtState(blockstate))
                {
                    return BlockInit.SMALL_CRYSTALLIZED_PERDITIO_BUD.get();
                } else if (blockstate.is(BlockInit.SMALL_CRYSTALLIZED_PERDITIO_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.MEDIUM_CRYSTALLIZED_PERDITIO_BUD.get();
                } else if (blockstate.is(BlockInit.MEDIUM_CRYSTALLIZED_PERDITIO_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.LARGE_CRYSTALLIZED_PERDITIO_BUD.get();
                } else if (blockstate.is(BlockInit.LARGE_CRYSTALLIZED_PERDITIO_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.CRYSTALLIZED_PERDITIO_CLUSTER.get();
                }
            case AER:
                if (canClusterGrowAtState(blockstate))
                {
                    return BlockInit.SMALL_CRYSTALLIZED_AER_BUD.get();
                } else if (blockstate.is(BlockInit.SMALL_CRYSTALLIZED_AER_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.MEDIUM_CRYSTALLIZED_AER_BUD.get();
                } else if (blockstate.is(BlockInit.MEDIUM_CRYSTALLIZED_AER_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.LARGE_CRYSTALLIZED_AER_BUD.get();
                } else if (blockstate.is(BlockInit.LARGE_CRYSTALLIZED_AER_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction)
                {
                    return BlockInit.CRYSTALLIZED_AER_CLUSTER.get();
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
