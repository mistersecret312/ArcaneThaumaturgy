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
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public class NitorRenderer implements BlockEntityRenderer<NitorBlockEntity>
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(ArcaneThaumaturgyMod.MODID, "block/nitor");

    public NitorRenderer(BlockEntityRendererProvider.Context context)
    {

    }

    @Override
    public void render(NitorBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int pPackedOverlay)
    {

        final TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS)
                .apply(TEXTURE);

        poseStack.pushPose();
        VertexConsumer consumer = buffer.getBuffer(ThaumaturgyRenderTypes.nitor(sprite.atlasLocation()));
        consumer.vertex(poseStack.last().pose(), 0, 0, 0).uv(sprite.getU1(), sprite.getV1()).endVertex();
        consumer.vertex(poseStack.last().pose(), 1, 0, 0).uv(sprite.getU0(), sprite.getV1()).endVertex();
        consumer.vertex(poseStack.last().pose(), 1, 1, 0).uv(sprite.getU0(), sprite.getV0()).endVertex();
        consumer.vertex(poseStack.last().pose(), 0, 1, 0).uv(sprite.getU1(), sprite.getV0()).endVertex();


        poseStack.popPose();
    }
}
