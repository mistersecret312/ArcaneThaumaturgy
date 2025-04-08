package com.mistersecret312.thaumaturgy.block_entities;

import com.mistersecret312.thaumaturgy.containers.ArcaneWorkbenchCraftingContainer;
import com.mistersecret312.thaumaturgy.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Optional;

public class ArcaneWorkbenchBlockEntity extends BlockEntity
{
    public static final String STORAGE = "storage";

    private ItemStackHandler input = createHandler(9);
    private ItemStackHandler output = createHandler(1);
    private ItemStackHandler wand = createOutput();

    public ArcaneWorkbenchBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(BlockEntityInit.ARCANE_WORKBENCH.get(), pPos, pBlockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ArcaneWorkbenchBlockEntity workbench)
    {
        if (!level.isClientSide())
        {
            ArcaneWorkbenchCraftingContainer container = new ArcaneWorkbenchCraftingContainer(workbench.input);
            Optional<CraftingRecipe> recipe = level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, container, level);

            recipe.ifPresentOrElse(rec ->
                    workbench.getOutputHandler().setStackInSlot(0, rec.assemble(container, level.registryAccess()).copy()),
                    () -> workbench.getOutputHandler().setStackInSlot(0, ItemStack.EMPTY));
        }
    }

    public ItemStackHandler createHandler(int size)
    {
        return new ItemStackHandler(size)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                markUpdated();
            }
        };
    }

    public ItemStackHandler createOutput()
    {
        return new ItemStackHandler(1)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                if(this.getStackInSlot(slot).isEmpty())
                {
                    //System.out.println("Crafted out");
                    //doRecipeAfterstuff();
                }

            }
        };
    }

    public void doRecipeAfterstuff()
    {
        for (int i = 0; i < this.getInput().getSlots(); i++)
        {
            this.getInput().extractItem(i, 1, false);
        }
    }

    public void markUpdated()
    {
        super.setChanged();
        if(level != null)
            level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
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

    public ItemStackHandler getInput()
    {
        return input;
    }

    public ItemStackHandler getWandHandler()
    {
        return wand;
    }

    public void setWand(ItemStack stack)
    {
        wand.setStackInSlot(0, stack);
    }

    public ItemStackHandler getOutputHandler()
    {
        return output;
    }

    public void setOutput(ItemStack stack)
    {
        this.output.setStackInSlot(0, stack);
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        this.input.deserializeNBT(tag.getCompound(STORAGE));
        this.output.deserializeNBT(tag.getCompound("output"));
        this.wand.deserializeNBT(tag.getCompound("wand"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.put(STORAGE, this.input.serializeNBT());
        tag.put("output", this.output.serializeNBT());
        tag.put("wand", this.wand.serializeNBT());
    }
}
