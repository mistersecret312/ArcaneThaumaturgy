package com.mistersecret312.thaumaturgy.block_entities;

import com.mistersecret312.thaumaturgy.init.BlockEntityInit;
import com.mistersecret312.thaumaturgy.init.NetworkInit;
import com.mistersecret312.thaumaturgy.network.packets.UpdatePedestalClientboundPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PedestalBlockEntity extends BlockEntity
{
    public static final String STORED = "stored";

    private ItemStack stored = ItemStack.EMPTY;

    public PedestalBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(BlockEntityInit.PEDESTAL.get(), pPos, pBlockState);
    }

    public ItemStack getStored()
    {
        return stored;
    }

    public void setStored(ItemStack stored)
    {
        this.stored = stored;
        NetworkInit.sendToTracking(this, new UpdatePedestalClientboundPacket(this.getBlockPos(), stored));
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        stored.deserializeNBT(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.put(STORED, stored.serializeNBT());
    }
}
