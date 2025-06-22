package com.chen1335.dimensionDifficultyLevel;

import com.chen1335.dimensionDifficultyLevel.API.objects.mobModifierFunctions.ModifyHealth;
import com.chen1335.dimensionDifficultyLevel.client.gui.MenuButton;
import com.chen1335.dimensionDifficultyLevel.common.LevelMobModifierRegisterHolder;
import com.chen1335.dimensionDifficultyLevel.common.mobModifier.MobModifierHandler;
import com.chen1335.dimensionDifficultyLevel.hooks.ICommonInventoryScreen;
import com.chen1335.dimensionDifficultyLevel.network.ModMessages;
import com.mojang.logging.LogUtils;
import com.soy.soycheese.client.gui.CookbookScreen;
import com.soy.soycheese.inventory.CookbookMenu;
import com.soy.soycheese.registries.MenuRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(DimensionDifficultyLevel.MODID)
public class DimensionDifficultyLevel {
    public static final String MODID = "ddl";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    private static MinecraftServer minecraftServer;

    public DimensionDifficultyLevel(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGHEST, this::onServerAboutToStart);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
    }

    public static ResourceLocation id(String s) {
        return ResourceLocation.fromNamespaceAndPath(MODID, s);
    }

    public void onServerAboutToStart(ServerAboutToStartEvent event) {
        minecraftServer = event.getServer();

        LevelMobModifierRegisterHolder.registerModifier(ServerLevel.OVERWORLD, MobModifierHandler.ModifyPriority.HIGH, ModifyHealth.INSTANCE);
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        ModMessages.register();
    }

    public void clientSetup(FMLClientSetupEvent event) {
        ICommonInventoryScreen.AddButton(1, new MenuButton(1, ResourceLocation.withDefaultNamespace("eeeee"), (pButton -> {
            CookbookMenu cookbookMenu = MenuRegistry.COOKBOOK.get().create(0, Minecraft.getInstance().player.getInventory());
            Minecraft.getInstance().player.containerMenu = cookbookMenu;
            Minecraft.getInstance().setScreen(new CookbookScreen(cookbookMenu, Minecraft.getInstance().player.getInventory(), Component.empty()));
        })));
    }

    public static MinecraftServer getMinecraftServer() {
        return minecraftServer;
    }
}
