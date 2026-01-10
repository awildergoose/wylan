package awildgoose.wylan.client;

import awildgoose.wylan.ccb.ClientCommonBridge;
import awildgoose.wylan.client.init.ModEntityModelLayers;
import awildgoose.wylan.client.init.ModRendering;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class WylanModClient {
	public static void init() {
		ClientCommonBridge.i = new ClientCommonBridgeImpl();
		ModEntityModelLayers.init();
		ModRendering.init();
		ScreenshakeHandler.init();
	}
}
