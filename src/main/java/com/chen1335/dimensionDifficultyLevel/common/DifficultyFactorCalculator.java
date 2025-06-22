package com.chen1335.dimensionDifficultyLevel.common;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.List;

public class DifficultyFactorCalculator {

    /**
     * 难度系数计算主方法
     */
    public static DifficultyContext calculate(LivingEntity targetEntity, List<ServerPlayer> nearbyPlayers, ServerLevel serverLevel, List<Structure> structures) {
        int experienceLevel = 0;
        for (ServerPlayer nearbyPlayer : nearbyPlayers) {
            experienceLevel += nearbyPlayer.experienceLevel;
        }
        return new DifficultyContext(experienceLevel);
    }


    public static class DifficultyContext{

        public final int experienceLevel;

        public DifficultyContext(int experienceLevel) {
            this.experienceLevel = experienceLevel;
        }
    }
}
