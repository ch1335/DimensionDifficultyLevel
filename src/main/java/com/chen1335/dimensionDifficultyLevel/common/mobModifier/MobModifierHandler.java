package com.chen1335.dimensionDifficultyLevel.common.mobModifier;

import com.chen1335.dimensionDifficultyLevel.API.Capabilities;
import com.chen1335.dimensionDifficultyLevel.API.contextPredicate.IContextPredicate;
import com.chen1335.dimensionDifficultyLevel.API.IMobModifier;
import com.chen1335.dimensionDifficultyLevel.DimensionDifficultyLevel;
import com.chen1335.dimensionDifficultyLevel.common.DifficultyFactorCalculator;
import com.chen1335.dimensionDifficultyLevel.common.capability.LevelMobModifier;
import com.chen1335.dimensionDifficultyLevel.utils.StructureHelper;
import com.google.common.collect.ImmutableList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = DimensionDifficultyLevel.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MobModifierHandler {
    public static final List<IContextPredicate> COMMON_PREDICATE = new ArrayList<>();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void EntityJoinLevelEvent(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide && !event.isCanceled()) {
            Entity entity = event.getEntity();
            if (entity instanceof LivingEntity living && living.getType() != EntityType.PLAYER && !living.getTags().contains("ddl_modified")) {
                ServerLevel serverLevel = (ServerLevel) event.getLevel();
                List<ServerPlayer> players = getNearbyPlayers(living);
                List<Structure> structures = StructureHelper.getStructuresAt(serverLevel, entity.blockPosition());
                DifficultyFactorCalculator.DifficultyContext difficultyContext = DifficultyFactorCalculator.calculate(living, players, serverLevel,structures);
                Context context = new Context(living, serverLevel, players, difficultyContext, structures);

                if (isModifiableEntity(context)) {
                    @NotNull LazyOptional<LevelMobModifier> levelMobModifierLazyOptional = serverLevel.getCapability(Capabilities.LEVEL_MOB_MODIFIER);
                    if (levelMobModifierLazyOptional.isPresent()) {
                        LevelMobModifier levelMobModifier = levelMobModifierLazyOptional.orElse(null);
                        if (levelMobModifier.isModifiableEntity(context)) {
                            for (List<IMobModifier> value : levelMobModifier.getModifyPriorityListMap().values()) {
                                for (IMobModifier iMobModifier : value) {
                                    if (iMobModifier.test(context)) {
                                        iMobModifier.run(context);
                                        if (living.isRemoved()) {
                                            event.setCanceled(true);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        living.addTag("ddl_modified");
                    }
                }
            }
        }
    }

    public record Context(
            LivingEntity livingEntity,
            ServerLevel serverLevel,
            List<ServerPlayer> players,
            DifficultyFactorCalculator.DifficultyContext difficultyContext,
            List<Structure> inStructures
    ) {
    }

    private static List<ServerPlayer> getNearbyPlayers(LivingEntity living) {
        ImmutableList.Builder<ServerPlayer> builder = ImmutableList.builder();
        for (Player player : living.level().players()) {
            if (!player.isSpectator()) {
                if (horizontalDistanceSquared(player, living) <= 16384.0D) {
                    builder.add((ServerPlayer) player);
                }
            }
        }
        return builder.build();
    }

    private static double horizontalDistanceSquared(Entity entity1, Entity entity2) {
        double f = entity1.getX() - entity2.getX();
        double f2 = entity1.getZ() - entity2.getZ();
        return f * f + f2 * f2;
    }

    private static boolean isModifiableEntity(Context context) {
        for (IContextPredicate entityPredicate : COMMON_PREDICATE) {
            if (!entityPredicate.test(context)) {
                return false;
            }
        }
        return true;
    }

    public enum ModifyPriority {
        HIGH, NORMAL, LOW;
    }
}
