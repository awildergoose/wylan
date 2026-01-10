package awildgoose.wylan.entity;

import awildgoose.wylan.ScreenshakeInstance;
import awildgoose.wylan.ccb.ClientCommonBridge;
import awildgoose.wylan.init.ModEntityTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animatable.processing.AnimationTest;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ZelderBossEntity extends Monster implements GeoEntity, RangedAttackMob {
	private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

	protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("animation.model.idle");
	private final ServerBossEvent bossEvent = (ServerBossEvent)new ServerBossEvent(
			this.getDisplayName(), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS
	).setDarkenScreen(true).setCreateWorldFog(true).setPlayBossMusic(true);

	private int age = 0;

	public ZelderBossEntity(EntityType<? extends ZelderBossEntity> entityType, Level world) {
		super(entityType, world);
		this.moveControl = new FlyingMoveControl(this, 10, false);
		this.setHealth(this.getMaxHealth());
		this.xpReward = 50;
		this.setNoGravity(true);
	}

	//region animation
	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>("Idle", 5, this::animController));
	}

	private PlayState animController(AnimationTest<GeoAnimatable> animTest) {
		return animTest.setAndContinue(IDLE_ANIM);
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.geoCache;
	}
	//endregion

	@Override
	public void performRangedAttack(LivingEntity target, float power) {
		GumballPelletEntity pellet = new GumballPelletEntity(ModEntityTypes.GUMBALL_PELLET.get(), this.level());
		pellet.setPos(
				this.getX(),
				this.getEyeY(),
				this.getZ()
		);

		double dx = target.getX() - this.getX();
		double dy = target.getEyeY() - pellet.getEyeY();
		double dz = target.getZ() - this.getZ();

		pellet.shoot(
				dx, dy, dz,
				1.5F, 0.0F
		);

		this.level().addFreshEntity(pellet);
	}

	@Override
	protected void customServerAiStep(ServerLevel serverLevel) {
		if (this.tickCount % 20 == 0) {
			this.heal(1.0F);
		}

		this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
	}

	@Override
	public void aiStep() {
		Vec3 vec3 = this.getDeltaMovement().multiply(1.0, 0.6, 1.0);
		Level level = this.level();

		if (level.getFluidState(blockPosition().below()).is(FluidTags.LAVA)) {
			vec3 = vec3.add(new Vec3(0, 0.5, 0));
		}

		this.setDeltaMovement(vec3);

		LivingEntity target = getTarget();
		if (target != null) {
			this.lookAt(target, 30.0f, 30.0f);
			this.moveControl.setWantedPosition(target.getX(), target.getY(), target.getZ(), 1.0);
		} else {
			setTarget(level.getNearestPlayer(this, 100.0));
		}

		super.aiStep();

		if (level.isClientSide) {
			float radius = 1.5f;
			float speed = 2.0f;
			Vec3[] positions = getRingPositions(12, speed, radius, radius, radius);

			for (Vec3 pos : positions) {
				level().addParticle(ParticleTypes.FLAME,
									pos.x, pos.y, pos.z,
									0, 0, 0);

				if (this.age % 5 == 0)
					ClientCommonBridge.i.addScreenshake(
							new ScreenshakeInstance(
									5, 0.0f, 0.2f, 3.0f, pos));
			}
		}
	}

	@Override
	public void tick() {
		super.tick();
		this.age++;

		Level level = this.level();

		if (level.isClientSide && age % 2 == 0) {
			// feet and body
			Vec3 a = getFootPosition(true);
			Vec3 b = getFootPosition(false);
			Vec3 c = position().add(0, 1.0, 0);

			level.addParticle(
					ParticleTypes.SOUL_FIRE_FLAME,
					a.x, a.y, a.z,
					0.0, 0.0, 0.0);
			level.addParticle(
					ParticleTypes.SOUL_FIRE_FLAME,
					b.x, b.y, b.z,
					0.0, 0.0, 0.0);
			level.addParticle(
					ParticleTypes.SOUL_FIRE_FLAME,
					c.x, c.y, c.z,
					0.0, 0.0, 0.0);
		}
	}

	public Vec3[] getRingPositions(int numberOfParticles, float speed, float radiusX, float radiusY, float radiusZ) {
		Vec3[] positions = new Vec3[numberOfParticles];
		double yaw = Math.toRadians(-this.getRotationVector().x);
		double animationRotation = (System.currentTimeMillis() % (long)(10000 / speed)) / (10000.0 / speed) * 2 * Math.PI;

		for (int i = 0; i < numberOfParticles; i++) {
			double angle = (2 * Math.PI * i / numberOfParticles) + animationRotation;

			double localX = Math.cos(angle) * radiusX;
			double localY = Math.sin(angle) * radiusY;
			double localZ = Math.sin(angle * 1.5) * radiusZ;

			double rotatedX = localX * Math.cos(yaw) - localZ * Math.sin(yaw);
			double rotatedZ = localX * Math.sin(yaw) + localZ * Math.cos(yaw);

			positions[i] = new Vec3(
					this.getX() + rotatedX,
					this.getY() + localY + 0.75,
					this.getZ() + rotatedZ
			);
		}

		return positions;
	}

	public Vec3 getFootPosition(boolean side) {
		double footOffsetX = (side ? 5.0 : -5.0) / 16.0;

		double yaw = Math.toRadians(-this.getRotationVector().x);
		double rotatedX = footOffsetX * Math.cos(yaw) * Math.sin(yaw);
		double rotatedZ = footOffsetX * Math.sin(yaw) * Math.cos(yaw);

		return new Vec3(
				this.getX() + rotatedX,
				this.getY(),
				this.getZ() + rotatedZ
		);
	}

	//region generic stuff
	@Override
	public void makeStuckInBlock(BlockState blockState, Vec3 vec3) {}

	@Override
	protected void registerGoals() {
		// 1 ranged attack per second
		this.goalSelector.addGoal(0, new RangedAttackGoal(this, 1.0, 20, 20.0F));
	}

	@Override
	protected @NotNull PathNavigation createNavigation(Level level) {
		FlyingPathNavigation flyingPathNavigation = new FlyingPathNavigation(this, level);
		flyingPathNavigation.setCanOpenDoors(false);
		flyingPathNavigation.setCanFloat(true);
		return flyingPathNavigation;
	}

	public static AttributeSupplier.Builder createDefaultAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 300.0)
				.add(Attributes.MOVEMENT_SPEED, 1.0)
				.add(Attributes.FLYING_SPEED, 0.2F)
				.add(Attributes.FOLLOW_RANGE, 100.0)
				.add(Attributes.ARMOR, 4.0);
	}

	@Override
	public boolean fireImmune() {
		return true;
	}

	@Override
	public boolean isInvulnerableTo(ServerLevel serverLevel, DamageSource damageSource) {
		if (damageSource.is(DamageTypeTags.IS_FIRE))
			return true;
		if (damageSource.is(DamageTypeTags.IS_FALL))
			return true;
		//noinspection RedundantIfStatement
		if (super.isInvulnerableTo(serverLevel, damageSource))
			return true;

		// SHIELD DEFENSE CHANCE!!
		return false;
	}

	@Override
	public void checkDespawn() {
		if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
			this.discard();
		} else {
			this.noActionTime = 0;
		}
	}

	@Override
	protected void readAdditionalSaveData(ValueInput valueInput) {
		super.readAdditionalSaveData(valueInput);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
	}

	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(this.getDisplayName());
	}

	@Override
	public void startSeenByPlayer(ServerPlayer serverPlayer) {
		super.startSeenByPlayer(serverPlayer);
		this.bossEvent.addPlayer(serverPlayer);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer serverPlayer) {
		super.stopSeenByPlayer(serverPlayer);
		this.bossEvent.removePlayer(serverPlayer);
	}
	//endregion
}
