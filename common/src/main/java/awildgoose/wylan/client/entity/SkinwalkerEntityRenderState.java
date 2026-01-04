package awildgoose.wylan.client.entity;

import io.netty.buffer.ByteBuf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntFunction;

@Environment(EnvType.CLIENT)
public class SkinwalkerEntityRenderState extends HumanoidRenderState {
	public SkinwalkerTexture texture;

	public enum SkinwalkerTexture {
		ANIMATED(0),
		KAT(1),
		LORDUCKIE(2),
		SM(3),
		WYLAN(4),
		ZELDER(5);

		public static final IntFunction<SkinwalkerTexture> BY_ID = ByIdMap.continuous(SkinwalkerTexture::id, values(),
																	  ByIdMap.OutOfBoundsStrategy.ZERO);
		public static final StreamCodec<ByteBuf, SkinwalkerTexture> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, SkinwalkerTexture::id);
		private final int id;

		SkinwalkerTexture(final int j) {
			this.id = j;
		}

		public int id() {
			return this.id;
		}

		public static SkinwalkerTexture random() {
			SkinwalkerTexture[] values = SkinwalkerTexture.class.getEnumConstants();
			return values[ThreadLocalRandom.current().nextInt(values.length)];
		}
	}
}
