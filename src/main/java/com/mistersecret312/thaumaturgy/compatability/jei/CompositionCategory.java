package com.mistersecret312.thaumaturgy.compatability.jei;

import com.klikli_dev.modonomicon.platform.Services;
import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.aspects.Aspect;
import com.mistersecret312.thaumaturgy.aspects.AspectStack;
import com.mistersecret312.thaumaturgy.datapack.AspectComposition;
import com.mistersecret312.thaumaturgy.init.AspectInit;
import com.mistersecret312.thaumaturgy.init.BlockInit;
import com.mistersecret312.thaumaturgy.init.ItemInit;
import com.mistersecret312.thaumaturgy.items.AspectItem;
import com.mistersecret312.thaumaturgy.recipes.CompositionRecipe;
import com.mistersecret312.thaumaturgy.recipes.TransmutationRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotDrawablesView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.placement.VerticalAlignment;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.gui.widgets.IScrollGridWidget;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CompositionCategory implements IRecipeCategory<CompositionRecipe>
{

    public static final ResourceLocation RECIPE_ID = new ResourceLocation(ArcaneThaumaturgyMod.MODID, "aspect");

    public static final RecipeType<CompositionRecipe> COMPOSITION = new RecipeType<>(RECIPE_ID, CompositionRecipe.class);

    private final IDrawable icon;
    private final IDrawable aspectBackground;

    public CompositionCategory(IGuiHelper helper)
    {
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, AspectItem.create(AspectInit.ORDO.get()));
        this.aspectBackground = helper.drawableBuilder(ResourceLocation.fromNamespaceAndPath(ArcaneThaumaturgyMod.MODID, "textures/gui/jei_composition.png"), 0, 0, 16, 16).setTextureSize(16, 16).build();
    }

    @Override
    public int getWidth()
    {
        return 142;
    }

    @Override
    public int getHeight()
    {
        return 110;
    }

    @Override
    public RecipeType<CompositionRecipe> getRecipeType()
    {
        return COMPOSITION;
    }

    @Override
    public Component getTitle()
    {
        return Component.translatable("jei.thaumaturgy.composition");
    }

    @Override
    public @Nullable IDrawable getBackground()
    {
        return null;
    }

    @Override
    public @Nullable IDrawable getIcon()
    {
        return this.icon;
    }

    @Override
    public void draw(CompositionRecipe composition, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics,
                     double mouseX, double mouseY)
    {

    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CompositionRecipe recipe, IFocusGroup group)
    {
        if(recipe.getAspect() != null && !recipe.getItems().isEmpty())
        {
            builder.addOutputSlot().addItemStack(AspectItem.create(recipe.getAspect()));

            recipe.getItems().forEach(item -> builder.addInputSlot().addItemStack(new ItemStack(item)));
        }
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, CompositionRecipe recipe, IFocusGroup focuses)
    {

        FormattedText text = new AspectStack(recipe.getAspect()).getTranslatable();
        builder.addText(text, getWidth() - 22, 20)
                .setPosition(10, 0)
                .setColor(0xFF505050)
                .setLineSpacing(0)
                .setShadow(true)
                .setTextAlignment(VerticalAlignment.CENTER)
                .setTextAlignment(HorizontalAlignment.CENTER);

        IRecipeSlotDrawablesView recipeSlots = builder.getRecipeSlots();
        List<IRecipeSlotDrawable> outputSlots = recipeSlots.getSlots(RecipeIngredientRole.INPUT);

        IScrollGridWidget scrollGridWidget = builder.addScrollGridWidget(outputSlots, 7, 5);
        scrollGridWidget.setPosition(0, 0, getWidth(), getHeight(), HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM);

        builder.addDrawable(aspectBackground).setPosition(scrollGridWidget.getScreenRectangle().position().x()+1, 1);
        IRecipeSlotDrawable inputSlot = recipeSlots.getSlots(RecipeIngredientRole.OUTPUT)
                .get(0);
        inputSlot.setPosition(scrollGridWidget.getScreenRectangle().position().x() + 1, 1);
    }
}



