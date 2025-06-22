package com.chen1335.dimensionDifficultyLevel.common;

import com.chen1335.dimensionDifficultyLevel.utils.StructureHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.List;
import java.util.Objects;

public class PersonalDifficultyCalculator {
    /**
     * 个人难度系数计算主方法
     */
    public static DifficultyFactorCalculator.DifficultyContext calculate(ServerPlayer serverPlayer, ServerLevel serverLevel, List<Structure> structures) {
        int difficulty = serverPlayer.experienceLevel;
        for (Structure structure : structures) {
            if (Objects.equals(StructureHelper.getStructureResourceLocation(serverLevel, structure), ResourceLocation.withDefaultNamespace("village_desert"))) {
                difficulty += 5;
            }
        }
        return new DifficultyFactorCalculator.DifficultyContext(difficulty);
    }
}
