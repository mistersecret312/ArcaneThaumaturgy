package com.mistersecret312.thaumaturgy.blocks;

import com.mistersecret312.thaumaturgy.init.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class EssentiaJarBlock extends Block implements SimpleWaterloggedBlock
{
    private static final VoxelShape SHAPE_UP = Block.box(3, 0, 3, 13, 13, 13);
    private static final VoxelShape SHAPE_DOWN = Block.box(3, 3, 3, 13, 16, 13);
    private static final VoxelShape SHAPE_NORTH = Block.box(3, 3, 3, 13, 13, 16);
    private static final VoxelShape SHAPE_SOUTH = Block.box(3, 3, 0, 13, 13, 13);
    private static final VoxelShape SHAPE_EAST = Block.box(0, 3, 3, 13, 13, 13);
    private static final VoxelShape SHAPE_WEST = Block.box(3, 3, 3, 16, 13, 13);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public EssentiaJarBlock(Properties pProperties)
    {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.UP).setValue(WATERLOGGED, false).setValue(OPEN, false));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit)
    {
        if (pPlayer.isCrouching())
        {
            pLevel.setBlock(pPos, pState.cycle(OPEN), 2);
            if (pState.getValue(OPEN))
            {
                pLevel.playSound(null, pPos, SoundInit.ESSENTIA_JAR_CLOSE.get(), SoundSource.BLOCKS, 1, 0.75F + pLevel.random.nextFloat() / 4);
            } else {
                pLevel.playSound(null, pPos, SoundInit.ESSENTIA_JAR_OPEN.get(), SoundSource.BLOCKS, 1, 0.75F + pLevel.random.nextFloat() / 4);
            }
            return InteractionResult.SUCCESS;
        } else if (pState.getValue(OPEN))
        {
            pLevel.setBlock(pPos, pState.setValue(OPEN, false), 2);
            pLevel.playSound(null, pPos, SoundInit.ESSENTIA_JAR_CLOSE.get(), SoundSource.BLOCKS, 1, 0.75F + pLevel.random.nextFloat() / 4);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        switch (pState.getValue(FACING)) {
            case UP:
                return SHAPE_UP;
            case DOWN:
                return SHAPE_DOWN;
            case NORTH:
                return SHAPE_NORTH;
            case SOUTH:
                return SHAPE_SOUTH;
            case WEST:
                return SHAPE_WEST;
            case EAST:
                return SHAPE_EAST;
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
        blockStateBuilder.add(WATERLOGGED, FACING, OPEN);
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
        return this.defaultBlockState().setValue(WATERLOGGED, accessor.getFluidState(pos).getType() == Fluids.WATER).setValue(FACING, pContext.getClickedFace()).setValue(OPEN, false);
    }

    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
