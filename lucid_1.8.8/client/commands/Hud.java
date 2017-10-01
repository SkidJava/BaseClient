/*
 * Decompiled with CFR 0_114.
 */
package client.commands;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;

import client.management.command.Com;
import client.management.command.Command;
import client.util.ChatUtils;
import client.util.FileUtils;

@Com(names={"hudcolor", "color", "hc"})
public class Hud
extends Command {
    private static final File HUD_COLOR_DIR = FileUtils.getConfigFile("Hud Color");

    @Override
    public void runCommand(String[] args) {
        try {
            String color = "";
            if (args.length > 1) {
                color = args[1];
            }
            if (!(color.contains("0x") || color.contains("0X") || color.contains("#"))) {
                color = "0x" + color;
            }
            client.modules.render.HUD.color = (int)((long)Integer.decode(color).intValue() + 0xFF000000L);
            System.out.println(String.valueOf(client.modules.render.HUD.color));
            ChatUtils.sendClientMessage("Hud color set to " + color);
            Hud.save();
        }
        catch (Exception e) {
            e.printStackTrace();
            ChatUtils.sendClientMessage(this.getHelp());
        }
    }

    @Override
    public String getHelp() {
        return "Hud Color - hudcolor <color, hc>  (hex color).";
    }

    public static void save() {
        File file = HUD_COLOR_DIR;
        ArrayList<String> content = new ArrayList<String>();
        content.add("" + client.modules.render.HUD.color);
        FileUtils.write(file, content, true);
    }

    public static void load() {
        try {
            int i;
            File file = HUD_COLOR_DIR;
            ArrayList content = (ArrayList)FileUtils.read(file);
            client.modules.render.HUD.color = i = Integer.parseInt((String)content.get(0));
        }
        catch (Exception file) {
            // empty catch block
        }
    }
}

