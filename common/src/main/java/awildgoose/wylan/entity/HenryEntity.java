package awildgoose.wylan.entity;

import awildgoose.wylan.entity.goal.HenryMilkCookiesSuicideGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
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

	public static AttributeSupplier.Builder createDefaultAttributes() {
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

	public static boolean canSpawnHere(EntityType<? extends Mob> type, ServerLevelAccessor world,
									   EntitySpawnReason reason, BlockPos pos, RandomSource ignoredRandom) {
		return world.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) &&
				world.getBrightness(LightLayer.SKY, pos) > 8 &&
				world.isUnobstructed(type.create(world.getLevel(), reason));
	}

}
