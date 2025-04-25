package com.mistersecret312.thaumaturgy.client.renderer;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.aspects.AspectStack;
import com.mistersecret312.thaumaturgy.aspects.Aspect;
import com.mistersecret312.thaumaturgy.datapack.AspectComposition;
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
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
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

        if(!pDisplayContext.firstPerson())
            return;

        pose.pushPose();
        pose.mulPose(Axis.ZP.rotationDegrees(180));
        pose.translate(-0.5, -0.7, 0.49);
        pose.scale(0.01F, 0.01F, 0.01F);

        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        Font font = minecraft.font;

        if(player != null)
        {
            if(!searchForEntity(player, pose, font, pBuffer, minecraft))
                searchForBlock(player, pose, font, pBuffer, minecraft);
        }



        //Minecraft.getInstance().font.drawInBatch("Hiya, Secret!", 0, 0, -1, false, pose.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, 15728880);

        pose.popPose();


        //super.renderByItem(pStack, pDisplayContext, pose, pBuffer, pPackedLight, pPackedOverlay);
    }

    public boolean searchForEntity(LocalPlayer player, PoseStack pose, Font font, MultiBufferSource buffer, Minecraft minecraft)
    {
        Vec3 start = player.getEyePosition();
        Vec3 end = start.add(player.getViewVector(1F).scale(5f));
        EntityHitResult result = ProjectileUtil.getEntityHitResult(player.level(), player, start, end, new AABB(start, end).inflate(1D), entity -> true, 0F);

        ClientPacketListener packetListener = minecraft.getConnection();
        RegistryAccess access = packetListener.registryAccess();
        Registry<AspectComposition> aspectCompositions = access.registryOrThrow(AspectComposition.REGISTRY_KEY);


        if(result != null)
        {
            Entity entity = result.getEntity();
            if(entity instanceof ItemEntity itemEntity)
            {
                ItemStack stack = itemEntity.getItem();

                pose.pushPose();
                String name = stack.getItem().getName(stack).getString();
                float textWidth = font.width(name);
                font.drawInBatch(name, -textWidth/2, -45, -1, false, pose.last().pose(), buffer, Font.DisplayMode.NORMAL, 0, 15728880);
                pose.popPose();

                Optional<Map.Entry<ResourceKey<AspectComposition>, AspectComposition>> object = aspectCompositions.entrySet().stream().filter(entry -> {
                    Item item = entry.getValue().getItem();
                    if(item != null)
                    {
                        return stack.is(item);
                    }
                    return false;
                }).findFirst();

                object.ifPresent(resourceKeyAspectCompositionEntry -> renderAspectComposition(pose, font, buffer, minecraft, resourceKeyAspectCompositionEntry));
            }
            else
            {
                pose.pushPose();
                String name = entity.getType().getDescription().getString();
                float textWidth = font.width(name);
                font.drawInBatch(name, -textWidth/2, -45, -1, false, pose.last().pose(), buffer, Font.DisplayMode.NORMAL, 0, 15728880);
                pose.popPose();

                Optional<Map.Entry<ResourceKey<AspectComposition>, AspectComposition>> object = aspectCompositions.entrySet().stream().filter(entry -> {
                    EntityType<?> type = entry.getValue().getEntity();
                    if(type != null)
                    {
                        return entity.getType().equals(type);
                    }
                    return false;
                }).findFirst();

                object.ifPresent(resourceKeyAspectCompositionEntry -> renderAspectComposition(pose, font, buffer, minecraft, resourceKeyAspectCompositionEntry));

            }

            return true;
        }

        return false;
    }

    public void searchForBlock(LocalPlayer player, PoseStack pose, Font font, MultiBufferSource buffer, Minecraft minecraft)
    {
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
            float scale = textWidth > 80 ? 80/textWidth : 1f;
            pose.scale(scale, scale, scale);
            font.drawInBatch(name, -textWidth/2, -45-(textWidth > 80 ? (textWidth-80)*scale : 0), -1, false, pose.last().pose(), buffer, Font.DisplayMode.NORMAL, 0, 15728880);
            pose.popPose();

            ClientPacketListener packetListener = minecraft.getConnection();
            RegistryAccess access = packetListener.registryAccess();
            Registry<AspectComposition> aspectCompositions = access.registryOrThrow(AspectComposition.REGISTRY_KEY);

            Stream<Map.Entry<ResourceKey<AspectComposition>, AspectComposition>> filtered = aspectCompositions.entrySet().stream().filter(key -> {
                Item item = key.getValue().getItem();
                if(item != null && item != Items.AIR)
                {
                    return blockState.getBlock().asItem().equals(item);
                }
                Fluid fluid = key.getValue().getFluid();
                if(fluid != null)
                {
                    return blockState.getFluidState().getFluidType().equals(fluid.getFluidType());
                }
                return false;
            });
            Optional<Map.Entry<ResourceKey<AspectComposition>, AspectComposition>> object = filtered.findFirst();
            object.ifPresent(resourceKeyAspectCompositionEntry -> renderAspectComposition(pose, font, buffer, minecraft, resourceKeyAspectCompositionEntry));
        }
    }

    public void renderAspectComposition(PoseStack pose, Font font, MultiBufferSource buffer, Minecraft minecraft, Map.Entry<ResourceKey<AspectComposition>, AspectComposition> object)
    {
        for (int i = 0; i < object.getValue().getAspects().size(); i++)
        {
            AspectStack stack = object.getValue().getAspects().get(i);
            Aspect aspect = stack.getAspect();
            if (aspect != null)
            {
                ResourceLocation texture = aspect.getTexture();
                if(minecraft.getResourceManager().getResource(texture).isEmpty())
                    texture = ResourceLocation.fromNamespaceAndPath(ArcaneThaumaturgyMod.MODID, "textures/aspect/error.png");


                pose.pushPose();
                pose.translate((object.getValue().getAspects().size()*-11)+(22*i)+1, 5, 0);

                RenderSystem.setShaderTexture(0, texture);
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

                font.drawInBatch(String.valueOf(stack.getAmount()), 15, 12, -1, false, pose.last().pose(), buffer, Font.DisplayMode.POLYGON_OFFSET, 0, 15728880);

                pose.popPose();
            }
        }
    }
}
