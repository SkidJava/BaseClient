/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C03PacketPlayer
 *  net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition
 *  net.minecraft.util.MovementInput
 */
package client.modules.movement;

import client.eventapi.Event;
import client.eventapi.EventTarget;
import client.eventapi.events.MoveEvent;
import client.eventapi.events.UpdateEvent;
import client.management.module.Mod;
import client.management.module.Module;
import client.management.module.ModuleManager;
import client.management.option.Op;
import client.management.value.Val;
import client.management.value.Value;
import client.management.value.ValueManager;
import client.modules.player.Damage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MovementInput;

@Mod
public class Fly
extends Module {
    @Op
    private boolean nc = true;
    @Op
    private boolean damage;
    @Op
    private boolean glide;
    @Val(min=0.0, max=20.0, increment=0.1)
    private double speed = 0.8;
    @Val(min=0.0, max=0.5, increment=0.005)
    private double glideSpeed = 0.035;

    @Override
    public void onEnable() {
        if (this.damage && this.mc.thePlayer.isCollidedVertically) {
            double val = ValueManager.getValue((String)"damage", (Module)ModuleManager.getModule(Damage.class)).value;
            ValueManager.getValue("damage", ModuleManager.getModule(Damage.class)).setValue(0.5);
            ModuleManager.getModule(Damage.class).toggle();
            ValueManager.getValue("damage", ModuleManager.getModule(Damage.class)).setValue(val);
            this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.01, this.mc.thePlayer.posZ, false));
        }
        super.onEnable();
    }

    @EventTarget
    private void onUpdate(UpdateEvent event) {
        if (event.state == Event.State.PRE) {
            if (this.nc) {
                if (!this.mc.thePlayer.movementInput.jump && !this.mc.thePlayer.movementInput.sneak && this.glide && (double)this.mc.thePlayer.movementInput.moveForward == 0.0 && (double)this.mc.thePlayer.movementInput.moveStrafe == 0.0) {
                    this.mc.thePlayer.motionY = 0.0;
                    this.mc.thePlayer.motionZ = 0.0;
                    this.mc.thePlayer.motionX = 0.0;
                    event.setCancelled(true);
                } else {
                    this.mc.thePlayer.motionY = this.mc.thePlayer.movementInput.jump ? this.speed : (this.mc.thePlayer.movementInput.sneak ? - this.speed : (this.glide ? - this.glideSpeed : 0.0));
                }
            } else {
                this.mc.thePlayer.motionY = this.mc.thePlayer.movementInput.jump ? this.speed / 2.0 : (this.mc.thePlayer.movementInput.sneak ? (- this.speed) / 2.0 : (this.glide ? - this.glideSpeed : 0.0));
            }
        }
    }

    @EventTarget
    private void onMove(MoveEvent event) {
        if (!this.nc) {
            event.x *= this.speed;
            event.z *= this.speed;
        }
    }
}

