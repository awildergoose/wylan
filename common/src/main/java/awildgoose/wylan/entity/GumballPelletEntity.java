package awildgoose.wylan.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class GumballPelletEntity extends LivingEntity {
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
}
