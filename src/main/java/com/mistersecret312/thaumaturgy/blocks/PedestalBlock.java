package com.mistersecret312.thaumaturgy.blocks;

import com.mistersecret312.thaumaturgy.block_entities.PedestalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PedestalBlock extends Block implements EntityBlock
{

    public PedestalBlock(Properties pProperties)
    {
        super(pProperties);
    }



    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult hit)
    {
        ItemStack stack = player.getItemInHand(hand);

        if(level.getBlockEntity(pos) instanceof PedestalBlockEntity pedestal)
        {
            if(!stack.isEmpty())
                return putItem(pedestal, level, player, pos, stack);
            else
                return takeItem(pedestal, level, player, pos);
        }

        return InteractionResult.PASS;
    }

    public InteractionResult takeItem(PedestalBlockEntity pedestal, Level level, Player player, BlockPos pos)
    {
        ItemStack stack = pedestal.getDisplayItem().copy();
        if (stack != ItemStack.EMPTY) {
            player.addItem(stack);
            pedestal.setDisplayItem(ItemStack.EMPTY);

            level.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1, 1);

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public InteractionResult putItem(PedestalBlockEntity pedestal, Level level, Player player, BlockPos pos, ItemStack heldItem)
    {
        ItemStack stack = heldItem.copy();
        if (stack != ItemStack.EMPTY) {
            stack.setCount(1);
            pedestal.setDisplayItem(stack);
            if (!player.isCreative())
                heldItem.shrink(1);

            level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1, 1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston)
    {
        if(pLevel.getBlockEntity(pPos) instanceof PedestalBlockEntity pedestal)
        {
            Containers.dropContents(pLevel, pPos.above(), pedestal.getDroppableInventory());
            pLevel.updateNeighbourForOutputSignal(pPos, this);
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return makeShape();
    }

    public VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.25, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.75, 0.0625, 0.9375, 1, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.25, 0.1875, 0.8125, 0.75, 0.8125), BooleanOp.OR);

        return shape;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
    {
        return new PedestalBlockEntity(pPos, pState);
    }
}
