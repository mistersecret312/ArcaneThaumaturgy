package com.mistersecret312.thaumaturgy.tooltipcomponents;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.aspects.AspectStack;
import com.mistersecret312.thaumaturgy.aspects.Aspect;
import com.mistersecret312.thaumaturgy.datapack.AspectCompound;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public class AspectTooltipComponent implements ClientTooltipComponent, TooltipComponent
{
    public AspectCompound aspectCompound;

    public AspectTooltipComponent(AspectCompound composition)
    {
        this.aspectCompound = composition;
    }

    @Override
    public void renderImage(Font pFont, int pX, int pY, GuiGraphics pGuiGraphics)
    {
        final PoseStack pose = pGuiGraphics.pose();

        Minecraft minecraft = Minecraft.getInstance();
        for (int i = 0; i < aspectCompound.getAspects().size(); i++)
        {
            AspectStack stack = aspectCompound.getAspects().get(i);
            Aspect aspect = stack.getAspect();
            if(aspect != null)
            {
                ResourceLocation texture = aspect.getTexture();
                if(minecraft.getResourceManager().getResource(texture).isEmpty())
                    texture = ResourceLocation.fromNamespaceAndPath(ArcaneThaumaturgyMod.MODID, "textures/aspect/error.png");

                pose.pushPose();
                pGuiGraphics.blit(texture, pX+(22*i), pY, 0, 0, 18, 18, 18, 18);
                pose.scale(0.5f, 0.5f, 0.5f);
                pFont.drawInBatch(String.valueOf(stack.getAmount()), 2*(pX+(22*i)+15), 2*(pY+14), -1, true, pose.last().pose(), pGuiGraphics.bufferSource(), Font.DisplayMode.NORMAL, 0, 15728880);
                pose.popPose();
            }
        }
    }

    @Override
    public int getHeight()
    {
        return 18;
    }

    @Override
    public int getWidth(Font pFont)
    {
        return 18;
    }
}
