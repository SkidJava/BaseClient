/*
 * Decompiled with CFR 0_114.
 *
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package client.modules.player;

import client.eventapi.EventTarget;
import client.eventapi.events.UpdateEvent;
import client.management.module.Mod;
import client.management.module.Module;

@Mod
public class FastPlace
extends Module {
    @EventTarget
    private void onUpdate(UpdateEvent event) {
        this.mc.rightClickDelayTimer = 0;
    }
}

