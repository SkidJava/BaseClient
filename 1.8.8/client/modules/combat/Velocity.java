/*
 * Decompiled with CFR 0_114.
 *
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.server.S12PacketEntityVelocity
 */
package client.modules.combat;

import client.eventapi.EventTarget;
import client.eventapi.events.PacketReceiveEvent;
import client.eventapi.events.TickEvent;
import client.management.module.Mod;
import client.management.module.Module;
import client.management.module.ModuleManager;
import client.management.option.OptionManager;
import client.management.value.Val;
import client.modules.render.HUD;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

@Mod
public class Velocity
extends Module {
    @Val(min=0.0, max=200.0, increment=5.0)
    private double percent = 0.0;

    @EventTarget
    private void onTick(TickEvent event) {
        Character colorFormatCharacter = new Character('\u00a7');
        this.suffix = OptionManager.getOption((String)"Hyphen", (Module)ModuleManager.getModule(HUD.class)).value ? colorFormatCharacter + "7 - " + this.percent + "%" : colorFormatCharacter + "7 " + this.percent + "%";
    }

    @EventTarget
    private void onPacketReceive(PacketReceiveEvent event) {
        S12PacketEntityVelocity packet;
        if (event.packet instanceof S12PacketEntityVelocity && this.mc.theWorld.getEntityByID((packet = (S12PacketEntityVelocity)event.packet).getEntityID()) == this.mc.thePlayer) {
            if (this.percent > 0.0) {
                packet.motionX = (int)((double)packet.motionX * (this.percent / 100.0));
                packet.motionY = (int)((double)packet.motionY * (this.percent / 100.0));
                packet.motionZ = (int)((double)packet.motionZ * (this.percent / 100.0));
            } else {
                event.setCancelled(true);
            }
        }
    }
}

