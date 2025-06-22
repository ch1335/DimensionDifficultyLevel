package com.chen1335.dimensionDifficultyLevel.common.capability;

import com.chen1335.dimensionDifficultyLevel.API.Capabilities;
import com.chen1335.dimensionDifficultyLevel.API.contextPredicate.IContextPredicate;
import com.chen1335.dimensionDifficultyLevel.API.IMobModifier;
import com.chen1335.dimensionDifficultyLevel.common.mobModifier.MobModifierHandler;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class LevelMobModifier implements ICapabilitySerializable<CompoundTag> {
    private final LazyOptional<LevelMobModifier> holder = LazyOptional.of(() -> this);

    private final List<IContextPredicate> entityPredicates = new ArrayList<>();
    private final LinkedHashMap<MobModifierHandler.ModifyPriority, List<IMobModifier>> modifyPriorityListMap = new LinkedHashMap<>();

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return Capabilities.LEVEL_MOB_MODIFIER.orEmpty(cap, holder);
    }

    public LinkedHashMap<MobModifierHandler.ModifyPriority, List<IMobModifier>> getModifyPriorityListMap() {
        return modifyPriorityListMap;
    }

    public void addMobModifier(MobModifierHandler.ModifyPriority priority, IMobModifier mobModifier) {
        modifyPriorityListMap.get(priority).add(mobModifier);
    }

    public boolean isModifiableEntity(MobModifierHandler.Context context) {
        for (IContextPredicate entityPredicate : entityPredicates) {
            if (!entityPredicate.test(context)) {
                return false;
            }
        }
        return true;
    }

    public void clearMobModifier() {
        for (List<IMobModifier> value : modifyPriorityListMap.values()) {
            value.clear();
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        return new CompoundTag();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }

    {
        for (MobModifierHandler.ModifyPriority priority : MobModifierHandler.ModifyPriority.values()) {
            modifyPriorityListMap.put(priority, new ArrayList<>());
        }
    }

    public ImmutableList<IContextPredicate> getEntityPredicates() {
        return ImmutableList.copyOf(entityPredicates);
    }

    public void addEntityPredicate(IContextPredicate entityPredicate) {
        entityPredicates.add(entityPredicate);
    }
}
