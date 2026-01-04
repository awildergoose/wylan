package awildgoose.wylan.client.entity;

import awildgoose.wylan.entity.SkinwalkerTexture;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

@Environment(EnvType.CLIENT)
public class SkinwalkerEntityRenderState extends HumanoidRenderState {
	public SkinwalkerTexture texture;
}
