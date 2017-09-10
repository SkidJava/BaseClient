/*
 * Decompiled with CFR 0_114.
 */
package client.commands;

import client.management.command.Command;
import client.management.command.CommandManager;
import client.management.module.Module;
import client.management.module.ModuleManager;
import client.management.option.Option;
import client.management.option.OptionManager;
import client.management.value.Value;
import client.management.value.ValueManager;
import client.util.ChatUtils;

public class OptionCommand
extends Command {
    @Override
    public void runCommand(String[] args) {
        if (args.length < 2) {
            ChatUtils.sendClientMessage(OptionCommand.getHelpString());
            return;
        }
        Module mod = ModuleManager.getModule(args[0].replaceFirst(CommandManager.commandPrefix, ""));
        if (mod != ModuleManager.MODULE_EMPTY) {
            Option option = OptionManager.getOption(args[1], mod);
            Value value = ValueManager.getValue(args[1], mod);
            if (option != null) {
                option.setValue(!option.value);
                ChatUtils.sendClientMessage(String.valueOf(option.name) + " Set To " + option.value);
            } else if (value != null) {
                try {
                    value.setValue(Double.parseDouble(args[2]));
                    ChatUtils.sendClientMessage(String.valueOf(value.name) + " Set To " + args[2]);
                }
                catch (NumberFormatException e) {
                    ChatUtils.sendClientMessage("Value input error.");
                }
            } else {
                ChatUtils.sendClientMessage("Option or value not recognized.");
            }
        } else {
            ChatUtils.sendClientMessage(OptionCommand.getHelpString());
        }
    }

    public static String getHelpString() {
        return "Toggle Option / Set Value - (modname) (option/value name) <double>";
    }
}

