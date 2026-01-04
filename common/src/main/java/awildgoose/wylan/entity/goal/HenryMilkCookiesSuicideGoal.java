package awildgoose.wylan.entity.goal;

import awildgoose.wylan.ModUtils;
import awildgoose.wylan.entity.HenryEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class HenryMilkCookiesSuicideGoal extends Goal {
	final HenryEntity henry;
	int suicideCounter;
	boolean givingMilkAndCookies;
	@Nullable Player target;
	@Nullable BlockPos nearestLava;

	public HenryMilkCookiesSuicideGoal(HenryEntity henry) {
		super();

		this.henry = henry;
		this.suicideCounter = 20 * 3;
		this.givingMilkAndCookies = false;
		this.target = null;
		this.nearestLava = null;
		this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP, Flag.TARGET));
	}

	@Override
	public boolean canUse() {
		return true;
	}

	@Override
	public void tick() {
		Level level = this.henry.level();

		if (!givingMilkAndCookies) {
			if (target == null)
				this.target = level.getNearestPlayer(this.henry, 50.0d);

			if (target != null) {
				this.henry.getNavigation().moveTo(target, 1);

				if (target.distanceTo(this.henry) <= 3.0) {
					this.henry.lookAt(target, 10f, 5f);
					givingMilkAndCookies = true;
				}
			}
		} else {
			if (suicideCounter > 0) {
				suicideCounter--;

				if (suicideCounter % 20 == 0) {
					ModUtils.dropTowardsEntity(level, new ItemStack(Items.MILK_BUCKET), this.henry.getEyePosition(), this.target);
				} else if (suicideCounter % 5 == 0) {
					ModUtils.dropTowardsEntity(level, new ItemStack(Items.COOKIE), this.henry.getEyePosition(),
											   this.target);
				}

				return;
			}

			if (this.nearestLava == null)
				this.nearestLava = findNearestLava(level, this.henry.blockPosition());

			if (this.nearestLava != null) {
				this.henry.getMoveControl().setWantedPosition(nearestLava.getX(), nearestLava.getY(), nearestLava.getZ(), 1.0);
//				this.henry.getNavigation().moveTo(nearestLava.getX(), nearestLava.getY(), nearestLava.getZ(), 1.0);

				if (this.henry.distanceToSqr(this.nearestLava.getCenter()) <= 5.0) {
					// jump!
					Vec3 look = this.henry.getLookAngle();

					this.henry.setDeltaMovement(
							look.x * 0.45,
							0.0,
							look.z * 0.45
					);
					this.henry.hasImpulse = true;

					if (this.henry.onGround())
						this.henry.jumpFromGround();
				}
			}
		}
	}


	private static @Nullable BlockPos findNearestLava(Level level, BlockPos origin) {
		final int radius = 50;
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

		BlockPos bestPos = null;
		double bestDistSq = Double.MAX_VALUE;

		for (int dx = -radius; dx <= radius; dx++) {
			for (int dy = -radius; dy <= radius; dy++) {
				for (int dz = -radius; dz <= radius; dz++) {
					pos.set(
							origin.getX() + dx,
							origin.getY() + dy,
							origin.getZ() + dz
					);

					if (!level.isLoaded(pos)) continue;

					if (level.getBlockState(pos).is(Blocks.LAVA)) {
						double distSq = pos.distSqr(origin);
						if (distSq < bestDistSq) {
							bestDistSq = distSq;
							bestPos = pos.immutable();
						}
					}
				}
			}
		}

		return bestPos;
	}
}
