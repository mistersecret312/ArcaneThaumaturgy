package com.mistersecret312.thaumaturgy.client.renderer;

import com.mistersecret312.thaumaturgy.items.AspectItem;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;

public class FakeAspectRenderer extends BlockEntityWithoutLevelRenderer
{
    public FakeAspectRenderer()
    {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack,
                             MultiBufferSource buffer, int light, int overlay)
    {
        if(stack.getItem() instanceof AspectItem aspectItem)
        {
            ResourceLocation texture = aspectItem.getTexture(stack);

            RenderSystem.setShaderTexture(0, texture);
            VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(texture));

            poseStack.pushPose();
            light = 15728880;

            // Basic textured quad (flat 2D sprite)
            Matrix4f matrix = poseStack.last().pose();

            float size = 1f; // You can scale this
            float z = 0.0f;

            vertexConsumer.vertex(matrix, 0, 0, z).color(255, 255, 255, 255).uv(0, 1).overlayCoords(overlay).uv2(light).normal(0, 1, 0).endVertex();
            vertexConsumer.vertex(matrix, size, 0, z).color(255, 255, 255, 255).uv(1, 1).overlayCoords(overlay).uv2(light).normal(0, 1, 0).endVertex();
            vertexConsumer.vertex(matrix, size, size, z).color(255, 255, 255, 255).uv(1, 0).overlayCoords(overlay).uv2(light).normal(0, 1, 0).endVertex();
            vertexConsumer.vertex(matrix, 0, size, z).color(255, 255, 255, 255).uv(0, 0).overlayCoords(overlay).uv2(light).normal(0, 1, 0).endVertex();

            poseStack.popPose();}
    }
}
