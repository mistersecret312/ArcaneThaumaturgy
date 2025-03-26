package com.mistersecret312.thaumaturgy.client.renderer;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.datapack.Aspect;
import com.mistersecret312.thaumaturgy.datapack.AspectComposition;
import com.mistersecret312.thaumaturgy.util.RenderBlitUtil;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.joml.Matrix4f;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class ThaumometerInfoRenderer extends BlockEntityWithoutLevelRenderer {

    public static ThaumometerInfoRenderer INSTANCE = new ThaumometerInfoRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());

    public ThaumometerInfoRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
    }

    @Override
    public void renderByItem(ItemStack pStack, ItemDisplayContext pDisplayContext, PoseStack pose, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {

        final BakedModel model = Minecraft.getInstance().getModelManager().getModel(ResourceLocation.fromNamespaceAndPath(ArcaneThaumaturgyMod.MODID, "item/thaumometer_baked"));
        Minecraft.getInstance().getItemRenderer().renderModelLists(model, pStack, pPackedLight, pPackedOverlay, pose, pBuffer.getBuffer(RenderType.translucent()));


        pose.pushPose();
        pose.mulPose(Axis.ZP.rotationDegrees(180));
        pose.translate(-0.5, -0.7, 0.49);
        pose.scale(0.01F, 0.01F, 0.01F);



        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        Font font = minecraft.font;

        ClientLevel level = (ClientLevel) player.level();
        BlockHitResult rayTrace = level.clip(new ClipContext(player.getEyePosition(1F), player.getEyePosition(1F).add(player.getLookAngle().scale(5F)),
                ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, null));

        BlockPos pos = rayTrace.getBlockPos();
        BlockState blockState = level.getBlockState(pos);
        if(!blockState.isAir())
        {
            pose.pushPose();
            String name = blockState.getBlock().getName().getString();
            float textWidth = font.width(name);
            font.drawInBatch(name, -textWidth/2, -45, -1, false, pose.last().pose(), pBuffer, Font.DisplayMode.NORMAL, 0, 15728880);
            pose.popPose();

            ClientPacketListener packetListener = minecraft.getConnection();
            RegistryAccess access = packetListener.registryAccess();
            Registry<AspectComposition> aspectCompositions = access.registryOrThrow(AspectComposition.REGISTRY_KEY);
            Registry<Aspect> aspects = access.registryOrThrow(Aspect.REGISTRY_KEY);

            Stream<Map.Entry<ResourceKey<AspectComposition>, AspectComposition>> filtered = aspectCompositions.entrySet().stream().filter(key -> {
                Item item = key.getValue().getItem();
                return blockState.getBlock().asItem().equals(item);
            });
            Optional<Map.Entry<ResourceKey<AspectComposition>, AspectComposition>> object = filtered.findFirst();
            if(object.isPresent())
            {
                for (int i = 0; i < object.get().getValue().getAspects().size(); i++)
                {
                    AspectComposition.AspectStack stack = object.get().getValue().getAspects().get(i);
                    Aspect aspect = aspects.get(stack.getAspect());
                    if (aspect != null)
                    {
                        pose.pushPose();

                        pose.translate((object.get().getValue().getAspects().size()*-11)+(22*i)+1, 5, 0);

                        RenderSystem.setShaderTexture(0, aspect.getTexture());
                        RenderSystem.setShader(GameRenderer::getPositionTexShader);
                        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                        Matrix4f matrix4f = pose.last().pose();
                        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
                        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                        bufferbuilder.vertex(matrix4f, 0, 0, 0).uv(0, 0).endVertex();
                        bufferbuilder.vertex(matrix4f, 0, 18, 0).uv(0, 1).endVertex();
                        bufferbuilder.vertex(matrix4f, 18, 18, 0).uv(1, 1).endVertex();
                        bufferbuilder.vertex(matrix4f, 18, 0, 0).uv(1, 0).endVertex();
                        BufferUploader.drawWithShader(bufferbuilder.end());

                        font.drawInBatch(String.valueOf(stack.getAmount()), 15, 12, -1, false, pose.last().pose(),pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, 15728880);

                        pose.popPose();
                    }
                }
            }
        }

        //Minecraft.getInstance().font.drawInBatch("Hiya, Secret!", 0, 0, -1, false, pose.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, 15728880);

        pose.popPose();


        //super.renderByItem(pStack, pDisplayContext, pose, pBuffer, pPackedLight, pPackedOverlay);
    }
}
