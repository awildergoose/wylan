package awildgoose.wylan.entity.goal;

import awildgoose.wylan.entity.HenryEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

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
					this.dropTowardsTarget(new ItemStack(Items.MILK_BUCKET));
				} else if (suicideCounter % 5 == 0) {
					this.dropTowardsTarget(new ItemStack(Items.COOKIE));
				}

				return;
			}

			if (this.nearestLava == null)
				this.nearestLava = findNearestLava(level, this.henry.blockPosition());

			if (this.nearestLava != null) {
				this.henry.getNavigation().moveTo(nearestLava.getX(), nearestLava.getY(), nearestLava.getZ(), 1.0);

				if (this.henry.distanceToSqr(this.nearestLava.getCenter()) <= 5.0) {
					// jump!
					Vec3 look = this.henry.getLookAngle();
					double y = 0.0d;
					if (this.henry.onGround())
						y = this.getJumpPower() * 2.0;

					this.henry.setDeltaMovement(
							look.x * 0.45,
							y,
							look.z * 0.45
					);
					this.henry.hasImpulse = true;
				}
			}
		}
	}


	protected float getJumpPower() {
		return (float)this.henry.getAttributeValue(Attributes.JUMP_STRENGTH) * this.getBlockJumpFactor() + this.henry.getJumpBoostPower();
	}

	protected float getBlockJumpFactor() {
		float f = this.henry.level().getBlockState(this.henry.blockPosition()).getBlock().getJumpFactor();
		float g = this.henry.level().getBlockState(this.henry.getBlockPosBelowThatAffectsMyMovement()).getBlock().getJumpFactor();
		return f == 1.0 ? g : f;
	}

	private void dropTowardsTarget(ItemStack stack) {
		if (stack.isEmpty() || target == null) return;

		double dx = target.getX() - this.henry.getX();
		double dy = target.getEyeY() - this.henry.getEyeY();
		double dz = target.getZ() - this.henry.getZ();

		Vec3 direction = new Vec3(dx, dy, dz).normalize().scale(0.5);

		ItemEntity item = new ItemEntity(
				this.henry.level(),
				this.henry.getX(),
				this.henry.getEyeY() - 0.3,
				this.henry.getZ(),
				stack
		);

		item.setDeltaMovement(direction.x, direction.y, direction.z);
		item.setPickUpDelay(0);
		item.setThrower(this.henry);

		this.henry.level().addFreshEntity(item);
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
