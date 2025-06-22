package com.chen1335.dimensionDifficultyLevel.mixins.minecraft;

import com.chen1335.dimensionDifficultyLevel.hooks.InventoryScreenHooks;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModInventoryScreenMixin extends EffectRenderingInventoryScreen<CreativeModeInventoryScreen.ItemPickerMenu> {
    public CreativeModInventoryScreenMixin(CreativeModeInventoryScreen.ItemPickerMenu p_98701_, Inventory p_98702_, Component p_98703_) {
        super(p_98701_, p_98702_, p_98703_);
    }

    @Inject(method = "init", at = @At("RETURN"))
    public void init(CallbackInfo ci) {
        InventoryScreenHooks.init(this, this.leftPos, this.topPos);
    }

    @Inject(method = "render", at = @At("RETURN"))
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        InventoryScreenHooks.render(guiGraphics,this.leftPos,this.topPos, mouseX, mouseY, partialTick,true, ci);
    }
}
