package awildgoose.wylan.entity;

import awildgoose.wylan.ModUtils;
import awildgoose.wylan.WylanMod;
import awildgoose.wylan.init.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
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
	protected void applyGravity() {
		SkinwalkerTexture texture = this.getTexture();
		boolean isKat = texture == SkinwalkerTexture.KAT;
		if (!isKat)
			super.applyGravity();
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

		if (isKat) {
			if (player != null) {
				var targetPosition = player.position();

				this.setInvulnerable(true);
				double d = targetPosition.x + 0.5 - this.getX();
				double e = targetPosition.y + 0.1 - this.getY();
				double f = targetPosition.z + 0.5 - this.getZ();
				Vec3 vec3 = this.getDeltaMovement();
				Vec3 vec32 = vec3.add((Math.signum(d) * 0.5 - vec3.x) * 0.1F, (Math.signum(e) * 0.7F - vec3.y) * 0.1F, (Math.signum(f) * 0.5 - vec3.z) * 0.1F);
				double ascend = 0.1;
				if (targetPosition.y < getY())
					ascend *= -1;
				this.setDeltaMovement(vec32.add(0, ascend, 0));
				float g = (float)(Mth.atan2(vec32.z, vec32.x) * 180.0F / (float)Math.PI) - 90.0F;
				float h = Mth.wrapDegrees(g - this.getYRot());
				this.zza = 0.5F;
				this.setYRot(this.getYRot() + h);
			}

			return;
		}

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
	protected void dropCustomDeathLoot(ServerLevel serverLevel, DamageSource damageSource, boolean bl) {
		super.dropCustomDeathLoot(serverLevel, damageSource, bl);

		SkinwalkerTexture texture = this.getTexture();
		if (texture == SkinwalkerTexture.LETTUCE) {
			this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
			serverLevel.explode(this, this.getX(), this.getY(), this.getZ(), 3, Level.ExplosionInteraction.MOB);
		}

		Item item = switch (texture) {
			case ANIMATED -> ModItems.ANIMATED_COOKIE.get();
			case KAT -> ModItems.KAT_COOKIE.get();
			case LORDUCKIE -> ModItems.LORDUCKIE_COOKIE.get();
			case SM -> ModItems.SM_COOKIE.get();
			case WYLAN, WYLAN_EVIL -> ModItems.WYLAN_COOKIE.get();
			case ZELDER, ZELDER_OILED -> ModItems.ZELDER_COOKIE.get();
			case HUMMUS, GUAC, LETTUCE -> ModItems.WYLAN_COOKIE.get(); // TODO
		};

		this.spawnAtLocation(serverLevel, item);
	}

	@Override
	protected @NotNull InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);

		if (!itemStack.isEmpty()) {
			if (itemStack.getItem().equals(ModItems.OIL_BUCKET.get())) {
				// this is oil!
				SkinwalkerTexture texture = this.getTexture();
				boolean isUnoiledZelder = texture == SkinwalkerTexture.ZELDER;
				boolean isOiledZelder = texture == SkinwalkerTexture.ZELDER_OILED;

				if (isUnoiledZelder) {
					if (!player.level().isClientSide) {
						this.setTexture(SkinwalkerTexture.ZELDER_OILED);
					} else {
						level().playLocalSound(
								player, SoundEvents.HONEY_DRINK.value(), SoundSource.PLAYERS, 1.0f, 1.0f);
					}

					return InteractionResult.SUCCESS;
				} else if (isOiledZelder) {
					if (!player.level().isClientSide) {
						var server = player.level()
								.getServer();

						if (server != null) {
							player.teleport(new TeleportTransition(server.getLevel(WylanMod.ZELDER_ARENA),
																   new Vec3(2.5, 40, 2.5), // pos
																   Vec3.ZERO, // velocity
																   0, 0,   // rotation
																   TeleportTransition.PLAY_PORTAL_SOUND));
						} else {
							LogUtils.getLogger()
									.error("SkinwalkerEntity::mobInteract(): Server is null somehow?");
						}
					}

					return InteractionResult.SUCCESS;
				}
			} else if (itemStack.getItem().equals(ModItems.SALAD.get())) {
				// this is a salad!
				SkinwalkerTexture texture = this.getTexture();
				boolean isRegularWylan = texture == SkinwalkerTexture.WYLAN;

				if (isRegularWylan) {
					if (!player.level().isClientSide) {
						this.setTexture(SkinwalkerTexture.WYLAN_EVIL);
					} else {
						level().playLocalSound(player, SoundEvents.FOX_EAT, SoundSource.PLAYERS, 1.0f, 1.0f);

						Vec3[] positions = ModUtils.getRingPositions(getX(), getY(), getZ(), getRotationVector().x, 16,
																  2.0f, 1.0f, 1.0f, 1.0f);

						for (var position : positions)
						{
							var x = position.x;
							var y = position.y;
							var z = position.z;
							level().addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.0, 0.0);
							level().addParticle(ParticleTypes.FLAME, x, y, z, 0.0, 0.0, 0.0);
						}
					}

					return InteractionResult.SUCCESS;
				}
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
