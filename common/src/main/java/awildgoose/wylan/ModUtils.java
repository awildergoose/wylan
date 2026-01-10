package awildgoose.wylan;

import awildgoose.wylan.payloads.ScreenshakeS2CPayload;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ModUtils {
	public static void dropTowardsEntity(Level level, ItemStack stack, Vec3 eyePos, Entity target) {
		if (stack.isEmpty() || target == null) return;

		double dx = target.getX() - eyePos.x;
		double dy = target.getEyeY() - eyePos.y;
		double dz = target.getZ() - eyePos.z;

		Vec3 direction = new Vec3(dx, dy, dz).normalize().scale(0.5);

		ItemEntity item = new ItemEntity(
				level,
				eyePos.x,
				eyePos.y - 0.3,
				eyePos.z,
				stack
		);

		item.setDeltaMovement(direction.x, direction.y, direction.z);
		item.setPickUpDelay(0);
		level.addFreshEntity(item);
	}

	private static ScreenshakeS2CPayload makeScreenshakePacket(ScreenshakeInstance instance) {
		return new ScreenshakeS2CPayload(
				instance.duration,
				instance.startingStrength, instance.endingStrength,
				instance.falloffDistance, instance.center);
	}

	public static void broadcastScreenshake(ServerLevel level, ScreenshakeInstance instance) {
		broadcastPayload(level, makeScreenshakePacket(instance));
	}

	public static void sendScreenshake(ServerPlayer player, ScreenshakeInstance instance) {
		sendS2CPayload(player, makeScreenshakePacket(instance));
	}

	public static ClientboundCustomPayloadPacket makeS2CPacket(CustomPacketPayload payload) {
		return new ClientboundCustomPayloadPacket(payload);
	}

	public static void sendS2CPacket(ServerPlayer player, ClientboundCustomPayloadPacket packet) {
		player.connection.send(packet);
	}

	public static void sendS2CPayload(ServerPlayer player, CustomPacketPayload payload) {
		sendS2CPacket(player, new ClientboundCustomPayloadPacket(payload));
	}

	public static void broadcastPayload(ServerLevel level, CustomPacketPayload payload) {
		var packet = makeS2CPacket(payload);

		for (ServerPlayer player : level.players())
			sendS2CPacket(player, packet);
	}
}
