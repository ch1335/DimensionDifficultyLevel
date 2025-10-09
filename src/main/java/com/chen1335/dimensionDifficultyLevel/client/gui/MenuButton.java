package com.chen1335.dimensionDifficultyLevel.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class MenuButton extends ImageButton {
    public static ResourceLocation MENU_BUTTON_BACK = ResourceLocation.fromNamespaceAndPath("ddl", "textures/gui/button/menu_hover.png");

    private final ResourceLocation icon;
    private final Component hoverName;
    private final int id;

    //按钮id，按钮图片位置，按钮悬停名，按钮点击行为
    public MenuButton(int id, ResourceLocation pResourceLocation,Component hoverName, Button.OnPress pOnPress) {
        super(0, 0, 24, 24, 0, 0, 24, MENU_BUTTON_BACK, 24, 48, pOnPress);
        this.id = id;
        icon = pResourceLocation;
        this.hoverName = hoverName;
    }

    public void updatePosition(int leftPos, int topPos) {
        setX(getXById(id, leftPos));
        setY(getYById(id, topPos));
    }

    public int getXById(int id, int leftPos) {
        return leftPos - 64 + (id % 2 == 0 ? 37 : 10);
    }

    public int getYById(int id, int topPos) {
        int i = (id + 1) / 2;
        return topPos + 50 + (i - 1) * 27;
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.renderWidget(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTexture(pGuiGraphics, this.icon, this.getX(), this.getY(), 0, 0, 0, 24, 24, 24, 24);

        if (isHovered) {
            pGuiGraphics.renderTooltip(Minecraft.getInstance().font, hoverName, pMouseX, pMouseY);
        }
    }
}
