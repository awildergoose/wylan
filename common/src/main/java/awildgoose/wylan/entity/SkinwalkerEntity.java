package awildgoose.wylan.entity;

import awildgoose.wylan.WylanMod;
import awildgoose.wylan.init.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class SkinwalkerEntity extends PathfinderMob {
	public static final EntityDataAccessor<SkinwalkerTexture> TEXTURE =
			SynchedEntityData.defineId(SkinwalkerEntity.class,
									   EntityDataSerializer.forValueType(
											   SkinwalkerTexture.STREAM_CODEC));

	public SkinwalkerEntity(EntityType<? extends SkinwalkerEntity> entityType, Level world) {
		super(entityType, world);
	}

	public static AttributeSupplier.Builder createDefaultAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 5)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.3);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.updateSwingTime();
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level().isClientSide)
			return;

		Player player = this.level().getNearestPlayer(this, 300.0);
		SkinwalkerTexture texture = this.getTexture();
		boolean isWylan = texture == SkinwalkerTexture.WYLAN;
		boolean isZelder = texture == SkinwalkerTexture.ZELDER || texture == SkinwalkerTexture.ZELDER_OILED;
		boolean isKat = texture == SkinwalkerTexture.KAT;
		ServerLevel level = (ServerLevel) level();

		if (player != null) {
			// maybe only move sometimes, like every 0-3 seconds set a goal?
			this.lookAt(player, 10f, 5f);
			this.moveControl.setWantedPosition(
					player.getX(), player.getY(), player.getZ(),
					isZelder ? 10.0 : 1.0
			);

			if (isWylan) {
				// adjusted from 67 (too often)
				if (this.random.nextInt(670) == 1) {
					this.setPos(player.position());
				}
			}

			if (isZelder && distanceTo(player) <= 5.0d) {
				player.hurtServer(level, level.damageSources().mobAttack(this), 1.0f);
			}
		}

		if (isZelder && !this.swinging) {
			this.swing(InteractionHand.MAIN_HAND);
		}

		if (isKat) {
			this.setInvulnerable(true);
		}

		if (isWylan) {
			Vec3 look = getLookAngle();
			Vec3 forward = new Vec3(look.x, 0, look.z).normalize();

			BlockPos blockPos = BlockPos.containing(
					position().add(forward)
			);

			BlockState state = level.getBlockState(blockPos);
			if (state.getBlock() instanceof ComposterBlock) {
				int levelValue = state.getValue(ComposterBlock.LEVEL);

				if (levelValue < 7) {
					level.setBlock(
							blockPos,
							state.setValue(ComposterBlock.LEVEL, levelValue + 1),
							3
					);
				}
			}
		}
	}

	@Override
	protected @NotNull InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);

		if (!itemStack.isEmpty() && itemStack.getItem().equals(ModItems.OIL_BUCKET.get())) {
			// this is oil!
			SkinwalkerTexture texture = this.getTexture();
			boolean isUnoiledZelder = texture == SkinwalkerTexture.ZELDER;
			boolean isOiledZelder = texture == SkinwalkerTexture.ZELDER_OILED;

			if (isUnoiledZelder) {
				if (!player.level().isClientSide) {
					this.setTexture(SkinwalkerTexture.ZELDER_OILED);
				} else {
					level().playLocalSound(player, SoundEvents.HONEY_DRINK.value(), SoundSource.PLAYERS, 1.0f, 1.0f);
				}

				return InteractionResult.SUCCESS;
			} else if (isOiledZelder) {
				if (!player.level().isClientSide) {
					var server = player.level().getServer();

					if (server != null) {
						player.teleport(new TeleportTransition(server.getLevel(WylanMod.ZELDER_ARENA),
															   new Vec3(2.5, 40, 2.5), // pos
															   Vec3.ZERO, // velocity
															   0, 0,   // rotation
															   TeleportTransition.PLAY_PORTAL_SOUND));
					} else {
						LogUtils.getLogger().error("SkinwalkerEntity::mobInteract(): Server is null somehow?");
					}
				}

				return InteractionResult.SUCCESS;
			}
		}

		// TODO: MCC + Wylan => explosion effect

		return super.mobInteract(player, interactionHand);
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

		SkinwalkerTexture randomTexture = SkinwalkerTexture.random();
		if (this.level().getBiome(this.blockPosition()).is(Biomes.PALE_GARDEN)) {
			randomTexture = SkinwalkerTexture.HUMMUS;
		}

		this.setTexture(valueInput.read("Texture", SkinwalkerTexture.CODEC).orElse(randomTexture));
	}

	public static boolean canSpawnHere(EntityType<? extends Mob> type, ServerLevelAccessor world,
									   EntitySpawnReason reason, BlockPos pos, RandomSource ignoredRandom) {
		return !world.getBlockState(pos.below()).isAir() &&
				world.isUnobstructed(type.create(world.getLevel(), reason));
	}
}
