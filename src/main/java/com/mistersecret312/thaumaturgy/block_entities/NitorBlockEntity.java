package com.mistersecret312.thaumaturgy.block_entities;

import com.mistersecret312.thaumaturgy.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class NitorBlockEntity extends BlockEntity
{
    public NitorBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(BlockEntityInit.NITOR.get(), pPos, pBlockState);
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

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
    }
}
