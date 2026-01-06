package awildgoose.wylan.entity;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
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

	public ZelderBossEntity(EntityType<? extends ZelderBossEntity> entityType, Level world) {
		super(entityType, world);
		this.moveControl = new FlyingMoveControl(this, 10, false);
		this.setHealth(this.getMaxHealth());
		this.xpReward = 50;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new RangedAttackGoal(this, 1.0, 40, 20.0F));
		this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 30.0F));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	@Override
	protected @NotNull PathNavigation createNavigation(Level level) {
		FlyingPathNavigation flyingPathNavigation = new FlyingPathNavigation(this, level);
		flyingPathNavigation.setCanOpenDoors(false);
		flyingPathNavigation.setCanFloat(true);
		return flyingPathNavigation;
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 300.0)
				.add(Attributes.MOVEMENT_SPEED, 0.6)
				.add(Attributes.FLYING_SPEED, 0.2F)
				.add(Attributes.FOLLOW_RANGE, 100.0)
				.add(Attributes.ARMOR, 4.0);
	}

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

	@Override
	public void performRangedAttack(LivingEntity livingEntity, float f) {

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
	protected void customServerAiStep(ServerLevel serverLevel) {
		this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
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
}
