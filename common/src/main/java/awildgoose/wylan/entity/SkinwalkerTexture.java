package awildgoose.wylan.entity;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntFunction;

public enum SkinwalkerTexture implements StringRepresentable {
	ANIMATED(0),
	KAT(1),
	LORDUCKIE(2),
	SM(3),
	WYLAN(4),
	ZELDER(5),
	HUMMUS(6),
	LETTUCE(7);

	public static final IntFunction<SkinwalkerTexture> BY_ID = ByIdMap.continuous(SkinwalkerTexture::id, values(),
																				  ByIdMap.OutOfBoundsStrategy.ZERO);
	public static final Codec<SkinwalkerTexture> CODEC =
			StringRepresentable.fromEnum(SkinwalkerTexture::values);
	public static final StreamCodec<ByteBuf, SkinwalkerTexture> STREAM_CODEC = ByteBufCodecs.idMapper(
			BY_ID, SkinwalkerTexture::id);
	private final int id;

	SkinwalkerTexture(final int j) {
		this.id = j;
	}

	public int id() {
		return this.id;
	}

	public static SkinwalkerTexture random() {
		SkinwalkerTexture[] values = SkinwalkerTexture.class.getEnumConstants();
		return values[ThreadLocalRandom.current()
				.nextInt(values.length)];
	}

	@Override
	public @NotNull String getSerializedName() {
		return String.valueOf(this.id);
	}
}
