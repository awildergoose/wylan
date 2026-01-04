package awildgoose.wylan.entity;

import awildgoose.wylan.client.entity.SkinwalkerEntityRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class SkinwalkerEntity extends PathfinderMob {
	public static final EntityDataAccessor<SkinwalkerEntityRenderState.SkinwalkerTexture> TEXTURE =
			SynchedEntityData.defineId(SkinwalkerEntity.class,
									   EntityDataSerializer.forValueType(
											   SkinwalkerEntityRenderState.SkinwalkerTexture.STREAM_CODEC));

	public SkinwalkerEntity(EntityType<? extends SkinwalkerEntity> entityType, Level world) {
		super(entityType, world);
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 5)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.3);
	}

	@Override
	public void tick() {
		super.tick();

		var player = this.level().getNearestPlayer(this, 300.0);

		if (player != null) {
			// maybe only move sometimes, like every 0-3 seconds set a goal?
			this.lookAt(player, 10f, 5f);
			this.moveControl.setWantedPosition(player.getX(), player.getY(), player.getZ(), 1.0);
		}
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(TEXTURE, SkinwalkerEntityRenderState.SkinwalkerTexture.random());
	}

	public static boolean canSpawnHere(EntityType<? extends Mob> type, ServerLevelAccessor world,
									   EntitySpawnReason reason, BlockPos pos, RandomSource ignoredRandom) {
		return !world.getBlockState(pos.below()).isAir() &&
				world.isUnobstructed(type.create(world.getLevel(), reason));
	}
}
