package awildgoose.wylan.mixin.client;

import awildgoose.wylan.client.init.ModRendering;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

@Mixin(ChunkSectionLayer.class)
public class ChunkSectionLayerMixin {
	@Inject(method = "<clinit>", at = @At("RETURN"))
	private static void addLavaLayer(CallbackInfo ci) {
		try {
			ChunkSectionLayer[] values = ChunkSectionLayer.values();

			Constructor<ChunkSectionLayer> ctor = ChunkSectionLayer.class.getDeclaredConstructor(
					String.class, int.class, RenderPipeline.class, int.class, boolean.class, boolean.class
			);
			ctor.setAccessible(true);

			ChunkSectionLayer lava = ctor.newInstance(
					"LAVA",
					values.length,
					ModRendering.LAVA_PIPELINE,
					786432,
					true,
					true
			);

			Field valuesField = ChunkSectionLayer.class.getDeclaredField("$VALUES");
			valuesField.setAccessible(true);
			ChunkSectionLayer[] newValues = Arrays.copyOf(values, values.length + 1);
			newValues[values.length] = lava;
			valuesField.set(null, newValues);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}