package awildgoose.wylan.fabric.sodium;

import net.caffeinemc.mods.sodium.client.render.chunk.terrain.TerrainRenderPass;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.Material;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.parameters.AlphaCutoffParameter;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

public class SodiumCompat {

	public static Object LAVA_PASS;
	public static Object LAVA_MATERIAL;

	public SodiumCompat() {
		LAVA_PASS = new TerrainRenderPass(ChunkSectionLayer.valueOf("LAVA"),
											 false,
											 false);
		LAVA_MATERIAL = new Material(
				(TerrainRenderPass) LAVA_PASS,
				AlphaCutoffParameter.ZERO,
				true
		);
	}
}
