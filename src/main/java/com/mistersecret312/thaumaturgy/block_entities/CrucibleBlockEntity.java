package com.mistersecret312.thaumaturgy.block_entities;

import com.mistersecret312.thaumaturgy.blocks.CrucibleBlock;
import com.mistersecret312.thaumaturgy.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CrucibleBlockEntity extends BlockEntity
{
    private int waterLevel;
    private int itemsCrafted;

    public CrucibleBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(BlockEntityInit.CRUCIBLE.get(), pPos, pBlockState);
        waterLevel = 0; //Cap 4
        itemsCrafted = 0; //Cap 4, 4 items can be crafted per 1 water level
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CrucibleBlockEntity crucible)
    {
        if (!level.isClientSide)
        {

        }
    }

    public int getWaterLevel()
    {
        return waterLevel;
    }

    public void setWaterLevel(int newLevel)
    {
        waterLevel = newLevel;
    }

    public int getItemsCrafted()
    {
        return itemsCrafted;
    }

    public void setItemsCrafted(int newItems)
    {
        itemsCrafted = newItems;
    }

    @Override
    public void load(CompoundTag compound)
    {
        super.load(compound);
        waterLevel = compound.getInt("water_level");
        itemsCrafted = compound.getInt("items_crafted");
    }

    @Override
    protected void saveAdditional(CompoundTag compound)
    {
        super.saveAdditional(compound);
        compound.putInt("water_level", waterLevel);
        compound.putInt("items_crafted", itemsCrafted);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        load(pkt.getTag());
    }

    private void markUpdated() {
        super.setChanged();
        if (level != null)
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }
}
