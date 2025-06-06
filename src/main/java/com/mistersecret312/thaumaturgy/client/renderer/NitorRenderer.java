package com.mistersecret312.thaumaturgy.client.renderer;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.block_entities.NitorBlockEntity;
import com.mistersecret312.thaumaturgy.blocks.NitorBlock;
import com.mistersecret312.thaumaturgy.client.ThaumaturgyRenderTypes;
import com.mistersecret312.thaumaturgy.init.BlockInit;
import com.mistersecret312.thaumaturgy.util.RenderBlitUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Quaternionf;

public class NitorRenderer implements BlockEntityRenderer<NitorBlockEntity>
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(ArcaneThaumaturgyMod.MODID, "block/nitor/nitor_");

    public NitorRenderer(BlockEntityRendererProvider.Context context)
    {

    }

    @Override
    public void render(NitorBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int pPackedOverlay)
    {
        String texture = "block/nitor/nitor_" + blockEntity.getBlockState().getValue(NitorBlock.TYPE).getSerializedName();

        final TextureAtlasSprite spriteA = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS)
                .apply(new ResourceLocation(ArcaneThaumaturgyMod.MODID, texture));

        poseStack.pushPose();

        poseStack.translate(0.5f, 0.5f, 0.5f);

        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180));

        VertexConsumer consumerA = buffer.getBuffer(ThaumaturgyRenderTypes.nitor(spriteA.atlasLocation()));
        consumerA.vertex(poseStack.last().pose(), -0.5f, -0.5f, 0).color(FastColor.ABGR32.color(255, 255, 255, 255)).uv(spriteA.getU0(), spriteA.getV1()).endVertex();
        consumerA.vertex(poseStack.last().pose(), 0.5f, -0.5f, 0).color(FastColor.ABGR32.color(255, 255, 255, 255)).uv(spriteA.getU1(), spriteA.getV1()).endVertex();
        consumerA.vertex(poseStack.last().pose(), 0.5f, 0.5f, 0).color(FastColor.ABGR32.color(255, 255, 255, 255)).uv(spriteA.getU1(), spriteA.getV0()).endVertex();
        consumerA.vertex(poseStack.last().pose(), -0.5f, 0.5f, 0).color(FastColor.ABGR32.color(255, 255, 255, 255)).uv(spriteA.getU0(), spriteA.getV0()).endVertex();

        poseStack.popPose();

    }
}
