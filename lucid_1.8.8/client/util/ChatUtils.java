package client.util;

import client.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class ChatUtils {
    public static void sendClientMessage(String message) {
        String prefix = "\u00a7b[" + Client.NAME + "]: \u00a7r";
        Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(String.valueOf(prefix) + message));
    }

    public static void sendMessage(String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(message));
    }
}

