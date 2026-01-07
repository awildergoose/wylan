package awildgoose.wylan.client.render;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;

@Environment(EnvType.CLIENT)
public class GooseUniform implements AutoCloseable {
	public static final int UBO_SIZE = new Std140SizeCalculator().putFloat().get();
	private final GpuBuffer buffer = RenderSystem.getDevice().createBuffer(() -> "Goose UBO", 136, UBO_SIZE);

	public void update() {
		try (MemoryStack memoryStack = MemoryStack.stackPush()) {
			ByteBuffer byteBuffer = Std140Builder.onStack(memoryStack, UBO_SIZE)
					.putFloat(System.currentTimeMillis())
					.get();
			RenderSystem.getDevice().createCommandEncoder().writeToBuffer(this.buffer.slice(), byteBuffer);
		}

		RenderSystemExtension.setGooseUniformBuffer(this.buffer);
	}

	public void close() {
		this.buffer.close();
	}
}
