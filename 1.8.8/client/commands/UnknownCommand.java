/*
 * Decompiled with CFR 0_114.
 */
package client.commands;

import client.management.command.Com;
import client.management.command.Command;
import client.management.command.CommandManager;
import client.util.ChatUtils;

@Com(names={""})
public class UnknownCommand
extends Command {
    @Override
    public void runCommand(String[] args) {
        ChatUtils.sendClientMessage("Unknown Command. Try " + CommandManager.commandPrefix + "help.");
    }
}

