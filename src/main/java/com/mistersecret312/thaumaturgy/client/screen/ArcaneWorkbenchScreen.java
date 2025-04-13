package com.mistersecret312.thaumaturgy.client.screen;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.aspects.Aspect;
import com.mistersecret312.thaumaturgy.aspects.AspectStack;
import com.mistersecret312.thaumaturgy.aspects.DefinedAspectStackHandler;
import com.mistersecret312.thaumaturgy.containers.ArcaneWorkbenchCraftingContainer;
import com.mistersecret312.thaumaturgy.init.AspectInit;
import com.mistersecret312.thaumaturgy.init.RecipeTypeInit;
import com.mistersecret312.thaumaturgy.items.WandItem;
import com.mistersecret312.thaumaturgy.menu.ArcaneWorkbenchMenu;
import com.mistersecret312.thaumaturgy.recipes.IArcaneCraftingRecipe;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.joml.Matrix4f;

import java.util.List;
import java.util.Optional;

public class ArcaneWorkbenchScreen extends AbstractContainerScreen<ArcaneWorkbenchMenu>
{
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ArcaneThaumaturgyMod.MODID, "textures/gui/arcane_crafting_table_gui.png");

    public ArcaneWorkbenchScreen(ArcaneWorkbenchMenu pMenu, Inventory pPlayerInventory, Component pTitle)
    {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageHeight = 256;
        this.imageWidth = 220;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float pPartialTick, int pMouseX, int pMouseY)
    {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        graphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        renderBackground(graphics);
        final PoseStack poseStack = graphics.pose();
        super.render(graphics, mouseX, mouseY, partialTick);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        ItemStack wand = this.menu.blockEntity.getWandHandler().getStackInSlot(0);
        Optional<IArcaneCraftingRecipe> recipe = this.menu.level.getRecipeManager().getRecipeFor(RecipeTypeInit.Types.ARCANE_CRAFTING.get(), new ArcaneWorkbenchCraftingContainer(this.menu.blockEntity.getInput(), this.menu.blockEntity.getWandHandler()), this.menu.level);
        if (recipe.isPresent())
        {
            if (!wand.isEmpty() && wand.getItem() instanceof WandItem wandItem)
            {
                DefinedAspectStackHandler handler = wandItem.getAspects(wand);
                List<Aspect> primal = List.of(AspectInit.AER.get(), AspectInit.TERRA.get(), AspectInit.IGNIS.get(), AspectInit.AQUA.get(), AspectInit.ORDO.get(), AspectInit.PERDITIO.get());

                List<AspectStack> stacks = recipe.get().getAspects();

                AspectStack aer = stacks.stream().filter(aspectStack -> aspectStack.getAspect().equals(primal.get(0))).findFirst().orElse(AspectStack.EMPTY);
                AspectStack wandAer = handler.getStackInSlot(primal.get(0));
                if(!aer.isEmpty() && wandAer.getAmount() >= aer.getAmount())
                {
                    //Aer
                    poseStack.pushPose();
                    poseStack.translate(88, 10, 0);
                    graphics.blit(primal.get(0).getTexture(), x + (22 * 0), y, 0, 0, 18, 18, 18, 18);
                    poseStack.scale(0.5f, 0.5f, 0.5f);
                    Minecraft.getInstance().font.drawInBatch(String.valueOf(aer.getAmount()), 2 * (x + (22 * 0) + 15), 2 * (y + 14), -1, true, poseStack.last().pose(), graphics.bufferSource(), Font.DisplayMode.NORMAL, 0, 15728880);
                    poseStack.popPose();
                }

                AspectStack perditio = stacks.stream().filter(aspectStack -> aspectStack.getAspect().equals(primal.get(1))).findFirst().orElse(AspectStack.EMPTY);
                AspectStack wandPerditio = handler.getStackInSlot(primal.get(0));
                if(!perditio.isEmpty() && wandPerditio.getAmount() >= perditio.getAmount())
                {
                    //Perditio
                    poseStack.pushPose();
                    poseStack.translate(109, 34, 0);
                    graphics.blit(primal.get(1).getTexture(), x + (22 * 1), y, 0, 0, 18, 18, 18, 18);
                    poseStack.scale(0.5f, 0.5f, 0.5f);
                    Minecraft.getInstance().font.drawInBatch(String.valueOf(perditio.getAmount()), 2 * (x + (22 * 1) + 15), 2 * (y + 14), -1, true, poseStack.last().pose(), graphics.bufferSource(), Font.DisplayMode.NORMAL, 0, 15728880);
                    poseStack.popPose();
                }

                AspectStack ordo = stacks.stream().filter(aspectStack -> aspectStack.getAspect().equals(primal.get(2))).findFirst().orElse(AspectStack.EMPTY);
                AspectStack wandOrdo = handler.getStackInSlot(primal.get(0));
                if(!ordo.isEmpty() && wandOrdo.getAmount() >= ordo.getAmount())
                {
                    //Ordo
                    poseStack.pushPose();
                    poseStack.translate(87, 84, 0);
                    graphics.blit(primal.get(2).getTexture(), x + (22 * 2), y, 0, 0, 18, 18, 18, 18);
                    poseStack.scale(0.5f, 0.5f, 0.5f);
                    Minecraft.getInstance().font.drawInBatch(String.valueOf(ordo.getAmount()), 2 * (x + (22 * 2) + 15), 2 * (y + 14), -1, true, poseStack.last().pose(), graphics.bufferSource(), Font.DisplayMode.NORMAL, 0, 15728880);
                    poseStack.popPose();
                }

                AspectStack aqua = stacks.stream().filter(aspectStack -> aspectStack.getAspect().equals(primal.get(3))).findFirst().orElse(AspectStack.EMPTY);
                AspectStack wandAqua = handler.getStackInSlot(primal.get(0));
                if(!aqua.isEmpty() && wandAqua.getAmount() >= aqua.getAmount())
                {
                    //Aqua
                    poseStack.pushPose();
                    poseStack.translate(22, 108, 0);
                    graphics.blit(primal.get(3).getTexture(), x + (22 * 3), y, 0, 0, 18, 18, 18, 18);
                    poseStack.scale(0.5f, 0.5f, 0.5f);
                    Minecraft.getInstance().font.drawInBatch(String.valueOf(handler.getStackInSlot(primal.get(3)).getAmount()), 2 * (x + (22 * 3) + 15), 2 * (y + 14), -1, true, poseStack.last().pose(), graphics.bufferSource(), Font.DisplayMode.NORMAL, 0, 15728880);
                    poseStack.popPose();
                }

                AspectStack ignis = stacks.stream().filter(aspectStack -> aspectStack.getAspect().equals(primal.get(4))).findFirst().orElse(AspectStack.EMPTY);
                AspectStack wandIgnis = handler.getStackInSlot(primal.get(0));
                if(!ignis.isEmpty() && wandIgnis.getAmount() >= ignis.getAmount())
                {
                    //Ignis
                    poseStack.pushPose();
                    poseStack.translate(-43, 84, 0);
                    graphics.blit(primal.get(4).getTexture(), x + (22 * 4), y, 0, 0, 18, 18, 18, 18);
                    poseStack.scale(0.5f, 0.5f, 0.5f);
                    Minecraft.getInstance().font.drawInBatch(String.valueOf(ignis.getAmount()), 2 * (x + (22 * 4) + 15), 2 * (y + 14), -1, true, poseStack.last().pose(), graphics.bufferSource(), Font.DisplayMode.NORMAL, 0, 15728880);
                    poseStack.popPose();
                }

                AspectStack terra = stacks.stream().filter(aspectStack -> aspectStack.getAspect().equals(primal.get(5))).findFirst().orElse(AspectStack.EMPTY);
                AspectStack wandTerra = handler.getStackInSlot(primal.get(0));
                if(!terra.isEmpty() && wandTerra.getAmount() >= terra.getAmount())
                {
                    //Terra
                    poseStack.pushPose();
                    poseStack.translate(-65, 34, 0);
                    graphics.blit(primal.get(5).getTexture(), x + (22 * 5), y, 0, 0, 18, 18, 18, 18);
                    poseStack.scale(0.5f, 0.5f, 0.5f);
                    Minecraft.getInstance().font.drawInBatch(String.valueOf(handler.getStackInSlot(primal.get(5)).getAmount()), 2 * (x + (22 * 5) + 15), 2 * (y + 14), -1, true, poseStack.last().pose(), graphics.bufferSource(), Font.DisplayMode.NORMAL, 0, 15728880);
                    poseStack.popPose();
                }
            }
        }
        renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY)
    {
        //graphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
        //this.font.draw(stack, this.playerInventoryTitle, (float)this.inventoryLabelX, (float)this.inventoryLabelY, 4210752);
    }
}
