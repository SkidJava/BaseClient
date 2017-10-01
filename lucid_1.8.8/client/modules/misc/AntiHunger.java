/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C03PacketPlayer
 */
package client.modules.misc;

import client.eventapi.EventTarget;
import client.eventapi.events.PacketSendEvent;
import client.management.module.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

public class AntiHunger
extends Module {
    @EventTarget
    private void onPacketSend(PacketSendEvent event) {
        if (event.packet instanceof C03PacketPlayer) {
            C03PacketPlayer packet = (C03PacketPlayer)event.packet;
            packet.onGround = false;
        }
    }
}

