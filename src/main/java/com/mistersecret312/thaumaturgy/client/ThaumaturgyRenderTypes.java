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

    public static RenderType aspect(ResourceLocation location)
    {
        return create("aspect", DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, false, true,
                RenderType.CompositeState.builder()
                        .setShaderState(RenderStateShard.RENDERTYPE_TEXT_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(location, false, true))
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .createCompositeState(true));
    }
}
