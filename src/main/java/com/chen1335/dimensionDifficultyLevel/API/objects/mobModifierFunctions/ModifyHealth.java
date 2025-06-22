package com.chen1335.dimensionDifficultyLevel.API.objects.mobModifierFunctions;

import com.chen1335.dimensionDifficultyLevel.API.contextPredicate.IContextPredicate;
import com.chen1335.dimensionDifficultyLevel.common.DifficultyFactorCalculator;
import com.chen1335.dimensionDifficultyLevel.API.objects.predicates.IsZombiePredicate;
import com.chen1335.dimensionDifficultyLevel.common.mobModifier.MobModifierBase;
import com.chen1335.dimensionDifficultyLevel.common.mobModifier.MobModifierHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.List;

public class ModifyHealth extends MobModifierBase {
    public static final ModifyHealth INSTANCE = new ModifyHealth(List.of(IsZombiePredicate.INSTANCE));

    public ModifyHealth(List<IContextPredicate> entityPredicates) {
        super(entityPredicates);
    }

    @Override
    public void run(MobModifierHandler.Context context) {
        LivingEntity living = context.livingEntity();
        DifficultyFactorCalculator.DifficultyContext difficultyContext = context.difficultyContext();
        living.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(new AttributeModifier("test", difficultyContext.experienceLevel, AttributeModifier.Operation.ADDITION));
        living.setHealth(living.getMaxHealth());
    }
}
