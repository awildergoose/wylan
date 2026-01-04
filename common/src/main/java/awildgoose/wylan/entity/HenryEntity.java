package awildgoose.wylan.entity;

import awildgoose.wylan.entity.goal.HenryMilkCookiesSuicideGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animatable.processing.AnimationTest;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class HenryEntity extends PathfinderMob implements GeoEntity {
	protected static final RawAnimation RUN_ANIM = RawAnimation.begin().thenLoop("animation.henry.new_run");
	protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("animation.henry.run_idle");

	private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

	public HenryEntity(EntityType<? extends HenryEntity> entityType, Level world) {
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
		this.goalSelector.addGoal(0, new HenryMilkCookiesSuicideGoal(this));
		this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 8.0f));
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>("Running", 5, this::runAnimController));
	}

	private PlayState runAnimController(AnimationTest<GeoAnimatable> animTest) {
		if (animTest.isMoving())
			return animTest.setAndContinue(RUN_ANIM);

		return animTest.setAndContinue(IDLE_ANIM);
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.geoCache;
	}
}
