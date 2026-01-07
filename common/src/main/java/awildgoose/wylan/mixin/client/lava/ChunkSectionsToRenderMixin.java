package awildgoose.wylan.mixin.client.lava;

import net.minecraft.client.renderer.chunk.ChunkSectionLayerGroup;
import net.minecraft.client.renderer.chunk.ChunkSectionsToRender;
import net.minecraft.util.profiling.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkSectionsToRender.class)
public class ChunkSectionsToRenderMixin {
	@Inject(at = @At("RETURN"), method = "renderGroup(Lnet/minecraft/client/renderer/chunk/ChunkSectionLayerGroup;)V")
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
