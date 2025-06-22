package com.chen1335.dimensionDifficultyLevel.mixins.minecraft;

import com.chen1335.dimensionDifficultyLevel.hooks.ICommonInventoryScreen;
import com.chen1335.dimensionDifficultyLevel.hooks.InventoryScreenHooks;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({InventoryScreen.class})
public abstract class InventoryScreenMixin extends EffectRenderingInventoryScreen<InventoryMenu> {
    @Shadow
    public abstract RecipeBookComponent getRecipeBookComponent();

    public InventoryScreenMixin(InventoryMenu p_98701_, Inventory p_98702_, Component p_98703_) {
        super(p_98701_, p_98702_, p_98703_);
    }

    @Inject(method = "init", at = @At("RETURN"))
    public void init(CallbackInfo ci) {
        InventoryScreenHooks.init(this, this.leftPos, this.topPos);
    }

    @Inject(method = "lambda$init$0", at = @At(value = "RETURN"))
    public void onClickRecipeBook(CallbackInfo ci) {
        ICommonInventoryScreen.cast(this).ddl$updateMenuButtons((InventoryScreen)(Object)this);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/InventoryScreen;renderBackground(Lnet/minecraft/client/gui/GuiGraphics;)V", shift = At.Shift.AFTER))
    private void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (!getRecipeBookComponent().isVisible()) {
            InventoryScreenHooks.render(guiGraphics, this.leftPos, this.topPos, mouseX, mouseY, partialTick, false, ci);
        }
    }
}
