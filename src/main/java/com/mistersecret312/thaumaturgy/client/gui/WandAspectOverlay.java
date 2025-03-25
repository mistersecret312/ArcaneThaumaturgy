package com.mistersecret312.thaumaturgy.client.gui;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.items.WandItem;
import com.mistersecret312.thaumaturgy.util.RenderBlitUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.joml.Quaternionf;

public class WandAspectOverlay
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(ArcaneThaumaturgyMod.MODID, "textures/gui/wand_gui.png");

    public static final IGuiOverlay OVERLAY = ((gui, guiGraphics, partialTick, screenWidth, screenHeight)
    -> {
        float scale = (float) Minecraft.getInstance().getWindow().getGuiScale();
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();
        boolean hasWand = main.is(ArcaneThaumaturgyMod.WANDS) || off.is(ArcaneThaumaturgyMod.WANDS);
        if (!hasWand) return;

        ItemStack wandStack = main.is(ArcaneThaumaturgyMod.WANDS) ? main : off;
        WandItem wand = (WandItem) wandStack.getItem();

        int cap = wand.getCapacity(wandStack);
        int[] aspects = wand.getAspects(wandStack);
        if (cap == 0 || aspects.length == 0) return;

        double[] percentages = new double[aspects.length];
        for (int i = 0; i < aspects.length; i++) {
            percentages[i] = (double) aspects[i] / cap;
        }

        PoseStack pose = guiGraphics.pose();
        float centerX = 32;
        float centerY = (34 + 34 * (1/scale));

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);

        pose.pushPose();
        pose.scale(0.5f, 0.5f, 0.5f);

        RenderBlitUtil.blit(TEXTURE, pose, centerX - 18, centerY - 18, 0, 0, 36, 36);


        float angleIncrement = 20f;
        float[] angles = new float[aspects.length];


        for (int i = 0; i < aspects.length; i++) {
            angles[i] = (i - (aspects.length / 2)) * angleIncrement;
        }

        float[] textureX = { 104, 94, 64, 74, 54, 84 };
        float pivotOffsetY = -3 * (1/scale);

        for (int i = 0; i < aspects.length; i++) {
            float angleDeg = angles[i] +234;

            float barHeight = 48f * (float) percentages[i];

            pose.pushPose();
            pose.translate(centerX, (centerY + pivotOffsetY), 0);
            pose.mulPose(Axis.ZN.rotationDegrees(angleDeg));
            pose.translate(0, -40, 0);
            //guiGraphics.fill(-4, -48, -4+8, (int) (-48+(barHeight)), 209);
            float cutHeight = (float) (48 * percentages[i]);  // Calculate the cut height based on the percentage
            RenderBlitUtil.blit(TEXTURE, pose,-4, -48 + (48 - cutHeight), textureX[i], 0, 8, cutHeight);
            RenderBlitUtil.blit(TEXTURE, pose, -8, -58, 36, 0, 16, 68);
            pose.popPose();
        }

        pose.popPose();
    });
}
