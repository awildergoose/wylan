package awildgoose.wylan.neoforge.sodium;

import net.caffeinemc.mods.sodium.client.render.chunk.terrain.TerrainRenderPass;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.Material;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.parameters.AlphaCutoffParameter;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

public class SodiumCompat {
	public static TerrainRenderPass LAVA_PASS = new TerrainRenderPass(ChunkSectionLayer.valueOf("LAVA"),
														   false,
														   false);
	public static Material LAVA_MATERIAL = new Material(
			LAVA_PASS,
			AlphaCutoffParameter.ZERO,
			true
	);
}
