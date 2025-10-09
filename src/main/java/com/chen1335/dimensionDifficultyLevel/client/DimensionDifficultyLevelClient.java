package com.chen1335.dimensionDifficultyLevel.client;

import com.chen1335.dimensionDifficultyLevel.client.gui.MenuButton;
import com.chen1335.dimensionDifficultyLevel.hooks.ICommonInventoryScreen;
import com.soy.soycheese.client.gui.CookbookScreen;
import com.soy.soycheese.inventory.CookbookMenu;
import com.soy.soycheese.registries.MenuRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class DimensionDifficultyLevelClient {
    public static void init(){
        //添加按钮
        ICommonInventoryScreen.AddButton(1, new MenuButton(1, ResourceLocation.withDefaultNamespace("eeeee"), Component.translatable("ddl.menu.button.CookbookMenu"), (pButton -> {
            CookbookMenu cookbookMenu = MenuRegistry.COOKBOOK.get().create(0, Minecraft.getInstance().player.getInventory());
            Minecraft.getInstance().player.containerMenu = cookbookMenu;
            Minecraft.getInstance().setScreen(new CookbookScreen(cookbookMenu, Minecraft.getInstance().player.getInventory(), Component.empty()));
        })));
    }

    public static Player getPlayer(){
        return Minecraft.getInstance().player;
    }
}
