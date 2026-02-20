package awildgoose.wylan.neoforge.mixin.client.sodium;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.opengl.GlTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import net.caffeinemc.mods.sodium.client.gl.shader.uniform.GlUniformInt;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.ChunkShaderTextureSlot;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.DefaultShaderInterface;
import net.neoforged.neoforge.client.blaze3d.validation.ValidationGpuTexture;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

// This is a NeoForge only fix!
@Mixin(DefaultShaderInterface.class)
public class NFDefaultShaderInterfaceMixin {
	@Shadow @Final private Map<ChunkShaderTextureSlot, GlUniformInt> uniformTextures;

	@Inject(at = @At("HEAD"), method = "bindTexture", cancellable = true)
	private void bindTexture(ChunkShaderTextureSlot slot, GpuTextureView textureView, CallbackInfo ci) {
		if (textureView.texture() instanceof ValidationGpuTexture vTex) {
			ci.cancel();

			GlTexture tex = (GlTexture)vTex.getRealTexture();
			GlStateManager._activeTexture('蓀' + slot.ordinal());
			GlStateManager._bindTexture(tex.glId());
			GlStateManager._texParameter(3553, 33084, textureView.baseMipLevel());
			GlStateManager._texParameter(3553, 33085, textureView.baseMipLevel() + textureView.mipLevels() - 1);
			tex.flushModeChanges(3553);
			GlUniformInt uniform = this.uniformTextures.get(slot);
			uniform.setInt(slot.ordinal());
		}
	}
}
