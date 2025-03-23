package com.mistersecret312.thaumaturgy.tooltipcomponents;

import com.mistersecret312.thaumaturgy.datapack.Aspect;
import com.mistersecret312.thaumaturgy.items.AspectDisplayTest;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

public class AspectTooltipComponent implements ClientTooltipComponent, TooltipComponent
{

    public Aspect aspect;

    public AspectTooltipComponent(Aspect aspect)
    {
        this.aspect = aspect;
    }

    @Override
    public void renderImage(Font pFont, int pX, int pY, GuiGraphics pGuiGraphics)
    {
        final PoseStack pose = pGuiGraphics.pose();

        if (aspect != null)
        {
            pose.pushPose();
            pGuiGraphics.blit(aspect.getTexture(), pX, pY, 0, 0, 18, 18, 18, 18);
            pFont.drawInBatch("15", pX + 8, pY + 10, -1, true, pose.last().pose(), pGuiGraphics.bufferSource(), Font.DisplayMode.NORMAL, 0, 15728880);
            pose.popPose();
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
