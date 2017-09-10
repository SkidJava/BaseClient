/*
 * Decompiled with CFR 0_114.
 */
package client.modules.render;

import client.eventapi.EventTarget;
import client.eventapi.events.TickEvent;
import client.management.module.Mod;
import client.management.module.Module;

@Mod
public class Brightness
extends Module {
	
    @EventTarget
    private void onTick(TickEvent event) {
    	this.mc.gameSettings.gammaSetting = 100F;
    }

    @Override
    public void onDisable() {
        super.onDisable();
    	this.mc.gameSettings.gammaSetting = 0F;
    }
}

