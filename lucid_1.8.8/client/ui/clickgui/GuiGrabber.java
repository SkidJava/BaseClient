/*
 * Decompiled with CFR 0_114.
 *
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 */
package client.ui.clickgui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class GuiGrabber
extends GuiScreen {
    private int scrollVelocity;

    public void mouseClicked(int mX, int mY, int button) {
        int mouseX = Mouse.getX();
        int mouseY = Display.getHeight() - Mouse.getY();
        Gui.instance.mousePress(mouseX, mouseY, button);
    }

    public void mouseReleased(int mX, int mY, int button) {
        int mouseX = Mouse.getX();
        int mouseY = Display.getHeight() - Mouse.getY();
        Gui.instance.mouseRelease(mouseX, mouseY, button);
    }

    public void handleKeyboardInput() {
        int key = Keyboard.getEventKey();
        char keyChar = Keyboard.getEventCharacter();
        if (Keyboard.getEventKeyState()) {
            Gui.instance.keyPress(key, keyChar);
        } else {
            Gui.instance.keyRelease(key, keyChar);
        }
    }

    public void drawScreen(int x, int y, float par3) {
        GL11.glPushMatrix();
        ScaledResolution scaledRes = new ScaledResolution(this.mc);
        float scale = (float)scaledRes.getScaleFactor() / (float)Math.pow(scaledRes.getScaleFactor(), 2.0);
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        int mouseX = Mouse.getX();
        int mouseY = Display.getHeight() - Mouse.getY();
        Gui.instance.update(mouseX, mouseY);
        Gui.instance.render();
        GL11.glPopMatrix();
        if (Mouse.hasWheel()) {
            int wheel = Mouse.getDWheel();
            this.scrollVelocity = wheel < 0 ? -120 : (wheel > 0 ? 120 : 0);
        }
        Gui.instance.mouseScroll(this.scrollVelocity);
    }
}

