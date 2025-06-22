package com.chen1335.dimensionDifficultyLevel.utils;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.List;

public class StructureHelper {
    public static List<Structure> getStructuresAt(ServerLevel serverLevel, BlockPos blockPos) {
        ImmutableList.Builder<Structure> builder = ImmutableList.builder();
        StructureManager structureManager = serverLevel.structureManager();
        for (Structure structure : structureManager.getAllStructuresAt(blockPos).keySet()) {
            if (structureManager.getStructureWithPieceAt(blockPos, structure).isValid()) {
                builder.add(structure);
            }
        }
        return builder.build();
    }

    public static ResourceKey<Structure> getStructureKey(Level serverLevel, Structure structure) {
        return serverLevel.registryAccess().registryOrThrow(Registries.STRUCTURE).getResourceKey(structure).get();
    }

    public static ResourceLocation getStructureResourceLocation(Level serverLevel, Structure structure) {
        return serverLevel.registryAccess().registryOrThrow(Registries.STRUCTURE).getKey(structure);
    }
}
