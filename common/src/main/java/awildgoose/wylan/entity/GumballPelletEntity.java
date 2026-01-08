package awildgoose.wylan.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class GumballPelletEntity extends LivingEntity {
	private int age = 0;

	public GumballPelletEntity(EntityType<? extends GumballPelletEntity> entityType, Level level) {
		super(entityType, level);
	}

	public static AttributeSupplier.Builder createDefaultAttributes() {
		return LivingEntity.createLivingAttributes();
	}

	@Override
	public @NotNull HumanoidArm getMainArm() {
		return HumanoidArm.RIGHT;
	}

	//region stolen from mojang's Projectile
	public Vec3 getMovementToShoot(double xDir, double yDir, double zDir, float velocity, float inaccuracy) {
		return new Vec3(xDir, yDir, zDir)
				.normalize()
				.add(this.random.triangle(0.0, 0.0172275 * inaccuracy), this.random.triangle(0.0, 0.0172275 * inaccuracy), this.random.triangle(0.0, 0.0172275 * inaccuracy))
				.scale(velocity);
	}

	public void shoot(double xDir, double yDir, double zDir, float velocity, float inaccuracy) {
		Vec3 vec3 = this.getMovementToShoot(xDir, yDir, zDir, velocity, inaccuracy);
		this.setDeltaMovement(vec3);
		this.hasImpulse = true;
		double i = vec3.horizontalDistance();
		this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * 180.0F / (float)Math.PI));
		this.setXRot((float)(Mth.atan2(vec3.y, i) * 180.0F / (float)Math.PI));
		this.yRotO = this.getYRot();
		this.xRotO = this.getXRot();
	}

	protected static float lerpRotation(float f, float g) {
		while (g - f < -180.0F) {
			f -= 360.0F;
		}

		while (g - f >= 180.0F) {
			f += 360.0F;
		}

		return Mth.lerp(0.2F, f, g);
	}

	protected void updateRotation() {
		Vec3 vec3 = this.getDeltaMovement();
		double d0 = vec3.horizontalDistance();
		this.setXRot(lerpRotation(this.xRotO, (float)(Mth.atan2(vec3.y, d0) * 180.0 / (float) Math.PI)));
		this.setYRot(lerpRotation(this.yRotO, (float)(Mth.atan2(vec3.x, vec3.z) * 180.0 / (float) Math.PI)));
	}
	//endregion

	protected boolean canHitEntity(Entity entity) {
		return true;
	}

	@Override
	public boolean isInvulnerableTo(ServerLevel serverLevel, DamageSource damageSource) {
		return true;
	}

	@Override
	protected double getDefaultGravity() {
		return 0.03;
	}

	@Override
	public void tick() {
		this.age++;
		this.applyGravity();
		this.setDeltaMovement(this.getDeltaMovement().scale(0.99));

		HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
		Vec3 vec3;

		if (hitresult.getType() != HitResult.Type.MISS) {
			vec3 = hitresult.getLocation();
		} else {
			vec3 = this.position().add(this.getDeltaMovement());
		}

		this.setPos(vec3);
		this.updateRotation();
		this.applyEffectsFromBlocks();

		if (hitresult.getType() != HitResult.Type.MISS && this.isAlive()) {
			if (hitresult instanceof EntityHitResult entityHitResult) {
				Entity entity = entityHitResult.getEntity();

				if (entity.level() instanceof ServerLevel serverLevel) {
					entity.hurtServer(serverLevel, this.damageSources().mobAttack(this), 3);
				}
			}

			if (!this.level().isClientSide) {
				this.level().broadcastEntityEvent(this, (byte)69);
				this.discard();
			}
		}

		if (this.level().isClientSide) {
			if (age % 5 == 0)
				this.level().addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
		}
	}

	@Override
	public void handleEntityEvent(byte b) {
		if (b == 69) {
			this.level().addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
		} else {
			super.handleEntityEvent(b);
		}
	}
}
