package com.mistersecret312.thaumaturgy.block_entities;

import com.mistersecret312.thaumaturgy.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class PedestalBlockEntity extends BlockEntity
{
    public static final String STORED = "stored";

    private ItemStackHandler stored;

    public PedestalBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(BlockEntityInit.PEDESTAL.get(), pPos, pBlockState);
        this.stored = new ItemStackHandler(1){
            @Override
            public int getSlotLimit(int slot)
            {
                return 1;
            }

            @Override
            protected void onContentsChanged(int slot)
            {
                markUpdated();
            }
        };
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

    public NonNullList<ItemStack> getDroppableInventory() {
        NonNullList<ItemStack> drops = NonNullList.create();
        drops.add(getDisplayItem());
        setDisplayItem(ItemStack.EMPTY);
        return drops;
    }

    public void markUpdated()
    {
        super.setChanged();
        if(level != null)
            level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
    }

    public ItemStack getDisplayItem()
    {
        if(!stored.getStackInSlot(0).isEmpty())
            return stored.getStackInSlot(0).copy();
        return ItemStack.EMPTY;
    }

    public void setDisplayItem(ItemStack stack)
    {
        stored.setStackInSlot(0, stack.copy());
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        stored.deserializeNBT(tag.getCompound(STORED));
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.put(STORED, stored.serializeNBT());
    }
}
