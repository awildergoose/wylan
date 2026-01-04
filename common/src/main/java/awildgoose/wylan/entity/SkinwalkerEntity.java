package awildgoose.wylan.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class SkinwalkerEntity extends PathfinderMob {
	public static final EntityDataAccessor<SkinwalkerTexture> TEXTURE =
			SynchedEntityData.defineId(SkinwalkerEntity.class,
									   EntityDataSerializer.forValueType(
											   SkinwalkerTexture.STREAM_CODEC));

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

		if (this.level().isClientSide)
			return;

		if (this.firstTick)
			this.setTexture(SkinwalkerTexture.random());

		var player = this.level().getNearestPlayer(this, 300.0);

		SkinwalkerTexture texture = this.getTexture();
		boolean isZelder = texture == SkinwalkerTexture.ZELDER;
		boolean isKat = texture == SkinwalkerTexture.KAT;

		if (player != null) {
			// maybe only move sometimes, like every 0-3 seconds set a goal?
			this.lookAt(player, 10f, 5f);
			this.moveControl.setWantedPosition(player.getX(), player.getY(), player.getZ(), isZelder ? 10.0 : 1.0);

			// this is all your fault >:( /j
			if (this.random.nextInt(67) == 1) {
				if (texture == SkinwalkerTexture.WYLAN) {
					this.setPos(player.position());
				}
			}
		}

		if (isZelder && this.tickCount % 20 == 0) {
			this.swing(InteractionHand.MAIN_HAND);
		}

		if (isKat) {
			this.setInvulnerable(true);
		}
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(TEXTURE, SkinwalkerTexture.ANIMATED);
	}

	public SkinwalkerTexture getTexture() {
		return this.entityData.get(TEXTURE);
	}

	public void setTexture(SkinwalkerTexture texture) {
		this.entityData.set(TEXTURE, texture);
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput valueOutput) {
		super.addAdditionalSaveData(valueOutput);
		valueOutput.store("Texture", SkinwalkerTexture.CODEC, this.getTexture());
	}

	@Override
	protected void readAdditionalSaveData(ValueInput valueInput) {
		super.readAdditionalSaveData(valueInput);
		this.setTexture(valueInput.read("Texture", SkinwalkerTexture.CODEC).orElse(
				SkinwalkerTexture.ANIMATED));
	}

	public static boolean canSpawnHere(EntityType<? extends Mob> type, ServerLevelAccessor world,
									   EntitySpawnReason reason, BlockPos pos, RandomSource ignoredRandom) {
		return !world.getBlockState(pos.below()).isAir() &&
				world.isUnobstructed(type.create(world.getLevel(), reason));
	}
}
