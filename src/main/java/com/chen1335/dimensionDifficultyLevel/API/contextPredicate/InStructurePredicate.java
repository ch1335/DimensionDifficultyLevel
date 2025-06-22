package com.chen1335.dimensionDifficultyLevel.API.contextPredicate;

import com.chen1335.dimensionDifficultyLevel.common.mobModifier.MobModifierHandler;
import com.chen1335.dimensionDifficultyLevel.utils.StructureHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.Structure;

public class InStructurePredicate implements IContextPredicate {

    private final ResourceLocation structureResourceLocation;

    public InStructurePredicate(ResourceLocation structureResourceLocation) {
        this.structureResourceLocation = structureResourceLocation;
    }

    @Override
    public boolean test(MobModifierHandler.Context context) {
        ServerLevel serverLevel = context.serverLevel();
        for (Structure inStructure : context.inStructures()) {
            if (StructureHelper.getStructureResourceLocation(serverLevel, inStructure).equals(structureResourceLocation)) {
                return true;
            }
        }
        return false;
    }
}
