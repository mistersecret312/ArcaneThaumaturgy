package com.mistersecret312.thaumaturgy.compatability.jei;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.aspects.AspectStack;
import com.mistersecret312.thaumaturgy.init.BlockInit;
import com.mistersecret312.thaumaturgy.init.ItemInit;
import com.mistersecret312.thaumaturgy.items.AspectItem;
import com.mistersecret312.thaumaturgy.recipes.TransmutationRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class TransmutationCategory implements IRecipeCategory<TransmutationRecipe>
{
    public static final int ASPECTS_PER_LINE = 3;

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
    public void setRecipe(IRecipeLayoutBuilder builder, TransmutationRecipe recipe,
                          IFocusGroup group)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 4, 29).addIngredients(recipe.getCatalyst());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 47, 7).addItemStack(recipe.getResult());

        int aspectsTotal = recipe.aspects.size();
        int row = 1;
        for (int i = 0; i < aspectsTotal; i++)
        {
            AspectStack aspectStack = recipe.aspects.get(i);
            AspectItem item = (AspectItem) ItemInit.ASPECT.get();
            ItemStack stack = new ItemStack(item);
            item.setAspect(stack, aspectStack);

            int aspectSpacing = 16;
            int y = 0;
            int x = aspectSpacing*i;
            if(i >= ASPECTS_PER_LINE)
            {
                y = aspectSpacing * (i / ASPECTS_PER_LINE);
                x -= aspectSpacing*ASPECTS_PER_LINE*row;
                row++;
            }

            builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 30 + x, 60 + y).addItemStack(stack);
        }
    }


}
