package awildgoose.wylan.mixin.client;

import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(ChunkSectionLayer.class)
public class ChunkSectionLayerMixin {
	@Shadow
	private static ChunkSectionLayer[] $VALUES;
	@Unique private static ChunkSectionLayer LAVA;

	@Inject(method = "<clinit>", at = @At("RETURN"))
	private static void injectLavaLayer(CallbackInfo ci) {
		ChunkSectionLayer placeholder = $VALUES[0];

		LAVA = placeholder;

		ChunkSectionLayer[] newValues = Arrays.copyOf($VALUES, $VALUES.length + 1);
		newValues[newValues.length - 1] = LAVA;

		try {
			java.lang.reflect.Field valuesField = ChunkSectionLayer.class.getDeclaredField("$VALUES");
			valuesField.setAccessible(true);
			valuesField.set(null, newValues);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}