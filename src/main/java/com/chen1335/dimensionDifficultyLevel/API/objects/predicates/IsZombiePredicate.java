package com.chen1335.dimensionDifficultyLevel.API.objects.predicates;

import com.chen1335.dimensionDifficultyLevel.API.contextPredicate.IContextPredicate;
import com.chen1335.dimensionDifficultyLevel.common.mobModifier.MobModifierHandler;
import net.minecraft.world.entity.EntityType;

public class IsZombiePredicate implements IContextPredicate {
    public static final IsZombiePredicate INSTANCE = new IsZombiePredicate();

    @Override
    public boolean test(MobModifierHandler.Context entity) {
        return entity.livingEntity().getType() == EntityType.ZOMBIE;
    }
}
