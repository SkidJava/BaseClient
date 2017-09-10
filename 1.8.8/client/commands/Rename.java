/*
 * Decompiled with CFR 0_114.
 */
package client.commands;

import client.management.command.Com;
import client.management.command.Command;
import client.management.module.Module;
import client.management.module.ModuleManager;
import client.util.ChatUtils;

@Com(names={"rename", "rn"})
public class Rename
extends Command {
    @Override
    public void runCommand(String[] args) {
        String modName = "";
        String newName = "";
        if (args.length > 1) {
            modName = args[1];
            if (args.length > 2) {
                newName = args[2];
            }
        }
        Module module = ModuleManager.getModule(modName);
        if (module.name.equalsIgnoreCase("null")) {
            ChatUtils.sendClientMessage("Invalid Module.");
            return;
        }
        if (newName == "") {
            ChatUtils.sendClientMessage(String.valueOf(module.name) + "'s name has been reset.");
            module.name = module.realName;
            ModuleManager.save();
            return;
        }
        module.name = newName;
        ModuleManager.save();
        ChatUtils.sendClientMessage(String.valueOf(module.realName) + " renamed to " + newName);
    }

    @Override
    public String getHelp() {
        return "Rename - rename <rn> (module) (name) - Rename the specified module.";
    }
}

