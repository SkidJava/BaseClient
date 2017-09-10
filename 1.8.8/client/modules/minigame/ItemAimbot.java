package client.modules.minigame;

import client.eventapi.Event;
import client.eventapi.EventTarget;
import client.eventapi.events.UseItemEvent;
import client.management.module.Mod;
import client.management.module.Module;
import client.management.value.Val;
import client.util.RotationUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

@Mod
public class ItemAimbot extends Module
{
    @Val(min = 1.0, max = 60.0, increment = 1.0)
    private double range;

    public ItemAimbot() {
        this.range = 6.0;
    }

    @EventTarget
    private void onUseItem(final UseItemEvent event) {
        float yaw = 2.14748365E9f;
        float pitch = 2.14748365E9f;
        final float yawDifference = 2.14748365E9f;
        for (final Object o : this.mc.theWorld.playerEntities) {
        	final EntityPlayer player = (EntityPlayer)o;
            final String name = player.getDisplayName().getFormattedText();
            final Character colorFormatCharacter = new Character('§');
            final boolean validTarget = this.mc.thePlayer != player && this.mc.thePlayer.getDistanceToEntity((Entity)player) <= this.range && name.contains(colorFormatCharacter + "c");
            if (validTarget) {
                float diff = RotationUtils.getDistanceBetweenAngles(this.mc.thePlayer.rotationYaw, RotationUtils.getRotations((Entity)player)[0]);
                if (diff > yawDifference) {
                    continue;
                }
                diff = yawDifference;
                yaw = RotationUtils.getRotations((Entity)player)[0];
                pitch = RotationUtils.getRotations((Entity)player)[1];
            }
        }
        if (event.state == Event.State.PRE) {
            if (yaw != 2.14748365E9f) {
                this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C05PacketPlayerLook(yaw, pitch, true));
            }
        }
        else if (event.state == Event.State.POST && yaw != 2.14748365E9f) {
            this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C05PacketPlayerLook(this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, true));
        }
    }
}
