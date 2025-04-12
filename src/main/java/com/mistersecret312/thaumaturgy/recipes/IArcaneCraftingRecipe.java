package com.mistersecret312.thaumaturgy.recipes;

import com.mistersecret312.thaumaturgy.aspects.AspectStack;
import com.mistersecret312.thaumaturgy.containers.ArcaneWorkbenchCraftingContainer;
import com.mistersecret312.thaumaturgy.init.RecipeTypeInit;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;

public interface IArcaneCraftingRecipe extends Recipe<ArcaneWorkbenchCraftingContainer>
{
    @Override
    default RecipeType<?> getType()
    {
        return RecipeTypeInit.Types.ARCANE_CRAFTING.get();
    }

    List<AspectStack> getAspects();

    CraftingBookCategory category();
}
