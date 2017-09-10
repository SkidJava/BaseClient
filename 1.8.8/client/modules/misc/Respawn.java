/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 */
package client.modules.misc;

import client.eventapi.EventTarget;
import client.eventapi.events.TickEvent;
import client.management.module.Mod;
import client.management.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

@Mod
public class Respawn
extends Module {
    @EventTarget
    private void onTick(TickEvent event) {
        if (!this.mc.thePlayer.isEntityAlive()) {
            this.mc.thePlayer.respawnPlayer();
        }
    }
}

