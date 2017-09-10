/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C0APacketAnimation
 */
package client.modules.misc;

import client.eventapi.Event;
import client.eventapi.EventTarget;
import client.eventapi.events.UpdateEvent;
import client.management.module.Mod;
import client.management.module.Module;
import client.management.option.Op;
import client.management.value.Val;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0APacketAnimation;

@Mod
public class Derp
extends Module {
    @Op
    private boolean spinny;
    @Op
    private boolean headless;
    @Val(min=1.0, max=50.0, increment=1.0)
    private double increment = 25.0;
    private double serverYaw;

    @EventTarget(value=0)
    private void onUpdate(UpdateEvent event) {
        if (event.state == Event.State.PRE) {
            if (this.spinny) {
                this.serverYaw += this.increment;
                event.yaw = (float)this.serverYaw;
            }
            if (this.headless) {
                event.pitch = 180.0f;
            }
            if (!this.headless && !this.spinny) {
                event.yaw = (float)(Math.random() * 360.0);
                event.pitch = (float)(Math.random() * 360.0);
                this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0APacketAnimation());
            }
        }
    }
}

