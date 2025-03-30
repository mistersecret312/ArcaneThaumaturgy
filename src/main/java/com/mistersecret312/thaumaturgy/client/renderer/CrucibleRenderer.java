package com.mistersecret312.thaumaturgy.client.renderer;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.aspects.Aspects;
import com.mistersecret312.thaumaturgy.block_entities.CrucibleBlockEntity;
import com.mistersecret312.thaumaturgy.block_entities.NitorBlockEntity;
import com.mistersecret312.thaumaturgy.blocks.RunicMatrixBlock;
import com.mistersecret312.thaumaturgy.client.ThaumaturgyRenderTypes;
import com.mistersecret312.thaumaturgy.init.BlockInit;
import com.mojang.blaze3d.platform.Lighting;
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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.data.ModelData;

public class CrucibleRenderer implements BlockEntityRenderer<CrucibleBlockEntity>
{
    public CrucibleRenderer(BlockEntityRendererProvider.Context context)
    {

    }

    @Override
    public void render(CrucibleBlockEntity crucible, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int pPackedOverlay)
    {
        //Y range to be visible correctly [0.26f, 0.99f]
        //Crucible max, no overflow - 0.825f;
        //Crucible level 3 -

        //Level step - 0.0625
        //Level 1 - 0.4375

/*        int waterLevel = crucible.getWaterLevel();
        if (waterLevel > 0) {
            poseStack.pushPose();

            int aspectLimitStep = 64;
            float aspectLimit = waterLevel * aspectLimitStep;
            float aspectAmount = crucible.handler.getTotalStored();
            int overflowStep = Math.round(aspectAmount / (aspectLimit / 4));

            final TextureAtlasSprite spriteA = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS)
                    .apply(new ResourceLocation(ArcaneThaumaturgyMod.MODID, "block/crucible/water_" + (overflowStep + 1)));

            float waterHeight = (4 + 1 + 2 * waterLevel + overflowStep) * 0.0625f;

            poseStack.translate(0.5f, waterHeight, 0.5f);

            poseStack.mulPose(Axis.XN.rotationDegrees(90));

            VertexConsumer consumerA = buffer.getBuffer(ThaumaturgyRenderTypes.crucibleWater(spriteA.atlasLocation()));
            consumerA.vertex(poseStack.last().pose(), -0.5f, -0.5f, 0).uv(spriteA.getU0(), spriteA.getV1()).endVertex();
            consumerA.vertex(poseStack.last().pose(), 0.5f, -0.5f, 0).uv(spriteA.getU1(), spriteA.getV1()).endVertex();
            consumerA.vertex(poseStack.last().pose(), 0.5f, 0.5f, 0).uv(spriteA.getU1(), spriteA.getV0()).endVertex();
            consumerA.vertex(poseStack.last().pose(), -0.5f, 0.5f, 0).uv(spriteA.getU0(), spriteA.getV0()).endVertex();

            poseStack.popPose();
        }*/
    }

    @Override
    public boolean shouldRenderOffScreen(CrucibleBlockEntity pBlockEntity)
    {
        return true;
    }
}
