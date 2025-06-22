package com.chen1335.dimensionDifficultyLevel.common.mobModifier;

import com.chen1335.dimensionDifficultyLevel.API.IMobModifier;
import com.chen1335.dimensionDifficultyLevel.API.contextPredicate.IContextPredicate;

import java.util.List;

public abstract class MobModifierBase implements IMobModifier {
    public final List<IContextPredicate> entityPredicates;

    public MobModifierBase(List<IContextPredicate> entityPredicates) {
        this.entityPredicates = entityPredicates;
    }

    public List<IContextPredicate> getEntityPredicates() {
        return entityPredicates;
    }

    @Override
    public boolean test(MobModifierHandler.Context context) {
        for (IContextPredicate entityPredicate : entityPredicates) {
            if (!entityPredicate.test(context)) {
                return false;
            }
        }
        return true;
    }

    protected void cancelSpawn(MobModifierHandler.Context context) {
        context.livingEntity().discard();
    }
}
