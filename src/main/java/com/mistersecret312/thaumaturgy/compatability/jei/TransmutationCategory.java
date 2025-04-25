package com.mistersecret312.thaumaturgy.compatability.jei;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.aspects.AspectStack;
import com.mistersecret312.thaumaturgy.init.BlockInit;
import com.mistersecret312.thaumaturgy.init.ItemInit;
import com.mistersecret312.thaumaturgy.items.AspectItem;
import com.mistersecret312.thaumaturgy.recipes.TransmutationRecipe;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.vanilla.IJeiBrewingRecipe;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.data.tags.VanillaItemTagsProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransmutationCategory implements IRecipeCategory<TransmutationRecipe>
{
    public static final int ASPECTS_PER_LINE = 2;

    public static final ResourceLocation RECIPE_ID = new ResourceLocation(ArcaneThaumaturgyMod.MODID, "transmutation");
    public static final ResourceLocation TEXTURE = new ResourceLocation(ArcaneThaumaturgyMod.MODID, "textures/gui/jei_transmutation.png");

    public static final RecipeType<TransmutationRecipe> TRANSMUTATION_TYPE = new RecipeType<>(RECIPE_ID, TransmutationRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public TransmutationCategory(IGuiHelper helper)
    {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 110, 105);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockInit.CRUCIBLE.get()));
    }

    @Override
    public RecipeType<TransmutationRecipe> getRecipeType()
    {
        return TRANSMUTATION_TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Component.translatable("jei.thaumaturgy.transmutation");
    }

    @Override
    public @Nullable IDrawable getBackground()
    {
        return this.background;
    }

    @Override
    public @Nullable IDrawable getIcon()
    {
        return this.icon;
    }

    @Override
    public void draw(TransmutationRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics,
                     double mouseX, double mouseY)
    {

    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TransmutationRecipe recipe, IFocusGroup group)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 4, 29).addIngredients(recipe.getCatalyst());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 47, 7).addItemStack(recipe.getResult());

        HashMap<Integer, List<AspectStack>> rowList = new HashMap<>();
        for (int i = 0; i < recipe.aspects.size(); i += ASPECTS_PER_LINE)
        {
            List<AspectStack> subList = new ArrayList<>();
            for (int j = i; j < recipe.aspects.size() &&  j < ASPECTS_PER_LINE+i; j++)
            {
                subList.add(recipe.aspects.get(j));
            }

            rowList.put(i/ASPECTS_PER_LINE, subList);
        }
        rowList.forEach((row, aspects) -> {
            int x = 31-(((aspects.size()-1)*16)/2);
            int y = 60+row*16;
            for (AspectStack aspectStack : aspects)
            {
                AspectItem item = (AspectItem) ItemInit.ASPECT.get();
                ItemStack stack = new ItemStack(item);
                item.setAspect(stack, aspectStack);
                x += 16;

                builder.addSlot(RecipeIngredientRole.INPUT, x, y).addItemStack(stack);
            }
        });

    }
}



