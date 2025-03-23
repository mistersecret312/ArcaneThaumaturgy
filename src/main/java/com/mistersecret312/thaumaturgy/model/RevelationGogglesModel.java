package com.mistersecret312.thaumaturgy.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class RevelationGogglesModel extends HumanoidModel {
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
				PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 16);
	}
}