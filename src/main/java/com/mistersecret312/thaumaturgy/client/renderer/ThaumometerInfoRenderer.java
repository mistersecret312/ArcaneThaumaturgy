package com.mistersecret312.thaumaturgy.client.renderer;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class ThaumometerInfoRenderer extends BlockEntityWithoutLevelRenderer {

    public static ThaumometerInfoRenderer INSTANCE = new ThaumometerInfoRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());

    public ThaumometerInfoRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
    }

    @Override
    public void renderByItem(ItemStack pStack, ItemDisplayContext pDisplayContext, PoseStack pose, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {

        pose.pushPose();
        pose.mulPose(Axis.ZP.rotationDegrees(180));
        pose.translate(-0.7, -1, 0.55);
        pose.scale(0.01F, 0.01F, 0.01F);
        Minecraft.getInstance().font.drawInBatch("Hiya, Secret!", 0, 0, 0x000000, false, pose.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0xFFFFFF, pPackedLight);
        pose.popPose();

        final BakedModel model = Minecraft.getInstance().getModelManager().getModel(ResourceLocation.fromNamespaceAndPath(ArcaneThaumaturgyMod.MODID, "item/thaumometer_baked"));
        Minecraft.getInstance().getItemRenderer().renderModelLists(model, pStack, pPackedLight, pPackedOverlay, pose, pBuffer.getBuffer(RenderType.translucent()));

        super.renderByItem(pStack, pDisplayContext, pose, pBuffer, pPackedLight, pPackedOverlay);
    }
}
