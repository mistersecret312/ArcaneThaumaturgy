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

    public ItemStack stack;

    public AspectTooltipComponent(ItemStack stack)
    {
        this.stack = stack;
    }

    @Override
    public void renderImage(Font pFont, int pX, int pY, GuiGraphics pGuiGraphics)
    {
        final PoseStack pose = pGuiGraphics.pose();

        if(stack.getItem() instanceof AspectDisplayTest item)
        {
            Minecraft minecraft = Minecraft.getInstance();
            ClientPacketListener clientPacketListener = minecraft.getConnection();
            RegistryAccess registries = clientPacketListener.registryAccess();
            Registry<Aspect> aspectsRegistry = registries.registryOrThrow(Aspect.REGISTRY_KEY);

            Aspect aspect = aspectsRegistry.get(item.getAspect(stack));
            if(aspect != null)
            {
                //pose.pushPose();
                //pGuiGraphics.blit(aspect.getTexture(), pX, pY, 0, 0, 18, 18, 18, 18);
                //pose.popPose();
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
