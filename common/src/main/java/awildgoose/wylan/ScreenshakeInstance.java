package awildgoose.wylan;

import net.minecraft.world.phys.Vec3;

public class ScreenshakeInstance {
	public final int duration;
	public final float startingStrength;
	public final float endingStrength;
	public final float falloffDistance;
	public final Vec3 center;

	public int progress = 0;
	public boolean expired = false;

	public ScreenshakeInstance(int duration, float startingStrength, float endingStrength, float falloffDistance, Vec3 center) {
		this.duration = duration;
		this.startingStrength = startingStrength;
		this.endingStrength = endingStrength;
		this.falloffDistance = falloffDistance;
		this.center = center;
	}

	public void tick() {
		if (progress < duration) {
			progress++;
			if (progress == duration) expired = true;
		}
	}

	public boolean isExpired() {
		return expired;
	}
}
