package com.chen1335.dimensionDifficultyLevel.hooks;

import com.chen1335.dimensionDifficultyLevel.API.Capabilities;
import com.chen1335.dimensionDifficultyLevel.client.gui.MenuButton;
import com.chen1335.dimensionDifficultyLevel.common.capability.PlayerStatue;
import com.soy.soycheese.client.gui.CookbookScreen;
import com.soy.soycheese.inventory.CookbookMenu;
import com.soy.soycheese.registries.MenuRegistry;
import io.netty.util.collection.IntObjectHashMap;
import io.netty.util.collection.IntObjectMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class InventoryScreenHooks {
    public static ResourceLocation MENU = ResourceLocation.fromNamespaceAndPath("ddl", "textures/gui/menu.png");

    public static IntObjectMap<ResourceLocation> NUM_MAP = new IntObjectHashMap<>();

    public static void render(GuiGraphics guiGraphics, int leftPos, int topPos, int mouseX, int mouseY, float partialTick, boolean isCreativeMod, CallbackInfo ci) {
        @NotNull LazyOptional<PlayerStatue> optional = Minecraft.getInstance().player.getCapability(Capabilities.PLAYER_STATUE);
        if (optional.isPresent()) {
            PlayerStatue playerStatue = optional.orElse(null);
            Font font = Minecraft.getInstance().font;
            guiGraphics.blit(MENU, leftPos - 64, topPos, 0, 0, 65, 166, 262, 230);
            guiGraphics.drawString(font, Component.translatable("ddl.dimension.difficult"), leftPos - 64, topPos + 10, 16777215);

            guiGraphics.drawString(font, Component.translatable("ddl.dimension." + playerStatue.currentDimension.getNamespace() + "." + playerStatue.currentDimension.getPath()), leftPos - 64, topPos, 16777215);

            ResourceLocation resourceLocation = NUM_MAP.get(playerStatue.getCurrentDifficulty());
            if (resourceLocation != null) {
                guiGraphics.blit(resourceLocation, leftPos - 52, topPos + 12, 0, 0, 16, 16, 16, 16);
            }
        }
    }

    static {
        for (int i = 0; i <= 9; i++) {
            NUM_MAP.put(i, ResourceLocation.fromNamespaceAndPath("ddl", "textures/gui/num/num" + i + ".png"));
        }
    }

    public static void init(Screen screen, int leftPos, int topPos) {
        ICommonInventoryScreen commonInventoryScreen = ICommonInventoryScreen.cast(screen);
        updateScreenState(commonInventoryScreen, leftPos, topPos);
    }

    public static void updateScreenState(ICommonInventoryScreen screen, int leftPos, int topPos) {
        ICommonInventoryScreen.BUTTON_MAP.forEach((id, menuButton) -> {
            menuButton.updatePosition(leftPos, topPos);
            screen.ddl$$addMenuButton(id, menuButton);
        });

        screen.ddl$$addMenuButton(1, new MenuButton(1, ResourceLocation.withDefaultNamespace("eeeee"), (pButton -> {
            CookbookMenu cookbookMenu = MenuRegistry.COOKBOOK.get().create(0, Minecraft.getInstance().player.getInventory());
            Minecraft.getInstance().player.containerMenu = cookbookMenu;
            Minecraft.getInstance().setScreen(new CookbookScreen(cookbookMenu, Minecraft.getInstance().player.getInventory(), Component.empty()));

        })));
    }
}
