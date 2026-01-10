package awildgoose.wylan.mixin.client;

import awildgoose.wylan.client.ScreenshakeHandler;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.util.RandomSource;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(LevelRenderer.class)
public class ScreenshakeMixin {
	@Inject(
			at = @At("HEAD"),
			method = "renderLevel"
	)
	public void renderLevel(
			GraphicsResourceAllocator graphicsResourceAllocator,
			DeltaTracker deltaTracker,
			boolean bl,
			Camera camera,
			Matrix4f positionMatrix,
			Matrix4f matrix4f2,
			GpuBufferSlice gpuBufferSlice,
			Vector4f vector4f,
			boolean bl2
	) {
		float intensity = ScreenshakeHandler.getIntensity();
		if (intensity <= 0f) return;

		var world = camera.getEntity().level();

		RandomSource rand = world.getRandom();
		float yawOffset = (rand.nextFloat() - 0.5f) * 2f * intensity;
		float pitchOffset = (rand.nextFloat() - 0.5f) * 2f * intensity;

		positionMatrix.rotateX((float) Math.toRadians(pitchOffset));
		positionMatrix.rotateY((float) Math.toRadians(yawOffset));
	}
}
