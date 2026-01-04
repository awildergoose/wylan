package awildgoose.wylan.client;

import awildgoose.wylan.init.ModEntityModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class WylanModClient {
	public static void init() {
		ModEntityModelLayers.init();
	}
}
