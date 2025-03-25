package com.mistersecret312.thaumaturgy.blocks;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public class RunicMatrixBlock extends Block
{
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    public RunicMatrixBlock(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(ACTIVE);
        super.createBlockStateDefinition(pBuilder);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        return this.defaultBlockState().setValue(ACTIVE, false);
    }
}
