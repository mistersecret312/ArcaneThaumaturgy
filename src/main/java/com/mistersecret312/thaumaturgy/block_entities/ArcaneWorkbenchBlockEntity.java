package com.mistersecret312.thaumaturgy.block_entities;

import com.mistersecret312.thaumaturgy.aspects.DefinedAspectStackHandler;
import com.mistersecret312.thaumaturgy.containers.ArcaneWorkbenchCraftingContainer;
import com.mistersecret312.thaumaturgy.init.BlockEntityInit;
import com.mistersecret312.thaumaturgy.init.RecipeTypeInit;
import com.mistersecret312.thaumaturgy.items.WandItem;
import com.mistersecret312.thaumaturgy.recipes.ArcaneCraftingShapedRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;
import java.util.Optional;

public class ArcaneWorkbenchBlockEntity extends BlockEntity
{
    public static final String STORAGE = "storage";

    private ItemStackHandler input = createHandler(9);
    private ItemStackHandler output = createHandler(1);
    private ItemStackHandler wand = createHandler(1);

    public ArcaneWorkbenchBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(BlockEntityInit.ARCANE_WORKBENCH.get(), pPos, pBlockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ArcaneWorkbenchBlockEntity workbench)
    {
        if (!level.isClientSide())
        {
            ArcaneWorkbenchCraftingContainer container = new ArcaneWorkbenchCraftingContainer(workbench.input, workbench.wand);

            Optional<ArcaneCraftingShapedRecipe> arcaneShapedRecipe = level.getRecipeManager().getRecipeFor(ArcaneCraftingShapedRecipe.Type.INSTANCE, container, level);
            arcaneShapedRecipe.ifPresentOrElse(rec ->
            {
                workbench.getOutputHandler().setStackInSlot(0, rec.assemble(container, level.registryAccess()).copy());
                return;
            }, () -> {
                workbench.getOutputHandler().setStackInSlot(0, ItemStack.EMPTY);
                return;
            });

            Optional<CraftingRecipe> recipe = level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, container, level);
            recipe.ifPresentOrElse(rec -> workbench.getOutputHandler().setStackInSlot(0, rec.assemble(container, level.registryAccess()).copy()), () -> workbench.getOutputHandler().setStackInSlot(0, ItemStack.EMPTY));

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

    public void doRecipeAfterstuff(Player player)
    {
        ArcaneWorkbenchCraftingContainer container = new ArcaneWorkbenchCraftingContainer(this.getInput(), this.getWandHandler());
        List<ItemStack> remaining = this.level.getRecipeManager().getRemainingItemsFor(RecipeType.CRAFTING, container, level);

        for (int i = 0; i < remaining.size(); i++)
        {
            ItemStack stack = this.getInput().getStackInSlot(i).copy();
            ItemStack stack1 = remaining.get(i);
            if (!stack.isEmpty())
            {
                this.getInput().extractItem(i, 1, false);
                stack = this.getInput().getStackInSlot(i).copy();
            }

            if (!stack1.isEmpty())
            {
                if (stack.isEmpty()) this.getInput().setStackInSlot(i, stack1);
                else if (ItemStack.isSameItemSameTags(stack, stack1))
                {
                    stack1.grow(stack.getCount());
                    this.getInput().setStackInSlot(i, stack1);
                } else if (!player.getInventory().add(stack1))
                {
                    player.drop(stack1, false);
                }
            }
        }
    }

    public void doMagicRecipeStuff(Player player, ArcaneCraftingShapedRecipe recipe)
    {
        ArcaneWorkbenchCraftingContainer container = new ArcaneWorkbenchCraftingContainer(this.getInput(), this.getWandHandler());
        List<ItemStack> remaining = this.level.getRecipeManager().getRemainingItemsFor(ArcaneCraftingShapedRecipe.Type.INSTANCE, container, level);

        ItemStack wandStack = this.getWandHandler().getStackInSlot(0);
        if(!wandStack.isEmpty() && wandStack.getItem() instanceof WandItem wandItem)
        {
            recipe.getAspects().forEach(aspectStack ->
            {
                wandItem.getAspects(wandStack).extractAspect(aspectStack.getAspect(), aspectStack.getAmount(), false);
            });
        }
        for (int i = 0; i < remaining.size(); i++)
        {
            ItemStack stack = this.getInput().getStackInSlot(i).copy();
            ItemStack stack1 = remaining.get(i);
            if (!stack.isEmpty())
            {
                this.getInput().extractItem(i, 1, false);
                stack = this.getInput().getStackInSlot(i).copy();
            }

            if (!stack1.isEmpty())
            {
                if (stack.isEmpty()) this.getInput().setStackInSlot(i, stack1);
                else if (ItemStack.isSameItemSameTags(stack, stack1))
                {
                    stack1.grow(stack.getCount());
                    this.getInput().setStackInSlot(i, stack1);
                } else if (!player.getInventory().add(stack1))
                {
                    player.drop(stack1, false);
                }
            }
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
