package awildgoose.wylan.client;

import awildgoose.wylan.ScreenshakeInstance;
import awildgoose.wylan.ccb.IClientCommonBridge;

public class ClientCommonBridgeImpl implements IClientCommonBridge {
	@Override
	public void addScreenshake(ScreenshakeInstance instance) {
		ScreenshakeHandler.addScreenshake(instance);
	}
}
