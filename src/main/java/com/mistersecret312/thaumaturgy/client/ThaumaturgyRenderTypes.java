package com.mistersecret312.thaumaturgy.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ThaumaturgyRenderTypes extends RenderType
{

    public ThaumaturgyRenderTypes(String pName, VertexFormat pFormat, VertexFormat.Mode pMode, int pBufferSize,
                                  boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState,
                                  Runnable pClearState)
    {
        super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
    }

    public static RenderType nitor(ResourceLocation location)
    {
        return create("nitor", DefaultVertexFormat.POSITION_TEX,
                VertexFormat.Mode.QUADS, 256, true, true,
                RenderType.CompositeState.builder()
                        .setShaderState(RenderStateShard.RENDERTYPE_ITEM_ENTITY_TRANSLUCENT_CULL_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(location, false, false))
                        .createCompositeState(true)
        );
    }
}
