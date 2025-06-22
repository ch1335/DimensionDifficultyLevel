package com.chen1335.dimensionDifficultyLevel.common;

import com.chen1335.dimensionDifficultyLevel.API.Capabilities;
import com.chen1335.dimensionDifficultyLevel.API.contextPredicate.IContextPredicate;
import com.chen1335.dimensionDifficultyLevel.API.IMobModifier;
import com.chen1335.dimensionDifficultyLevel.DimensionDifficultyLevel;
import com.chen1335.dimensionDifficultyLevel.common.mobModifier.MobModifierHandler;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = DimensionDifficultyLevel.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LevelMobModifierRegisterHolder {
    private static final Multimap<ResourceKey<Level>, Holder> REGISTER_HOLDER = HashMultimap.create();
    private static final Multimap<ResourceKey<Level>, IContextPredicate> PREDICATE_HOLDER = HashMultimap.create();
    private static final List<Holder> COMMON_REGISTER_HOLDER = new ArrayList<>();

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        Level level = (Level) event.getLevel();
        if (!level.isClientSide) {
            level.getCapability(Capabilities.LEVEL_MOB_MODIFIER).ifPresent(levelMobModifier -> {
                for (Holder holder : COMMON_REGISTER_HOLDER) {
                    levelMobModifier.addMobModifier(holder.priority, holder.mobModifier);
                }
                for (Holder holder : REGISTER_HOLDER.get(level.dimension())) {
                    levelMobModifier.addMobModifier(holder.priority, holder.mobModifier);
                }

                for (IContextPredicate entityPredicate : PREDICATE_HOLDER.get(level.dimension())) {
                    levelMobModifier.addEntityPredicate(entityPredicate);
                }
            });
        }
    }

    public static void registerModifier(ResourceKey<Level> levelResourceKey, MobModifierHandler.ModifyPriority priority, IMobModifier mobModifier) {
        REGISTER_HOLDER.put(levelResourceKey, new Holder(priority, mobModifier));
    }

    public static void registerModifier(MobModifierHandler.ModifyPriority priority, IMobModifier mobModifier) {
        COMMON_REGISTER_HOLDER.add(new Holder(priority, mobModifier));
    }


    public static void registerCanModifyPredicate(ResourceKey<Level> levelResourceKey, IContextPredicate predicate) {
        PREDICATE_HOLDER.put(levelResourceKey, predicate);
    }

    public static void registerCanModifyPredicate(IContextPredicate predicate) {
        MobModifierHandler.COMMON_PREDICATE.add(predicate);
    }

    public record Holder(MobModifierHandler.ModifyPriority priority, IMobModifier mobModifier) {

    }
}
