package client.modules.movement;

import client.eventapi.EventTarget;
import client.eventapi.events.PacketSendEvent;
import client.eventapi.events.SprintEvent;
import client.eventapi.events.TickEvent;
import client.management.module.Mod;
import client.management.module.Module;
import client.management.module.ModuleManager;
import client.management.option.Op;
import net.minecraft.network.play.client.C0BPacketEntityAction;

@Mod
public class Sprint
extends Module {
    @Op
    private boolean multiDirection;
    @Op
    private boolean fake;
    @Op
    private boolean legit;

    @EventTarget
    private void onTick(TickEvent event) {
        if (this.canSprint()) {
            this.mc.thePlayer.setSprinting(true);
        }
    }

    @EventTarget
    private void onSprint(SprintEvent event) {
        if (this.canSprint()) {
            event.sprint = true;
        }
    }

    @EventTarget
    private void onPacketSend(PacketSendEvent event) {
        C0BPacketEntityAction packet;
        if (this.fake && event.packet instanceof C0BPacketEntityAction && ((packet = (C0BPacketEntityAction)event.packet).getAction() == C0BPacketEntityAction.Action.START_SPRINTING || packet.getAction() == C0BPacketEntityAction.Action.STOP_SPRINTING)) {
            event.setCancelled(true);
        }
    }

    private boolean canSprint() {
        if (!this.mc.thePlayer.isCollidedHorizontally && !this.mc.thePlayer.isSneaking() && (!this.legit || ModuleManager.getModule(NoSlowdown.class).enabled || this.legit && this.mc.thePlayer.getFoodStats().getFoodLevel() > 5 && !this.mc.thePlayer.isUsingItem())) {
            if (this.multiDirection) {
                if (this.mc.thePlayer.movementInput.moveForward == 0.0f && this.mc.thePlayer.movementInput.moveStrafe == 0.0f) {
                    return false;
                }
                return true;
            }
            if (this.mc.thePlayer.movementInput.moveForward > 0.0f) {
                return true;
            }
            return false;
        }
        return false;
    }
}

