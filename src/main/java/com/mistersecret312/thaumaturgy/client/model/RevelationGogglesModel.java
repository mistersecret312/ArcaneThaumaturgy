package com.mistersecret312.thaumaturgy.client.model;

import com.google.common.collect.ImmutableList;
import com.mistersecret312.thaumaturgy.client.Layers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RevelationGogglesModel extends HumanoidModel<LivingEntity> {

	public static RevelationGogglesModel INSTANCE;

	public RevelationGogglesModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createArmorLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition head = partdefinition.getChild("head");

		head.addOrReplaceChild("goggles", CubeListBuilder.create()
				.texOffs(0, 0)
				.addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F,
						new CubeDeformation(0.25F)),
				PartPose.offset(0.0F, -4.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 16);
	}

	@Override
	protected Iterable<ModelPart> bodyParts()
	{
		return ImmutableList.of();
	}

	@SubscribeEvent
	public static void bakeModelLayers(EntityRenderersEvent.AddLayers event)
	{
		EntityModelSet set = event.getEntityModels();
		INSTANCE = new RevelationGogglesModel(set.bakeLayer(Layers.REVELATION_GOGGLES));
	}
}