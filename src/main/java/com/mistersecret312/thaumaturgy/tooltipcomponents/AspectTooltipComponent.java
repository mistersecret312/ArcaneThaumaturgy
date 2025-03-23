package com.mistersecret312.thaumaturgy.tooltipcomponents;

import com.mistersecret312.thaumaturgy.datapack.Aspect;
import com.mistersecret312.thaumaturgy.datapack.AspectComposition;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public class AspectTooltipComponent implements ClientTooltipComponent, TooltipComponent
{
    public AspectComposition aspectComposition;

    public AspectTooltipComponent(AspectComposition composition)
    {
        this.aspectComposition = composition;
    }

    @Override
    public void renderImage(Font pFont, int pX, int pY, GuiGraphics pGuiGraphics)
    {
        final PoseStack pose = pGuiGraphics.pose();

        Minecraft minecraft = Minecraft.getInstance();
        Registry<Aspect> registry = minecraft.getConnection().registryAccess().registryOrThrow(Aspect.REGISTRY_KEY);
        for (int i = 0; i < aspectComposition.getAspects().size(); i++)
        {
            AspectComposition.AspectStack stack = aspectComposition.getAspects().get(i);
            Aspect aspect = registry.get(stack.getAspect());
            if(aspect != null)
            {
                pose.pushPose();
                pGuiGraphics.blit(aspect.getTexture(), pX+(22*i), pY, 0, 0, 18, 18, 18, 18);
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
