package com.mistersecret312.thaumaturgy.compatability.jei;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.aspects.Aspect;
import com.mistersecret312.thaumaturgy.aspects.AspectStack;
import com.mistersecret312.thaumaturgy.datapack.AspectComposition;
import com.mistersecret312.thaumaturgy.init.AspectInit;
import com.mistersecret312.thaumaturgy.init.BlockInit;
import com.mistersecret312.thaumaturgy.init.ItemInit;
import com.mistersecret312.thaumaturgy.init.RecipeTypeInit;
import com.mistersecret312.thaumaturgy.items.AspectItem;
import com.mistersecret312.thaumaturgy.recipes.CompositionRecipe;
import com.mistersecret312.thaumaturgy.recipes.TransmutationRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.AirItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.RecipeManager;
import org.checkerframework.checker.units.qual.C;

import java.util.*;

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
        registration.addRecipeCategories(new CompositionCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration)
    {
        IIngredientSubtypeInterpreter<ItemStack> aspects =
                (stack, context) -> AspectItem.getAspect(stack).toLanguageKey();

        registration.registerSubtypeInterpreter(ItemInit.ASPECT.get(), aspects);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration)
    {
        RecipeManager recipeManager = Objects.requireNonNull(minecraft.level).getRecipeManager();

        List<TransmutationRecipe> transmutationRecipes = recipeManager.getAllRecipesFor(TransmutationRecipe.Type.INSTANCE);
        registration.addRecipes(new RecipeType<>(TransmutationCategory.RECIPE_ID, TransmutationRecipe.class), transmutationRecipes);

        List<CompositionRecipe> compositionRecipes = new ArrayList<>();
        AspectInit.ASPECTS.getEntries().forEach(entry -> {
            Aspect aspect = entry.get();
            List<AspectComposition> compositions = new ArrayList<>();
            minecraft.getConnection().registryAccess().registryOrThrow(AspectComposition.REGISTRY_KEY).entrySet().forEach(compositionEntry -> {
                if(compositionEntry.getValue().getItem() != null && compositionEntry.getValue().getAspects().stream().anyMatch(stack -> stack.getAspect().equals(aspect)))
                    compositions.add(compositionEntry.getValue());
            });

            compositions.sort(Comparator.comparing(
                    composition -> composition.getAspects().stream().filter(filter ->
                            filter.getAspect().equals(aspect)).toList().get(0).getAmount()));

            List<Item> items = new ArrayList<>();
            Collections.reverse(compositions);
            compositions.forEach(composition ->
            {
                if(!(composition.getItem() instanceof AirItem))
                    items.add(composition.getItem());
            });

            compositionRecipes.add(new CompositionRecipe(aspect, items));
        });
        registration.addRecipes(new RecipeType<>(CompositionCategory.RECIPE_ID, CompositionRecipe.class), compositionRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        BlockInit.CRUCIBLE.ifPresent(crucible -> {
            Item item = crucible.asItem();
            if(item != null)
                registration.addRecipeCatalyst(new ItemStack(item), TransmutationCategory.TRANSMUTATION_TYPE);
        });

        BlockInit.ARCANE_WORKBENCH.ifPresent(workbench -> {
            Item item = workbench.asItem();
            if(item != null)
                registration.addRecipeCatalyst(new ItemStack(item), RecipeTypes.CRAFTING);
        });
    }
}
