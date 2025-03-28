package com.mistersecret312.thaumaturgy.block_entities;

import com.mistersecret312.thaumaturgy.blocks.CrucibleBlock;
import com.mistersecret312.thaumaturgy.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CrucibleBlockEntity extends BlockEntity
{


    public CrucibleBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(BlockEntityInit.CRUCIBLE.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag)
    {
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag)
    {
        super.load(pTag);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        return this.saveWithoutMetadata();
    }

    public boolean isBoiling()
    {
        return this.getBlockState().getValue(CrucibleBlock.IS_BOILING);
    }

    public int getWaterLevel()
    {
        return this.getBlockState().getValue(CrucibleBlock.FILL_LEVEL);
    }

}
