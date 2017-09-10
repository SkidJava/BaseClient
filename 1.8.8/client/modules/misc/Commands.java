/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C01PacketChatMessage
 */
package client.modules.misc;

import client.eventapi.EventTarget;
import client.eventapi.events.PacketSendEvent;
import client.management.command.Command;
import client.management.command.CommandManager;
import client.management.module.Mod;
import client.management.module.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;

@Mod(enabled = true, shown = false)
public class Commands extends Module
{
    @EventTarget
    private void onPacketSend(final PacketSendEvent event) {
        if (event.packet instanceof C01PacketChatMessage) {
            final C01PacketChatMessage packet = (C01PacketChatMessage)event.packet;
            final String message = packet.getMessage();
            if (message.startsWith(CommandManager.commandPrefix)) {
                event.setCancelled(true);
                final String[] args = message.split(" ");
                final Command commandFromMessage = CommandManager.getCommandFromMessage(message);
                commandFromMessage.runCommand(args);
            }
        }
    }
}

