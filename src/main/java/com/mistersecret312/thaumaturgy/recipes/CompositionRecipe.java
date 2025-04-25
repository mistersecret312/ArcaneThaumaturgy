package com.mistersecret312.thaumaturgy.recipes;

import com.mistersecret312.thaumaturgy.aspects.Aspect;
import com.mistersecret312.thaumaturgy.init.AspectInit;
import com.mistersecret312.thaumaturgy.init.RecipeTypeInit;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class CompositionRecipe implements Recipe<Container>
{
    public Aspect aspect;
    public List<Item> items;

    public CompositionRecipe(Aspect aspect, List<Item> items)
    {
        this.aspect = aspect;
        this.items = items;
    }

    public Aspect getAspect()
    {
        return aspect;
    }

    public List<Item> getItems()
    {
        return items;
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel)
    {
        return true;
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess)
    {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight)
    {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess)
    {
        return null;
    }

    @Override
    public ResourceLocation getId()
    {
        return AspectInit.ASPECT.get().getKey(getAspect());
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return null;
    }

    @Override
    public RecipeType<?> getType()
    {
        return RecipeTypeInit.Types.COMPOSITION.get();
    }
}
