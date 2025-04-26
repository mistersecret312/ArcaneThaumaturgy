package com.mistersecret312.thaumaturgy.items;

import com.mistersecret312.thaumaturgy.blocks.NitorBlock;
import com.mistersecret312.thaumaturgy.init.BlockInit;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class NitorItem extends BlockItem
{

    private NitorBlock.NitorType nitorType;

    public NitorItem(Properties pProperties, NitorBlock.NitorType type)
    {
        super(BlockInit.NITOR.get(), pProperties);
        this.nitorType = type;
    }

    public NitorBlock.NitorType getNitorType()
    {
        return nitorType;
    }

    @Override
    protected @Nullable BlockState getPlacementState(BlockPlaceContext pContext)
    {
        return BlockInit.NITOR.get().defaultBlockState().setValue(NitorBlock.TYPE, nitorType);
    }
}
