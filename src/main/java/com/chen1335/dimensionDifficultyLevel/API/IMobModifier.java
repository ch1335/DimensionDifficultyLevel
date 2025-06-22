package com.chen1335.dimensionDifficultyLevel.API;

import com.chen1335.dimensionDifficultyLevel.common.mobModifier.MobModifierHandler;

public interface IMobModifier {
    void run(MobModifierHandler.Context context);

    boolean test(MobModifierHandler.Context living);
}
