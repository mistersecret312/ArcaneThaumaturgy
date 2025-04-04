package com.mistersecret312.thaumaturgy.compatability.jei;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.init.BlockInit;
import com.mistersecret312.thaumaturgy.recipes.TransmutationRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class ArcaneThaumaturgyJEIPlugin implements IModPlugin
{
    private static final ResourceLocation PLUGIN_LOCATION = new ResourceLocation(ArcaneThaumaturgyMod.MODID, "jei_plugin");

    private static Minecraft minecraft = Minecraft.getInstance();

    @Override
    public ResourceLocation getPluginUid()
    {
        return PLUGIN_LOCATION;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration)
    {
        registration.addRecipeCategories(new TransmutationCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration)
    {
        RecipeManager recipeManager = Objects.requireNonNull(minecraft.level).getRecipeManager();

        List<TransmutationRecipe> transmutationRecipes = recipeManager.getAllRecipesFor(TransmutationRecipe.Type.INSTANCE);
        registration.addRecipes(new RecipeType<>(TransmutationCategory.RECIPE_ID, TransmutationRecipe.class), transmutationRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        BlockInit.CRUCIBLE.ifPresent(crucible -> {
            Item item = crucible.asItem();
            if(item != null)
                registration.addRecipeCatalyst(new ItemStack(item), TransmutationCategory.TRANSMUTATION_TYPE);
        });
    }
}
