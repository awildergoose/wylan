package awildgoose.wylan.payloads;

import awildgoose.wylan.WylanMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record ScreenshakeS2CPayload(int duration, float startingStrength, float endingStrength, float falloffDistance
		, Vec3 center) implements CustomPacketPayload {
	public static final ResourceLocation PAYLOAD_ID = ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID, "screenshake");
	public static final CustomPacketPayload.Type<ScreenshakeS2CPayload> ID = new CustomPacketPayload.Type<>(PAYLOAD_ID);
	public static final StreamCodec<FriendlyByteBuf, ScreenshakeS2CPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.INT, ScreenshakeS2CPayload::duration,
			ByteBufCodecs.FLOAT, ScreenshakeS2CPayload::startingStrength,
			ByteBufCodecs.FLOAT, ScreenshakeS2CPayload::endingStrength,
			ByteBufCodecs.FLOAT, ScreenshakeS2CPayload::falloffDistance,
			Vec3.STREAM_CODEC, ScreenshakeS2CPayload::center,
			ScreenshakeS2CPayload::new
	);

	@Override
	public @NotNull Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
