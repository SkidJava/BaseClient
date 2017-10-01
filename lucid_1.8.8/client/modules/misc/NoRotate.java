/*
 * Decompiled with CFR 0_114.
 *
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.server.S08PacketPlayerPosLook
 */
package client.modules.misc;

import client.eventapi.EventTarget;
import client.eventapi.events.PacketReceiveEvent;
import client.management.module.Mod;
import client.management.module.Module;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

@Mod
public class NoRotate
extends Module {
    @EventTarget
    private void onPacketReceive(PacketReceiveEvent event) {
        if (event.packet instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook)event.packet;
            packet.yaw = this.mc.thePlayer.rotationYaw;
            packet.pitch = this.mc.thePlayer.rotationPitch;
        }
    }
}

