package awildgoose.wylan.client.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.*;

@Environment(EnvType.CLIENT)
public class SkinwalkerEntityModel extends HumanoidModel<SkinwalkerEntityRenderState> {
	public SkinwalkerEntityModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		return LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0), 64, 64);
	}

	@Override
	public void setupAnim(SkinwalkerEntityRenderState entityRenderState) {
		super.setupAnim(entityRenderState);
	}

}
