package awildgoose.wylan.client;

import awildgoose.wylan.ScreenshakeInstance;
import awildgoose.wylan.payloads.ScreenshakeS2CPayload;

public class ClientPayloadHandlers {
	public static void handleScreenShakePacket(ScreenshakeS2CPayload payload) {
		ScreenshakeHandler.addScreenshake(new ScreenshakeInstance(
				payload.duration(),
				payload.startingStrength(),
				payload.endingStrength(),
				payload.falloffDistance(),
				payload.center()
		));
	}
}
