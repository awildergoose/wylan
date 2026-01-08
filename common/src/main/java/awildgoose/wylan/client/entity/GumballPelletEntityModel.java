package awildgoose.wylan.client.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

@Environment(EnvType.CLIENT)
public class GumballPelletEntityModel extends EntityModel<GumballPelletEntityRenderState> {
	public GumballPelletEntityModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 15).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
																  PartPose.offset(0.0F, 24.0F, 0.0F));

		bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create()
										  .texOffs(16, 5)
										  .addBox(-2.0F, -0.5F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
								  PartPose.offsetAndRotation(2.5F, -3.0F, 0.0F, 0.0F, 1.5708F, -1.5708F));
		bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create()
										  .texOffs(16, 0)
										  .addBox(-2.0F, -0.5F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
								  PartPose.offsetAndRotation(-2.5F, -3.0F, 0.0F, 0.0F, 1.5708F, -1.5708F));
		bb_main.addOrReplaceChild("cube_r3", CubeListBuilder.create()
										  .texOffs(0, 10)
										  .addBox(-2.0F, -0.5F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
								  PartPose.offsetAndRotation(0.0F, -3.0F, -2.5F, 1.5708F, 0.0F, 0.0F));
		bb_main.addOrReplaceChild("cube_r4", CubeListBuilder.create()
										  .texOffs(0, 5)
										  .addBox(-2.0F, -0.5F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
								  PartPose.offsetAndRotation(0.0F, -3.0F, 2.5F, 1.5708F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}
}
