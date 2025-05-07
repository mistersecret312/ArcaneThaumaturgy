package com.mistersecret312.thaumaturgy.containers;

import com.mistersecret312.thaumaturgy.aspects.Aspect;
import com.mistersecret312.thaumaturgy.aspects.AspectStack;
import com.mistersecret312.thaumaturgy.aspects.DefinedAspectStackHandler;
import com.mistersecret312.thaumaturgy.init.AspectInit;
import com.mistersecret312.thaumaturgy.items.WandItem;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public class ArcaneWorkbenchCraftingContainer implements CraftingContainer
{
    public List<ItemStack> items;
    public List<AspectStack> stacks;

    public ArcaneWorkbenchCraftingContainer(ItemStackHandler items, ItemStackHandler wand)
    {
        this.items = new ArrayList<>();
        for (int i = 0; i < items.getSlots(); i++)
            this.items.add(items.getStackInSlot(i));
        this.stacks = new ArrayList<>();
        ItemStack stack = wand.getStackInSlot(0);
        if (!stack.isEmpty() && stack.getItem() instanceof WandItem wandItem)
        {
            if(wandItem.getAspects(stack) == null)
                stack = WandItem.createPrimal(wandItem, 25, false);

            List<Aspect> primal = List.of(AspectInit.ORDO.get(), AspectInit.AER.get(), AspectInit.AQUA.get(), AspectInit.IGNIS.get(), AspectInit.TERRA.get(), AspectInit.PERDITIO.get());
            for (Aspect aspect : primal)
            {
                AspectStack aspectStack = wandItem.getAspects(stack).getStackInSlot(aspect).copy();
                if(aspectStack == null)
                    aspectStack = new AspectStack(aspect, 0);
                this.stacks.add(aspectStack);
            }
        }
    }

    @Override
    public int getWidth()
    {
        return 3;
    }

    @Override
    public int getHeight()
    {
        return 3;
    }

    @Override
    public List<ItemStack> getItems()
    {
        return items;
    }

    @Override
    public int getContainerSize()
    {
        return items.size();
    }

    @Override
    public boolean isEmpty()
    {
        for(ItemStack stack : this.items)
            if(!stack.isEmpty())
                return false;

        return true;
    }

    @Override
    public ItemStack getItem(int pSlot)
    {
        return pSlot >= this.getContainerSize() ? ItemStack.EMPTY : this.items.get(pSlot);
    }

    public AspectStack getAspect(Aspect aspect)
    {
        return this.stacks.stream().filter(aspectStack -> !aspectStack.isEmpty() && aspectStack.getAspect().equals(aspect)).findFirst().orElse(AspectStack.EMPTY);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount)
    {
        return ContainerHelper.removeItem(this.items, pSlot, pAmount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot)
    {
        return ContainerHelper.takeItem(this.items, pSlot);
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack)
    {
        this.items.set(pSlot, pStack);
    }

    @Override
    public void setChanged()
    {

    }

    @Override
    public boolean stillValid(Player pPlayer)
    {
        return true;
    }

    @Override
    public void clearContent()
    {
        this.items.clear();
    }

    @Override
    public void fillStackedContents(StackedContents pContents)
    {
        for(ItemStack stack : this.items)
            pContents.accountSimpleStack(stack);
    }
}
