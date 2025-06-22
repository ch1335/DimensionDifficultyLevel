package com.chen1335.dimensionDifficultyLevel.hooks;

import com.chen1335.dimensionDifficultyLevel.API.Capabilities;
import com.chen1335.dimensionDifficultyLevel.common.capability.PlayerStatue;
import com.mojang.blaze3d.vertex.PoseStack;
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
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            PlayerStatue playerStatue = optional.orElse(null);
            Font font = Minecraft.getInstance().font;
            guiGraphics.blit(MENU, leftPos - 64, topPos, 0, 0, 65, 166, 262, 230);
            Component dimName = Component.translatable("ddl.dimension." + playerStatue.currentDimension.getNamespace() + "." + playerStatue.currentDimension.getPath());
            Component eliteEnemyChance = Component.literal(String.format("%.0f%%",playerStatue.eliteEnemyChance));
            Component specialItemDropChance = Component.literal(String.format("%.0f%%",playerStatue.specialItemDropChance));
            guiGraphics.drawString(font, dimName, leftPos - 16 - font.width(dimName) / 2, topPos+8, 16777215);
            guiGraphics.drawString(font, eliteEnemyChance, leftPos - 16 - font.width(eliteEnemyChance) / 2, topPos+19, 16777215);
            guiGraphics.drawString(font, specialItemDropChance, leftPos - 16 - font.width(specialItemDropChance) / 2, topPos+29, 16777215);

            ResourceLocation resourceLocation = NUM_MAP.get(playerStatue.getCurrentDifficulty());
            if (resourceLocation != null) {
                guiGraphics.blit(resourceLocation, leftPos - 52, topPos + 12, 0, 0, 16, 16, 16, 16);
            }
            poseStack.popPose();
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
        if (leftPos == 0 && topPos == 0) {
            return;
        }
        ICommonInventoryScreen.BUTTON_MAP.forEach((id, menuButton) -> {
            menuButton.updatePosition(leftPos, topPos);
            screen.ddl$$addMenuButton(id, menuButton);
        });
    }


}
