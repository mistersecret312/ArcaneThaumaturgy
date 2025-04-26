package com.mistersecret312.thaumaturgy.blocks;

import com.mistersecret312.thaumaturgy.block_entities.ResearchTableBlockEntity;
import com.mistersecret312.thaumaturgy.util.MathUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class ResearchTableBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock, EntityBlock
{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty DUMMY = BooleanProperty.create("dummy");
    public static final VoxelShape SHAPE_NORTH_SOUTH = MathUtil.buildShape(
            Block.box(13, 0, 1, 15, 13, 3),
            Block.box(0, 13, 0, 16, 16, 16),
            Block.box(1, 0, 1, 3, 13, 3),
            Block.box(13, 0, 13, 15, 13, 15),
            Block.box(1, 0, 13, 3, 13, 15),
            Block.box(13, 5, 3, 15, 9, 13),
            Block.box(3, 5, 7, 13, 9, 9),
            Block.box(1, 5, 3, 3, 9, 13),
            Block.box(3, 11, 13, 13, 13, 15),
            Block.box(3, 11, 1, 13, 13, 3),
            Block.box(1, 11, 3, 3, 13, 13),
            Block.box(13, 11, 3, 15, 13, 13)
    );
    public static final VoxelShape SHAPE_EAST_WEST = MathUtil.buildShape(
            Block.box(13, 0, 13, 15, 13, 15),
            Block.box(0, 13, 0, 16, 16, 16),
            Block.box(13, 0, 1, 15, 13, 3),
            Block.box(1, 0, 13, 3, 13, 15),
            Block.box(1, 0, 1, 3, 13, 3),
            Block.box(3, 5, 13, 13, 9, 15),
            Block.box(7, 5, 3, 9, 9, 13),
            Block.box(3, 5, 1, 13, 9, 3),
            Block.box(1, 11, 3, 3, 13, 13),
            Block.box(13, 11, 3, 15, 13, 13),
            Block.box(3, 11, 1, 13, 13, 3),
            Block.box(3, 11, 13, 13, 13, 15)
    );

    public ResearchTableBlock(Properties pProperties)
    {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false).setValue(DUMMY, false));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        switch (pState.getValue(FACING)) {
            case NORTH, SOUTH:
                return SHAPE_NORTH_SOUTH;
            case EAST, WEST:
                return SHAPE_EAST_WEST;
            default:
                return SHAPE_NORTH_SOUTH;
        }
    }

    public RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.MODEL;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateBuilder)
    {
        blockStateBuilder.add(WATERLOGGED, FACING, DUMMY);
        super.createBlockStateDefinition(blockStateBuilder);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos)
    {
        if (pState.getValue(WATERLOGGED))
        {
            pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        Direction facing = pState.getValue(FACING);
        boolean dummy = pState.getValue(DUMMY);
        {
            // Check if current facing is correct
            BlockPos otherHalf = pPos.relative(facing.getClockWise(), dummy?-1: 1);
            BlockState otherState = pLevel.getBlockState(otherHalf);
            if(otherState.getBlock()==this&&otherState.getValue(FACING)==facing&&otherState.getValue(DUMMY)==!dummy)
                return pState;
        }
        // Find correct facing, or remove
        for(Direction candidate : FACING.getPossibleValues())
            if(candidate!=facing)
            {
                BlockPos otherHalf = pPos.relative(candidate.getClockWise(), dummy?-1: 1);
                BlockState otherState = pLevel.getBlockState(otherHalf);
                if(otherState.getBlock()==this&&otherState.getValue(FACING)==candidate&&otherState.getValue(DUMMY)==!dummy)
                    return pState.setValue(FACING, candidate);
            }

        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston)
    {
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
        boolean dummy = pState.getValue(DUMMY);
        BlockState state = pLevel.getBlockState(dummy ? pPos.relative(pState.getValue(FACING).getClockWise()) : pPos.relative(pState.getValue(FACING).getCounterClockWise()));

        if(state.getBlock() instanceof ResearchTableBlock)
            pLevel.removeBlock(pPos.relative(dummy ? pState.getValue(FACING).getClockWise() : pState.getValue(FACING).getCounterClockWise()), false);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        LevelAccessor accessor = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        return this.defaultBlockState().setValue(WATERLOGGED, accessor.getFluidState(pos).getType() == Fluids.WATER).setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public @org.jetbrains.annotations.Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
    {
        if(pState.getValue(DUMMY))
            return null;
        return new ResearchTableBlockEntity(pPos, pState);
    }
}
