package com.mistersecret312.thaumaturgy.blocks;

import com.mistersecret312.thaumaturgy.util.MathUtil;
import com.mistersecret312.thaumaturgy.util.SittableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class GreatwoodArmchairBlock extends HorizontalDirectionalBlock implements SittableBlock, SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final AABB SEAT = new AABB(0, 0, 0, 1, 0.5, 1);

    public static final VoxelShape SHAPE_NORTH = MathUtil.buildShape(
            Block.box(2, 10, 12, 14, 26, 14),
            Block.box(1, 8, 4, 3, 12, 6),
            Block.box(1, 12, 4, 3, 14, 14),
            Block.box(13, 12, 4, 15, 14, 14),
            Block.box(13, 8, 4, 15, 12, 6),
            Block.box(3, 11, 11, 13, 25, 12),
            Block.box(3, 0, 3, 5, 8, 5),
            Block.box(11, 0, 3, 13, 8, 5),
            Block.box(3, 0, 11, 5, 8, 13),
            Block.box(11, 0, 11, 13, 8, 13),
            Block.box(2, 8, 2, 14, 10, 14),
            Block.box(5, 6, 3, 11, 8, 5),
            Block.box(3, 10, 3, 13, 11, 13),
            Block.box(3, 6, 5, 5, 8, 11),
            Block.box(5, 6, 11, 11, 8, 13),
            Block.box(11, 6, 5, 13, 8, 11)
    );
    public static final VoxelShape SHAPE_SOUTH = MathUtil.buildShape(
            Block.box(2, 10, 2, 14, 26, 4),
            Block.box(13, 8, 10, 15, 12, 12),
            Block.box(13, 12, 2, 15, 14, 12),
            Block.box(1, 12, 2, 3, 14, 12),
            Block.box(1, 8, 10, 3, 12, 12),
            Block.box(3, 11, 4, 13, 25, 5),
            Block.box(11, 0, 11, 13, 8, 13),
            Block.box(3, 0, 11, 5, 8, 13),
            Block.box(11, 0, 3, 13, 8, 5),
            Block.box(3, 0, 3, 5, 8, 5),
            Block.box(2, 8, 2, 14, 10, 14),
            Block.box(5, 6, 11, 11, 8, 13),
            Block.box(3, 10, 3, 13, 11, 13),
            Block.box(11, 6, 5, 13, 8, 11),
            Block.box(5, 6, 3, 11, 8, 5),
            Block.box(3, 6, 5, 5, 8, 11)
    );
    public static final VoxelShape SHAPE_EAST = MathUtil.buildShape(
            Block.box(2, 10, 2, 4, 26, 14),
            Block.box(10, 8, 1, 12, 12, 3),
            Block.box(2, 12, 1, 12, 14, 3),
            Block.box(2, 12, 13, 12, 14, 15),
            Block.box(10, 8, 13, 12, 12, 15),
            Block.box(4, 11, 3, 5, 25, 13),
            Block.box(11, 0, 3, 13, 8, 5),
            Block.box(11, 0, 11, 13, 8, 13),
            Block.box(3, 0, 3, 5, 8, 5),
            Block.box(3, 0, 11, 5, 8, 13),
            Block.box(2, 8, 2, 14, 10, 14),
            Block.box(11, 6, 5, 13, 8, 11),
            Block.box(3, 10, 3, 13, 11, 13),
            Block.box(5, 6, 3, 11, 8, 5),
            Block.box(3, 6, 5, 5, 8, 11),
            Block.box(5, 6, 11, 11, 8, 13)
    );
    public static final VoxelShape SHAPE_WEST = MathUtil.buildShape(
            Block.box(12, 10, 2, 14, 26, 14),
            Block.box(4, 8, 13, 6, 12, 15),
            Block.box(4, 12, 13, 14, 14, 15),
            Block.box(4, 12, 1, 14, 14, 3),
            Block.box(4, 8, 1, 6, 12, 3),
            Block.box(11, 11, 3, 12, 25, 13),
            Block.box(3, 0, 11, 5, 8, 13),
            Block.box(3, 0, 3, 5, 8, 5),
            Block.box(11, 0, 11, 13, 8, 13),
            Block.box(11, 0, 3, 13, 8, 5),
            Block.box(2, 8, 2, 14, 10, 14),
            Block.box(3, 6, 5, 5, 8, 11),
            Block.box(3, 10, 3, 13, 11, 13),
            Block.box(5, 6, 11, 11, 8, 13),
            Block.box(11, 6, 5, 13, 8, 11),
            Block.box(5, 6, 3, 11, 8, 5)
    );

    public GreatwoodArmchairBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (this.sitOn(pLevel, pPos, pPlayer, pPlayer.getDirection())) {
            return InteractionResult.CONSUME;
        }

        return InteractionResult.CONSUME_PARTIAL;
    }

    @Override
    public AABB getSeatSize(BlockState state) {
        return SEAT;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        switch (pState.getValue(FACING)) {
            case NORTH:
                return SHAPE_NORTH;
            case SOUTH:
                return SHAPE_SOUTH;
            case EAST:
                return SHAPE_EAST;
            case WEST:
                return SHAPE_WEST;
            default:
                return SHAPE_NORTH;
        }
    }

    public RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.MODEL;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateBuilder)
    {
        blockStateBuilder.add(WATERLOGGED, FACING);
        super.createBlockStateDefinition(blockStateBuilder);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos)
    {
        if (pState.getValue(WATERLOGGED))
        {
            pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
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
}
