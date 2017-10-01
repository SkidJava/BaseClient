/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C0BPacketEntityAction
 *  net.minecraft.network.play.client.C0BPacketEntityAction$Action
 *  net.minecraft.util.MovementInput
 */
package client.modules.player;

import client.eventapi.Event;
import client.eventapi.EventTarget;
import client.eventapi.events.UpdateEvent;
import client.management.module.Mod;
import client.management.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.MovementInput;

@Mod
public class Sneak
extends Module {
    @EventTarget
    private void onPreUpdate(UpdateEvent event) {
        if (event.state == Event.State.PRE) {
            if (this.mc.thePlayer.isSneaking() || this.mc.thePlayer.movementInput.moveForward == 0.0f && this.mc.thePlayer.movementInput.moveStrafe == 0.0f) {
                return;
            }
            this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        } else if (event.state == Event.State.POST) {
            this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
        }
    }

    @Override
    public void onDisable() {
        this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        super.onDisable();
    }
}

