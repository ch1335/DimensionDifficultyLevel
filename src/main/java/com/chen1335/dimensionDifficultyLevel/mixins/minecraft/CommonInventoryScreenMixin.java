package com.chen1335.dimensionDifficultyLevel.mixins.minecraft;

import com.chen1335.dimensionDifficultyLevel.client.gui.MenuButton;
import com.chen1335.dimensionDifficultyLevel.hooks.ICommonInventoryScreen;
import io.netty.util.collection.IntObjectHashMap;
import io.netty.util.collection.IntObjectMap;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin({InventoryScreen.class, CreativeModeInventoryScreen.class})
public abstract class CommonInventoryScreenMixin extends EffectRenderingInventoryScreen<AbstractContainerMenu> implements ICommonInventoryScreen {
    @Unique
    private final IntObjectMap<MenuButton> ddl$menuButtons = new IntObjectHashMap<>();

    public CommonInventoryScreenMixin(AbstractContainerMenu p_98701_, Inventory p_98702_, Component p_98703_) {
        super(p_98701_, p_98702_, p_98703_);
    }


    @Override
    public void ddl$$addMenuButton(int id, MenuButton menuButton) {
        ddl$menuButtons.put(id, menuButton);
        addRenderableWidget(menuButton);
    }

    @Override
    public void ddl$updateMenuButtons(InventoryScreen inventoryScreen) {
        ddl$menuButtons.forEach((id, menuButton) -> {
            menuButton.setX(menuButton.getXById(id, leftPos));
            menuButton.visible = !inventoryScreen.getRecipeBookComponent().isVisible();
        });
    }
}
