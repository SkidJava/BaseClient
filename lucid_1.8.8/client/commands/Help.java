/*
 * Decompiled with CFR 0_114.
 */
package client.commands;

import java.util.List;

import client.commands.OptionCommand;
import client.management.command.Com;
import client.management.command.Command;
import client.management.command.CommandManager;
import client.util.ChatUtils;

@Com(names={"help", "?"})
public class Help
extends Command {
    @Override
    public void runCommand(String[] args) {
        ChatUtils.sendClientMessage("All Commands:");
        for (Command command : CommandManager.commandList) {
            if (command instanceof OptionCommand || command.getHelp() == null) continue;
            ChatUtils.sendMessage(command.getHelp());
        }
        ChatUtils.sendMessage(OptionCommand.getHelpString());
    }

    @Override
    public String getHelp() {
        return "Help - help <?> - Returns a list of commands and their information.";
    }
}

