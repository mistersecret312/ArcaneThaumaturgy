package com.mistersecret312.thaumaturgy.block_entities;

import com.mistersecret312.thaumaturgy.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class NitorBlockEntity extends BlockEntity
{

    public NitorBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(BlockEntityInit.NITOR.get(), pPos, pBlockState);
    }


}
