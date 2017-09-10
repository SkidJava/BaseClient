/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 */
package client.modules.render;

import client.management.module.Mod;
import client.management.module.Module;
import client.management.option.Op;
import client.ui.click.ClickGui;
import client.ui.clickgui.Gui;
import client.ui.clickgui.GuiGrabber;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

@Mod(keybind=54)
public class GUI
extends Module {
    @Op
    private boolean panel;

    @Override
    public void onEnable() {
        if (this.panel) {
            this.mc.displayGuiScreen((GuiScreen)new ClickGui());
        } else {
            Gui.instance.reloadOptions();
            this.mc.displayGuiScreen((GuiScreen)new GuiGrabber());
        }
    }
}

