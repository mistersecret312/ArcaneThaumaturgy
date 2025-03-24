package com.mistersecret312.thaumaturgy.client;

import com.mistersecret312.thaumaturgy.ArcaneThaumaturgyMod;
import com.mistersecret312.thaumaturgy.client.model.RevelationGogglesModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class Layers
{
    public static final ModelLayerLocation REVELATION_GOGGLES = new ModelLayerLocation(new ResourceLocation(ArcaneThaumaturgyMod.MODID, "revelation_goggles"), "main");

    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(REVELATION_GOGGLES, RevelationGogglesModel::createArmorLayer);
    }
}
