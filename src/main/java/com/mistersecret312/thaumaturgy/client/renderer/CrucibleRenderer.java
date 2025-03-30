package com.mistersecret312.thaumaturgy.client.renderer;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.block_entities.CrucibleBlockEntity;
import com.mistersecret312.thaumaturgy.block_entities.NitorBlockEntity;
import com.mistersecret312.thaumaturgy.client.ThaumaturgyRenderTypes;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;

public class CrucibleRenderer implements BlockEntityRenderer<CrucibleBlockEntity>
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(ArcaneThaumaturgyMod.MODID, "block/crucible/water_2");

    public CrucibleRenderer(BlockEntityRendererProvider.Context context)
    {

    }

    @Override
    public void render(CrucibleBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int pPackedOverlay)
    {

        final TextureAtlasSprite spriteA = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS)
                .apply(TEXTURE);

        poseStack.pushPose();

        //Y range to be visible correctly [0.26f, 0.99f]
        //Cauldron full bucket - 0.95f;
        //Cauldron 2/3 bottles - 0.75f;
        //Cauldron 1/3 bottles - 0.55f
        poseStack.translate(0.5f, 0.95f, 0.5f);

        //poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Axis.XN.rotationDegrees(90));


        VertexConsumer consumerA = buffer.getBuffer(ThaumaturgyRenderTypes.crucibleWater(spriteA.atlasLocation()));
        consumerA.vertex(poseStack.last().pose(), -0.5f, -0.5f, 0).uv(spriteA.getU0(), spriteA.getV1()).endVertex();
        consumerA.vertex(poseStack.last().pose(), 0.5f, -0.5f, 0).uv(spriteA.getU1(), spriteA.getV1()).endVertex();
        consumerA.vertex(poseStack.last().pose(), 0.5f, 0.5f, 0).uv(spriteA.getU1(), spriteA.getV0()).endVertex();
        consumerA.vertex(poseStack.last().pose(), -0.5f, 0.5f, 0).uv(spriteA.getU0(), spriteA.getV0()).endVertex();

        poseStack.popPose();

    }
}
