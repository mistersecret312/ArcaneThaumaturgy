package com.mistersecret312.thaumaturgy.client.renderer;

import com.mistersecret312.thaumaturgy.block_entities.PedestalBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.Level;

public class PedestalRenderer implements BlockEntityRenderer<PedestalBlockEntity>
{

    public PedestalRenderer(BlockEntityRendererProvider.Context context)
    {

    }

    @Override
    public void render(PedestalBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int pPackedOverlay)
    {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        Level level = blockEntity.getLevel();

        poseStack.pushPose();
        float time = (level.getGameTime() + partialTick) * 0.8f;
        float yOffset = Mth.sin(time * 0.075f) * 0.1f + 0.25f;

        poseStack.translate(0.5, 1 + yOffset, 0.5);
        poseStack.mulPose(Axis.YP.rotation(time * 0.075f));
        poseStack.scale(1, 1, 1);

        itemRenderer.renderStatic(blockEntity.getDisplayItem(), ItemDisplayContext.GROUND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, level, 0);
        poseStack.popPose();
    }
}
