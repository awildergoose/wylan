package awildgoose.wylan.entity;

import awildgoose.wylan.client.entity.SkinwalkerEntityRenderState;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

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
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new LookAtPlayerGoal(this, Player.class, 50));
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(TEXTURE, SkinwalkerEntityRenderState.SkinwalkerTexture.random());
	}
}
