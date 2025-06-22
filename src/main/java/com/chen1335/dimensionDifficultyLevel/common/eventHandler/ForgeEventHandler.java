package com.chen1335.dimensionDifficultyLevel.common.eventHandler;

import com.chen1335.dimensionDifficultyLevel.API.Capabilities;
import com.chen1335.dimensionDifficultyLevel.DimensionDifficultyLevel;
import com.chen1335.dimensionDifficultyLevel.utils.MobSpawnHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = DimensionDifficultyLevel.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventHandler {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.type == TickEvent.Type.PLAYER) {
            event.player.getCapability(Capabilities.PLAYER_STATUE).ifPresent(playerStatue -> playerStatue.tick(event.player));
        }
    }

    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        event.getEntity().getCapability(Capabilities.PLAYER_STATUE).ifPresent(playerStatue -> {
            playerStatue.currentDimension = event.getTo().location();
        });
    }

    @SubscribeEvent
    public static void ona(PlayerInteractEvent.RightClickItem event) {
        if (!event.getEntity().level().isClientSide) {
            Level level = event.getLevel();
            List<LivingEntity> entities = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                entities.add(EntityType.ZOMBIFIED_PIGLIN.create(level));
            }
            if (event.getHand() == InteractionHand.MAIN_HAND) {
                MobSpawnHelper.spawnMobs(entities, level, new AABB(-50, 64, -50, 50, 74, 50));
            }
        }
    }
}
