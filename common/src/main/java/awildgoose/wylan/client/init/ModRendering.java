package awildgoose.wylan.client.init;

import awildgoose.wylan.WylanMod;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.shaders.UniformType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import static net.minecraft.client.renderer.RenderStateShard.BLOCK_SHEET_MIPPED;
import static net.minecraft.client.renderer.RenderStateShard.LIGHTMAP;

@Environment(EnvType.CLIENT)
public class ModRendering {
	public static final RenderPipeline LAVA_PIPELINE = register(
			RenderPipeline.builder(RenderPipelines.TERRAIN_SNIPPET, RenderPipelines.GLOBALS_SNIPPET)
					.withUniform("Goose", UniformType.UNIFORM_BUFFER)
					.withVertexShader(path("core/rendertype_lava"))
					.withFragmentShader(path("core/rendertype_lava"))
					.withLocation(path("pipeline/lava"))
					.build()
	);
	public static final RenderType LAVA = RenderType.create(
			"lava",
			1536,
			true,
			false,
			LAVA_PIPELINE,
			RenderType.CompositeState.builder()
					.setLightmapState(LIGHTMAP)
					.setTextureState(BLOCK_SHEET_MIPPED)
					.createCompositeState(true)
	);

	private static ResourceLocation path(String p) {
		return ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID, p);
	}

	private static RenderPipeline register(RenderPipeline pipeline) {
		RenderPipelines.PIPELINES_BY_LOCATION.put(pipeline.getLocation(), pipeline);
		return pipeline;
	}

	public static LavaTransitionState lavaTransitionState = LavaTransitionState.IDLE;
	public static float lavaTransitionProgress = 0.0f;

	public enum LavaTransitionState {
		IDLE,
		LAVA_TO_OIL,
		OIL_TO_LAVA
	}

	public static void preRender(DeltaTracker deltaTracker) {
		float deltaTime = deltaTracker.getRealtimeDeltaTicks();
		float duration = 5.0f;

		int direction = switch (lavaTransitionState) {
			case LAVA_TO_OIL -> 1;
			case OIL_TO_LAVA -> -1;
			default -> 0;
		};

		if (direction != 0) {
			lavaTransitionProgress += (deltaTime / duration) * direction;
			lavaTransitionProgress = Mth.clamp(lavaTransitionProgress, 0f, 1f);

			if (lavaTransitionProgress <= 0f || lavaTransitionProgress >= 1f) {
				lavaTransitionState = LavaTransitionState.IDLE;
			}
		}
	}

	public static void init() {

	}
}
