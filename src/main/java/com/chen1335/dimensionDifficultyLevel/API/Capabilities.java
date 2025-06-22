package com.chen1335.dimensionDifficultyLevel.API;

import com.chen1335.dimensionDifficultyLevel.DimensionDifficultyLevel;
import com.chen1335.dimensionDifficultyLevel.common.capability.LevelMobModifier;
import com.chen1335.dimensionDifficultyLevel.common.capability.PlayerStatue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DimensionDifficultyLevel.MODID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Capabilities {
    public static Capability<LevelMobModifier> LEVEL_MOB_MODIFIER = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static Capability<PlayerStatue> PLAYER_STATUE = CapabilityManager.get(new CapabilityToken<>() {
    });

    @SubscribeEvent
    public static void RegisterCapabilitiesEvent(RegisterCapabilitiesEvent event) {
        event.register(LevelMobModifier.class);
        event.register(PlayerStatue.class);
    }

    @SubscribeEvent
    public static void AttachLevelCapabilitiesEvent(AttachCapabilitiesEvent<Level> event) {
        Level level = event.getObject();
        if (!event.getObject().isClientSide) {
            if (!level.getCapability(Capabilities.LEVEL_MOB_MODIFIER).isPresent()) {
                event.addCapability(DimensionDifficultyLevel.id("level_mob_modifier"),new LevelMobModifier());
            }
        }
    }

    @SubscribeEvent
    public static void AttachEntityCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof Player player) {
            if (!player.getCapability(PLAYER_STATUE).isPresent()) {
                event.addCapability(DimensionDifficultyLevel.id("player_statue"),new PlayerStatue());
            }
        }
    }
}
