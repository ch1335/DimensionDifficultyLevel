package com.chen1335.dimensionDifficultyLevel.hooks;

import com.chen1335.dimensionDifficultyLevel.client.gui.MenuButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;

import java.util.HashMap;
import java.util.Map;

public interface ICommonInventoryScreen {
    Map<Integer,MenuButton> BUTTON_MAP = new HashMap<>();
    void ddl$$addMenuButton(int id, MenuButton menuButton);

    void ddl$updateMenuButtons(InventoryScreen inventoryScreen);
    static ICommonInventoryScreen cast(Screen screen) {
        return (ICommonInventoryScreen) screen;
    }

    static void AddButton(int id,MenuButton menuButton){
        BUTTON_MAP.put(id,menuButton);
    }
}
