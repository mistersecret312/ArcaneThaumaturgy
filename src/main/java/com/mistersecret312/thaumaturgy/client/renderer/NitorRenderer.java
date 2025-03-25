package com.mistersecret312.thaumaturgy.client.renderer;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.block_entities.NitorBlockEntity;
import com.mistersecret312.thaumaturgy.client.ThaumaturgyRenderTypes;
import com.mistersecret312.thaumaturgy.util.RenderBlitUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

public class NitorRenderer implements BlockEntityRenderer<NitorBlockEntity>
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(ArcaneThaumaturgyMod.MODID, "textures/block/nitor.png");

    public NitorRenderer(BlockEntityRendererProvider.Context context)
    {

    }

    @Override
    public void render(NitorBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int pPackedOverlay)
    {

        poseStack.pushPose();
        VertexConsumer consumer = buffer.getBuffer(ThaumaturgyRenderTypes.nitor(TEXTURE));
        consumer.vertex(poseStack.last().pose(), 0, 0, 0).uv(0, 0);
        consumer.vertex(poseStack.last().pose(), 0, 16, 0).uv(0, 16);
        consumer.vertex(poseStack.last().pose(), 16, 16, 0).uv(16, 16);
        consumer.vertex(poseStack.last().pose(), 16, 0, 0).uv(16, 0);

        poseStack.popPose();
    }
}
