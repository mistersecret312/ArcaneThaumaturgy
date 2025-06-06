package com.mistersecret312.thaumaturgy.items;

import com.mistersecret312.thaumaturgy.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class SilverwoodTapBlockItem extends BlockItem {
    public SilverwoodTapBlockItem(Properties pProperties) {
        super(BlockInit.SILVERWOOD_TAP.get(), pProperties);
    }

    @Override
    protected boolean canPlace(BlockPlaceContext pContext, BlockState pState) {
        return isOnSilverwoodTree(pContext.getLevel(), pContext.getClickedPos(), pContext.getHorizontalDirection());
    }

    public boolean isOnSilverwoodTree(Level pLevel, BlockPos pPos, Direction tapDirection) {
        Block tappedBlock = pLevel.getBlockState(pPos.relative(tapDirection)).getBlock();
        return tappedBlock.equals(BlockInit.SILVERWOOD_LOG.get()) || tappedBlock.equals(BlockInit.SILVERWOOD_WOOD.get()) || tappedBlock.equals(BlockInit.STRIPPED_SILVERWOOD_LOG.get()) || tappedBlock.equals(BlockInit.STRIPPED_SILVERWOOD_WOOD.get());
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext pContext, BlockState pState) {
        Level pLevel = pContext.getLevel();
        BlockPos pPos = pContext.getClickedPos();

        pLevel.playSound(null, pPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1, 1.25f);
        return super.placeBlock(pContext, pState);
    }
}
