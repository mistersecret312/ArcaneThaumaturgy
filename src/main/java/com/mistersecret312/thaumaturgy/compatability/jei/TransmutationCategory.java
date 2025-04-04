package com.mistersecret312.thaumaturgy.compatability.jei;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.aspects.Aspect;
import com.mistersecret312.thaumaturgy.aspects.AspectStack;
import com.mistersecret312.thaumaturgy.init.BlockInit;
import com.mistersecret312.thaumaturgy.recipes.TransmutationRecipe;
import com.mistersecret312.thaumaturgy.util.RenderBlitUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class TransmutationCategory implements IRecipeCategory<TransmutationRecipe>
{

    public static final ResourceLocation RECIPE_ID = new ResourceLocation(ArcaneThaumaturgyMod.MODID, "transmutation");
    public static final ResourceLocation TEXTURE = new ResourceLocation(ArcaneThaumaturgyMod.MODID, "textures/gui/jei_transmutation.png");

    public static final RecipeType<TransmutationRecipe> TRANSMUTATION_TYPE = new RecipeType<>(RECIPE_ID, TransmutationRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public TransmutationCategory(IGuiHelper helper)
    {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 85, 105);
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
        return Component.translatable("block.thaumaturgy.crucible");
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
        final PoseStack pose = guiGraphics.pose();
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        for (int i = 0; i < recipe.aspects.size(); i++)
        {
            AspectStack stack = recipe.aspects.get(i);
            Aspect aspect = stack.getAspect();
            if(aspect != null)
            {
                ResourceLocation texture = aspect.getTexture();
                if(Minecraft.getInstance().getResourceManager().getResource(texture).isEmpty())
                    texture = ResourceLocation.fromNamespaceAndPath(ArcaneThaumaturgyMod.MODID, "textures/aspect/error.png");

                int x = 35;
                int y = 45;

                pose.pushPose();
                RenderBlitUtil.blit(texture, pose, (float) (x+(22*i)), (float) y, 0, 0, 18, 18, 18, 18);
                pose.scale(0.5f, 0.5f, 0.5f);
                Minecraft.getInstance().font.drawInBatch(String.valueOf(stack.getAmount()), (float) (2*(x+(22*i)+15)), (float) (2*(y+14)), -1, true, pose.last().pose(), guiGraphics.bufferSource(), Font.DisplayMode.NORMAL, 0, 15728880);
                pose.popPose();
            }
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TransmutationRecipe recipe,
                          IFocusGroup group)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 9, 33).addIngredients(recipe.getCatalyst());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 53, 19).addItemStack(recipe.getResult());
    }
}
