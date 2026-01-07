package awildgoose.wylan.client.render;

import com.mojang.blaze3d.buffers.GpuBuffer;

public class RenderSystemExtension {
	private static GpuBuffer gooseUniformBuffer;

	public static void setGooseUniformBuffer(GpuBuffer buffer) {
		gooseUniformBuffer = buffer;
	}

	public static GpuBuffer getGooseUniformBuffer() {
		return gooseUniformBuffer;
	}
}
