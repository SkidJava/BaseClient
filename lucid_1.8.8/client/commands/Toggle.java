/*
 * Decompiled with CFR 0_114.
 */
package client.commands;

import client.management.command.Com;
import client.management.command.Command;
import client.management.module.Module;
import client.management.module.ModuleManager;
import client.util.ChatUtils;

@Com(names={"toggle", "t", "tog"})
public class Toggle
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
        module.toggle();
        ChatUtils.sendClientMessage(String.valueOf(module.name) + " is now " + (module.enabled ? "enabled" : "disabled"));
    }

    @Override
    public String getHelp() {
        return "Toggle - toggle <t, tog> (module) - Toggles the module's enabled state.";
    }
}

