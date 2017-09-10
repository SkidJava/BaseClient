/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 */
package client.modules.player;

import client.eventapi.Event;
import client.eventapi.EventTarget;
import client.eventapi.events.PotionDeincrementEvent;
import client.eventapi.events.UpdateEvent;
import client.management.module.Mod;
import client.management.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

@Mod
public class PotionSaver
extends Module {
    @EventTarget
    private void onUpdate(UpdateEvent event) {
        if (event.state == Event.State.PRE && this.mc.thePlayer.motionX == 0.0 && this.mc.thePlayer.motionZ == 0.0 && this.mc.thePlayer.isCollidedVertically) {
            event.setCancelled(true);
        }
    }

    @EventTarget
    private void onPotionDeincrement(PotionDeincrementEvent event) {
        if (this.mc.thePlayer.motionX == 0.0 && this.mc.thePlayer.motionZ == 0.0 && this.mc.thePlayer.isCollidedVertically) {
            event.setCancelled(true);
        }
    }
}

