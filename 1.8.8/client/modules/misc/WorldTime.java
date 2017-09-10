/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.server.S03PacketTimeUpdate
 */
package client.modules.misc;

import client.eventapi.EventTarget;
import client.eventapi.events.PacketReceiveEvent;
import client.eventapi.events.TickEvent;
import client.management.module.Mod;
import client.management.module.Module;
import client.management.value.Val;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

@Mod
public class WorldTime
extends Module {
    @Val(min=0.0, max=24000.0, increment=250.0)
    private double time = 9000.0;

    @EventTarget
    private void onPacketRecieve(PacketReceiveEvent event) {
        if (event.packet instanceof S03PacketTimeUpdate) {
            event.setCancelled(true);
        }
    }

    @EventTarget
    private void onTick(TickEvent event) {
        this.mc.theWorld.setWorldTime((long)this.time);
    }
}

