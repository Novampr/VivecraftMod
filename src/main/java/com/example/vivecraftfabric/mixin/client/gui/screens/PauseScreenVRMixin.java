package com.example.vivecraftfabric.mixin.client.gui.screens;

import com.example.vivecraftfabric.DataHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.social.SocialInteractionsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.vivecraft.gameplay.screenhandlers.KeyboardHandler;
import org.vivecraft.gui.settings.GuiQuickCommandsInGame;
import org.vivecraft.settings.AutoCalibration;
import org.vivecraft.settings.VRHotkeys;
import org.vivecraft.settings.VRSettings;
import org.vivecraft.utils.external.jkatvr;

@Mixin(PauseScreen.class)
public abstract class PauseScreenVRMixin extends Screen {

    private DataHolder dataholder = DataHolder.getInstance();

    protected PauseScreenVRMixin(Component component) {
        super(component);
    }


    @Inject(at =  @At("TAIL"), method = "init")
    public void addInit(CallbackInfo ci) {
        if (!Minecraft.getInstance().isMultiplayerServer()) {
            this.addRenderableWidget(new Button(this.width / 2 - 102, this.height / 4 + 72 + -16, 98, 20, new TranslatableComponent("vivecraft.gui.chat"), (p) ->
            {
                this.minecraft.setScreen(new ChatScreen(""));
                if (DataHolder.getInstance().vrSettings.autoOpenKeyboard)
                    KeyboardHandler.setOverlayShowing(true);
            }));
        } else {
            this.addRenderableWidget(new Button(this.width / 2 - 102, this.height / 4 + 72 + -16, 46, 20, new TranslatableComponent("vivecraft.gui.chat"), (p) ->
            {
                this.minecraft.setScreen(new ChatScreen(""));
            }));
            this.addRenderableWidget(new Button(this.width / 2 - 102 + 48, this.height / 4 + 72 + -16, 46, 20, new TranslatableComponent("vivecraft.gui.social"), (p) ->
            {
                this.minecraft.setScreen(new SocialInteractionsScreen());
            }));
        }

        this.addRenderableWidget(new Button(this.width / 2 + 4, this.height / 4 + 72 + -16, 98, 20, new TranslatableComponent("vivecraft.gui.commands"), (p) ->
        {
            this.minecraft.setScreen(new GuiQuickCommandsInGame(this));
            this.init();
        }));
        this.addRenderableWidget(new Button(this.width / 2 - 102, this.height / 4 + 120 + -16, 49, 20, new TranslatableComponent("vivecraft.gui.overlay"), (p) ->
        {
            this.minecraft.options.renderDebug = !this.minecraft.options.renderDebug;
            this.minecraft.setScreen((Screen) null);
        }));
        this.addRenderableWidget(new Button(this.width / 2 - 52, this.height / 4 + 120 + -16, 49, 20, new TranslatableComponent("vivecraft.gui.profiler"), (p) ->
        {
            if (!this.minecraft.options.renderDebug) this.minecraft.options.renderDebugCharts = false;
            this.minecraft.options.renderDebugCharts = !this.minecraft.options.renderDebugCharts;
            this.minecraft.options.renderDebug = this.minecraft.options.renderDebugCharts;
            this.minecraft.setScreen((Screen) null);
        }));
        this.addRenderableWidget(new Button(this.width / 2 + 4, this.height / 4 + 120 + -16, 98, 20, new TranslatableComponent("vivecraft.gui.screenshot"), (p) ->
        {
            this.minecraft.setScreen((Screen) null);
            DataHolder.getInstance().grabScreenShot = true;
        }));

        if (!DataHolder.getInstance().vrSettings.seated) {
            this.addRenderableWidget(new Button(this.width / 2 - 102, this.height / 4 + 144 + -16, 98, 20, new TranslatableComponent("vivecraft.gui.calibrateheight"), (p) ->
            {
                AutoCalibration.calibrateManual();
                DataHolder.getInstance().vrSettings.saveOptions();
                this.minecraft.setScreen((Screen) null);
            }));
        }

        if (DataHolder.katvr) {
            this.addRenderableWidget(new Button(this.width / 2 + 106, this.height / 4 + 144 + -16, 98, 20, new TranslatableComponent("vivecraft.gui.alignkatwalk"), (p) ->
            {
                jkatvr.resetYaw(DataHolder.getInstance().vrPlayer.vrdata_room_pre.hmd.getYaw());
                this.minecraft.setScreen((Screen) null);
            }));
        }

        if (!DataHolder.getInstance().vrSettings.seated || DataHolder.getInstance().vrSettings.displayMirrorMode == VRSettings.MirrorMode.THIRD_PERSON || DataHolder.getInstance().vrSettings.displayMirrorMode == VRSettings.MirrorMode.MIXED_REALITY) {
            this.addRenderableWidget(new Button(this.width / 2 + 4, this.height / 4 + 144 + -16, 98, 20, new TranslatableComponent("vivecraft.gui.movethirdpersoncam"), (p) ->
            {
                if (!VRHotkeys.isMovingThirdPersonCam()) {
                    VRHotkeys.startMovingThirdPersonCam(1, VRHotkeys.Triggerer.MENUBUTTON);
                } else if (VRHotkeys.getMovingThirdPersonCamTriggerer() == VRHotkeys.Triggerer.MENUBUTTON) {
                    VRHotkeys.stopMovingThirdPersonCam();
                    DataHolder.getInstance().vrSettings.saveOptions();
                }
            }));
        }
    }

    @Redirect(method = "createPauseMenu", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/PauseScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;", ordinal = 3))
    private GuiEventListener remove(PauseScreen instance, GuiEventListener guiEventListener) {
        return null;
    }
    @Redirect(method = "createPauseMenu", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/PauseScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;", ordinal = 4))
    private GuiEventListener remove2(PauseScreen instance, GuiEventListener guiEventListener) {
        return null;
    }
    @ModifyConstant(method = "createPauseMenu", constant = @Constant(intValue = 120), remap = false)
    private int moveDown(int constant) {
        return 168;
    }
}