package awildgoose.wylan.client;

import awildgoose.wylan.ScreenshakeInstance;
import dev.architectury.event.events.client.ClientTickEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.util.Mth;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ScreenshakeHandler {
	private static final List<ScreenshakeInstance> INSTANCES = new ArrayList<>();
	private static float intensity = 0f;

	public static float getIntensity() {
		return intensity;
	}

	public static void init() {
		ClientTickEvent.CLIENT_POST.register(client -> {
			if (client.level == null || client.isPaused()) return;

			Camera camera = client.gameRenderer.getMainCamera();
			float total = 0f;
			Iterator<ScreenshakeInstance> it = INSTANCES.iterator();
			while (it.hasNext()) {
				ScreenshakeInstance instance = it.next();
				instance.tick();
				total += getStrength(instance, camera);
				if (instance.isExpired()) it.remove();
			}
			intensity = total;
		});
	}

	public static float getStrength(ScreenshakeInstance instance, Camera camera) {
		if (instance.expired) return 0f;

		float t = instance.progress / (float) instance.duration;
		float strength = Mth.lerp(t, instance.startingStrength, instance.endingStrength);

		if (instance.falloffDistance > 0 && instance.center != null) {
			float dist = (float) camera.position().distanceTo(instance.center);
			if (dist > instance.falloffDistance) return 0f;
			float falloff = 1f - (dist / instance.falloffDistance);
			strength *= falloff;
		}

		return strength;
	}

	public static void addScreenshake(ScreenshakeInstance instance) {
		INSTANCES.add(instance);
	}
}
