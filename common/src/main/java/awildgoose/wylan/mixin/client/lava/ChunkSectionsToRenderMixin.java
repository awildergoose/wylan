package awildgoose.wylan.mixin.client.lava;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.chunk.ChunkSectionLayerGroup;
import net.minecraft.client.renderer.chunk.ChunkSectionsToRender;
import net.minecraft.util.profiling.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ChunkSectionsToRender.class)
public class ChunkSectionsToRenderMixin {
	@Inject(at = @At("HEAD"), method = "renderGroup(Lnet/minecraft/client/renderer/chunk/ChunkSectionLayerGroup;)V"
			, order = 500) // allow us to render our group, sodium!
	public void renderGroup(ChunkSectionLayerGroup chunkSectionLayerGroup, CallbackInfo ci) {
		if (chunkSectionLayerGroup == ChunkSectionLayerGroup.TRIPWIRE) {
			// ITS TIME FOR US!!!!!!
			Profiler.get().popPush("lava");
			@SuppressWarnings("DataFlowIssue")
			ChunkSectionsToRender self = (ChunkSectionsToRender) (Object) this;
			self.renderGroup(ChunkSectionLayerGroup.valueOf("LAVA"));
		}
	}
}
