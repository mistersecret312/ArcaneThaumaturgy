package com.mistersecret312.thaumaturgy.client.gui;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.items.WandItem;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.joml.Quaternionf;

public class WandAspectOverlay
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(ArcaneThaumaturgyMod.MODID, "textures/gui/wand_gui.png");

    public static final IGuiOverlay OVERLAY = ((gui, guiGraphics, partialTick, screenWidth, screenHeight)
    -> {
        final PoseStack pose = guiGraphics.pose();
        int x = screenWidth/2;
        int y = screenHeight/2;
        LocalPlayer player = Minecraft.getInstance().player;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);

        if(player != null)
        {
            boolean mainHand = player.getMainHandItem().is(ArcaneThaumaturgyMod.WANDS);
            boolean offHand = player.getOffhandItem().is(ArcaneThaumaturgyMod.WANDS);
            if (mainHand || offHand)
            {
                ItemStack stack = null;
                if(mainHand)
                    stack = player.getMainHandItem();
                else stack = player.getOffhandItem();

                if(stack != null)
                {
                    WandItem item = (WandItem) stack.getItem();
                    int cap = item.getCapacity(stack);
                    int[] aspects = item.getAspects(stack);
                    double[] percentages = new double[6];

                    for (int i = 0; i < aspects.length; i++)
                    {
                        percentages[i] = (double) aspects[i]/cap;
                    }

                    pose.pushPose();
                    //pose.scale(0.5f, 0.5f, 0.5f);
                    guiGraphics.blit(TEXTURE, x - 285, y - 145, 0, 0, 36, 36);

                    //Perditio
                    pose.pushPose();
                    pose.mulPose(Axis.ZP.rotationDegrees(15));
                    guiGraphics.blit(TEXTURE, x - 271 - 5, y - 95 - 13, 92, 0, 8, ((int) (48 * percentages[0])));
                    guiGraphics.blit(TEXTURE, x - 275 - 5, y - 105 - 13, 36, 0, 16, 68);
                    pose.popPose();

                    //Ordo
                    pose.pushPose();
                    //pose.mulPose(Axis.ZN.rotationDegrees(5));
                    guiGraphics.blit(TEXTURE, x - 271 , y - 95, 84, 0, 8, ((int) (48 * percentages[1])));
                    guiGraphics.blit(TEXTURE, x - 275 , y - 105, 36, 0, 16, 68);
                    pose.popPose();

                    //Aqua
                    pose.pushPose();
                    pose.mulPose(Axis.ZN.rotationDegrees(15));
                    guiGraphics.blit(TEXTURE, x - 271 + 3, y - 95 + 15, 60, 0, 8, ((int) (48 * percentages[2])));
                    guiGraphics.blit(TEXTURE, x - 275 + 3, y - 105 + 15, 36, 0, 16, 68);
                    pose.popPose();

                    //Ignis
                    pose.pushPose();
                    pose.mulPose(Axis.ZN.rotationDegrees(35));
                    guiGraphics.blit(TEXTURE, x - 271 - 5 , y - 95 + 34, 68, 0, 8, ((int) (48 * percentages[3])));
                    guiGraphics.blit(TEXTURE, x - 275 - 5, y - 105 + 34, 36, 0, 16, 68);
                    pose.popPose();

                    //Terra
                    pose.pushPose();
                    pose.mulPose(Axis.ZN.rotationDegrees(50));
                    guiGraphics.blit(TEXTURE, x - 271 - 5, y - 95 + 34, 52, 0, 8, ((int) (48 * percentages[4])));
                    guiGraphics.blit(TEXTURE, x - 275 - 5, y - 105 + 34, 36, 0, 16, 68);
                    pose.popPose();

                    //Aer
                    pose.pushPose();
                    pose.mulPose(Axis.ZN.rotationDegrees(65));
                    guiGraphics.blit(TEXTURE, x - 271 - 5, y - 95 + 45, 76, 0, 8, ((int) (48 * percentages[5])));
                    guiGraphics.blit(TEXTURE, x - 275 - 5, y - 105 + 45, 36, 0, 16, 68);
                    pose.popPose();

                    pose.popPose();

                }


            }
        }
    });
}
