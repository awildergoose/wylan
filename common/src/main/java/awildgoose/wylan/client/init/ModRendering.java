package awildgoose.wylan.client.init;

import awildgoose.wylan.WylanMod;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import static net.minecraft.client.renderer.RenderPipelines.TERRAIN_SNIPPET;

public class ModRendering {
	public static final RenderPipeline LAVA_PIPELINE = register(
			RenderPipeline.builder(TERRAIN_SNIPPET)
					.withLocation(path("pipeline/lava"))
					.build()
	);
	public static final RenderType LAVA = RenderType.create(
			"lava", 1536, false, true, LAVA_PIPELINE,
			RenderType.CompositeState.builder()
					.setOutputState(RenderStateShard.MAIN_TARGET)
					.createCompositeState(false)
	);

	private static ResourceLocation path(String p) {
		return ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID, p);
	}

	private static RenderPipeline register(RenderPipeline pipeline) {
		return RenderPipelines.PIPELINES_BY_LOCATION.put(pipeline.getLocation(), pipeline);
	}

	public static void init() {

	}
}
