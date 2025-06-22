package com.chen1335.dimensionDifficultyLevel.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class MobSpawnHelper {
    public static void spawnMobs(List<LivingEntity> entities, Level level, AABB aabb) {

        for (LivingEntity entity : entities) {
            int xLength = (int) (aabb.maxX - aabb.minX);
            int zLength = (int) (aabb.maxZ - aabb.minZ);
            int YLength = (int) (aabb.maxY - aabb.minY);
            boolean success = false;
            int tryCount = 0;

            do {
                int randomX = (int) (aabb.minX + level.random.nextInt(xLength));
                int randomZ = (int) (aabb.minZ + level.random.nextInt(zLength));
                for (int i = 0; i <= YLength; i++) {
                    int y = (int) (aabb.minY + i);
                    BlockPos blockPos = new BlockPos(randomX, y, randomZ);
                    if (NaturalSpawner.isSpawnPositionOk(SpawnPlacements.getPlacementType(entity.getType()), level, blockPos, entity.getType())) {
                        entity.setPos(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
                        level.addFreshEntity(entity);
                        success = true;
                        break;
                    }
                }

                if (!success) {
                    tryCount++;
                }
            } while (tryCount < 10 && !success);
        }
    }
}
