package org.vivecraft.client_vr.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;
import org.vivecraft.client.gui.framework.TwoHandedScreen;
import org.vivecraft.client.utils.Utils;
import org.vivecraft.client_vr.provider.InputSimulator;

public class GuiKeyboard extends TwoHandedScreen {
    private boolean isShift = false;
    private long airTypingWarningTime;

    public void init() {
        String s = this.dataholder.vrSettings.keyboardKeys;
        String s1 = this.dataholder.vrSettings.keyboardKeysShift;
        this.clearWidgets();

        if (this.isShift) {
            s = s1;
        }

        int i = 13;
        int j = 4;
        int k = 32;
        int l = 2;
        int i1 = 25;
        double d0 = (double) s.length() / (double) i;

        if (Math.floor(d0) == d0) {
            j = (int) d0;
        } else {
            j = (int) (d0 + 1.0D);
        }

        for (int j1 = 0; j1 < j; ++j1) {
            for (int k1 = 0; k1 < i; ++k1) {
                int l1 = j1 * i + k1;
                char c0 = ' ';

                if (l1 < s.length()) {
                    c0 = s.charAt(l1);
                }

                String s2 = String.valueOf(c0);
                final int code = l1 < this.dataholder.vrSettings.keyboardCodes.length ? this.dataholder.vrSettings.keyboardCodes[l1] : GLFW.GLFW_KEY_UNKNOWN;
                Button button = new Button(k + k1 * (i1 + l), k + j1 * (20 + l), i1, 20, Component.literal(s2), (p) ->
                {
                    InputSimulator.pressKeyForBind(code);
                    InputSimulator.releaseKeyForBind(code);
                    InputSimulator.typeChars(s2);
                });
                this.addRenderableWidget(button);
            }
        }

        this.addRenderableWidget(new Button(0, k + 3 * (20 + l), 30, 20, Component.literal("Shift"), (p) ->
        {
            this.setShift(!this.isShift);
        }));
        this.addRenderableWidget(new Button(k + 4 * (i1 + l), k + j * (20 + l), 5 * (i1 + l), 20, Component.literal(" "), (p) ->
        {
            InputSimulator.pressKeyForBind(GLFW.GLFW_KEY_SPACE);
            InputSimulator.releaseKeyForBind(GLFW.GLFW_KEY_SPACE);
            InputSimulator.typeChars(" ");
        }));
        this.addRenderableWidget(new Button(i * (i1 + l) + k, k, 35, 20, Component.literal("BKSP"), (p) ->
        {
            InputSimulator.pressKey(GLFW.GLFW_KEY_BACKSPACE);
            InputSimulator.releaseKey(GLFW.GLFW_KEY_BACKSPACE);
        }));
        this.addRenderableWidget(new Button(i * (i1 + l) + k, k + 2 * (20 + l), 35, 20, Component.literal("ENTER"), (p) ->
        {
            InputSimulator.pressKey(GLFW.GLFW_KEY_ENTER);
            InputSimulator.releaseKey(GLFW.GLFW_KEY_ENTER);
        }));
        this.addRenderableWidget(new Button(0, k + 20 + l, 30, 20, Component.literal("TAB"), (p) ->
        {
            InputSimulator.pressKey(GLFW.GLFW_KEY_TAB);
            InputSimulator.releaseKey(GLFW.GLFW_KEY_TAB);
        }));
        this.addRenderableWidget(new Button(0, k, 30, 20, Component.literal("ESC"), (p) ->
        {
            InputSimulator.pressKey(GLFW.GLFW_KEY_ESCAPE);
            InputSimulator.releaseKey(GLFW.GLFW_KEY_ESCAPE);
        }));
        this.addRenderableWidget(new Button((i - 1) * (i1 + l) + k, k + j * (20 + l), i1, 20, Component.literal("\u2191"), (p) ->
        {
            InputSimulator.pressKey(GLFW.GLFW_KEY_UP);
            InputSimulator.releaseKey(GLFW.GLFW_KEY_UP);
        }));
        this.addRenderableWidget(new Button((i - 1) * (i1 + l) + k, k + (j + 1) * (20 + l), i1, 20, Component.literal("\u2193"), (p) ->
        {
            InputSimulator.pressKey(GLFW.GLFW_KEY_DOWN);
            InputSimulator.releaseKey(GLFW.GLFW_KEY_DOWN);
        }));
        this.addRenderableWidget(new Button((i - 2) * (i1 + l) + k, k + (j + 1) * (20 + l), i1, 20, Component.literal("\u2190"), (p) ->
        {
            InputSimulator.pressKey(GLFW.GLFW_KEY_LEFT);
            InputSimulator.releaseKey(GLFW.GLFW_KEY_LEFT);
        }));
        this.addRenderableWidget(new Button(i * (i1 + l) + k, k + (j + 1) * (20 + l), i1, 20, Component.literal("\u2192"), (p) ->
        {
            InputSimulator.pressKey(GLFW.GLFW_KEY_RIGHT);
            InputSimulator.releaseKey(GLFW.GLFW_KEY_RIGHT);
        }));
        this.addRenderableWidget(new Button(k, k + -1 * (20 + l), 35, 20, Component.literal("CUT"), (p) ->
        {
            InputSimulator.pressKey(GLFW.GLFW_KEY_LEFT_CONTROL);
            InputSimulator.pressKey(GLFW.GLFW_KEY_X);
            InputSimulator.releaseKey(GLFW.GLFW_KEY_X);
            InputSimulator.releaseKey(GLFW.GLFW_KEY_LEFT_CONTROL);
        }));
        this.addRenderableWidget(new Button(35 + l + k, k + -1 * (20 + l), 35, 20, Component.literal("COPY"), (p) ->
        {
            InputSimulator.pressKey(GLFW.GLFW_KEY_LEFT_CONTROL);
            InputSimulator.pressKey(GLFW.GLFW_KEY_C);
            InputSimulator.releaseKey(GLFW.GLFW_KEY_C);
            InputSimulator.releaseKey(GLFW.GLFW_KEY_LEFT_CONTROL);
        }));
        this.addRenderableWidget(new Button(2 * (35 + l) + k, k + -1 * (20 + l), 35, 20, Component.literal("PASTE"), (p) ->
        {
            InputSimulator.pressKey(GLFW.GLFW_KEY_LEFT_CONTROL);
            InputSimulator.pressKey(GLFW.GLFW_KEY_V);
            InputSimulator.releaseKey(GLFW.GLFW_KEY_V);
            InputSimulator.releaseKey(GLFW.GLFW_KEY_LEFT_CONTROL);
        }));
    }

    public void setShift(boolean shift) {
        if (shift != this.isShift) {
            this.isShift = shift;
            this.reinit = true;
        }
    }

    public void render(PoseStack poseStack, int pMouseX, int pMouseY, float pPartialTicks) {
        this.renderBackground(poseStack);
        drawCenteredString(poseStack, this.font, "Keyboard", this.width / 2, 2, 16777215);
        super.render(poseStack, 0, 0, pPartialTicks);
    }
}
