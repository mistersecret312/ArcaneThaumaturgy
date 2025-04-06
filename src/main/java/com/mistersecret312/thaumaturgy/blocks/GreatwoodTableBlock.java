package com.mistersecret312.thaumaturgy.blocks;

import com.mistersecret312.thaumaturgy.init.BlockInit;
import com.mistersecret312.thaumaturgy.init.SoundInit;
import com.mistersecret312.thaumaturgy.items.WandItem;
import com.mistersecret312.thaumaturgy.util.MathUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class GreatwoodTableBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
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

    public GreatwoodTableBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = pPlayer.getItemInHand(pHand);

        if (stack.getItem() instanceof WandItem) {
            pLevel.setBlockAndUpdate(pPos, BlockInit.ARCANE_CRAFTING_TABLE.get().defaultBlockState().setValue(FACING, pState.getValue(FACING)));

            pLevel.playSound(null, pPos, SoundInit.WAND_USE.get(), SoundSource.BLOCKS);
        }

        return InteractionResult.PASS;
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
