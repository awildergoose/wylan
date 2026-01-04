package awildgoose.wylan;

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
}
