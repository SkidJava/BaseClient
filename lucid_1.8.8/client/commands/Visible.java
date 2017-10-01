/*
 * Decompiled with CFR 0_114.
 */
package client.commands;

import client.management.command.Com;
import client.management.command.Command;
import client.management.module.Module;
import client.management.module.ModuleManager;
import client.util.ChatUtils;

@Com(names={"visible", "v", "show"})
public class Visible
extends Command {
    @Override
    public void runCommand(String[] args) {
        String modName = "";
        if (args.length > 1) {
            modName = args[1];
        }
        Module module = ModuleManager.getModule(modName);
        if (module.name.equalsIgnoreCase("null")) {
            ChatUtils.sendClientMessage("Invalid Module.");
            return;
        }
        module.shown = !module.shown;
        ChatUtils.sendClientMessage(String.valueOf(module.name) + " is now " + (module.enabled ? "shown" : "hidden"));
    }

    @Override
    public String getHelp() {
        return "Visible - visible <v, show> (module) - Shows or hides the module on the arraylist.";
    }
}

