package awildgoose.wylan.client.entity;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class HenryEntityModel extends EntityModel<HenryEntityRenderState> {
	private final ModelPart head;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;

	public HenryEntityModel(ModelPart modelPart) {
		super(modelPart);

		head = root.getChild(PartNames.HEAD);
		leftLeg = root.getChild(PartNames.LEFT_LEG);
		rightLeg = root.getChild(PartNames.RIGHT_LEG);
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition root = modelData.getRoot();
		root.addOrReplaceChild(
				PartNames.BODY,
				CubeListBuilder.create().addBox(-6, -6, -6, 12, 12, 12),// x , y ,z  is the dimensions  |  width, height, depth
				PartPose.offset(0, 8, 0)
		);
		root.addOrReplaceChild(
				PartNames.HEAD,
				CubeListBuilder.create().texOffs(36, 0).addBox(-3, -6, -3, 6, 6, 6),
				PartPose.offset(0, 2, 0)
		);
		root.addOrReplaceChild(
				PartNames.LEFT_LEG,
				CubeListBuilder.create().texOffs(48, 12).addBox(-2, 0, -2, 4, 10, 4),
				PartPose.offset(-2.5f, 14, 0)
		);
		root.addOrReplaceChild(
				PartNames.RIGHT_LEG,
				CubeListBuilder.create().texOffs(48, 12).addBox(-2, 0, -2, 4, 10, 4),
				PartPose.offset(2.5f, 14, 0)
		);
		return LayerDefinition.create(modelData, 64, 32);
	}

	@Override
	public void setupAnim(HenryEntityRenderState state) {
		super.setupAnim(state);

		head.xRot = state.xRot * Mth.RAD_TO_DEG;
		head.yRot = state.yRot * Mth.RAD_TO_DEG;
		float limbSwingAmplitude = state.walkAnimationSpeed;
		float limbSwingAnimationProgress = state.walkAnimationPos;
		leftLeg.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.4f * limbSwingAmplitude;
		rightLeg.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;
	}

}
