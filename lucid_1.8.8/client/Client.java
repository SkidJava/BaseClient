package client;

import org.lwjgl.opengl.Display;

import client.commands.Hud;
import client.commands.InventoryCleaner;
import client.management.account.AccountManager;
import client.management.command.CommandManager;
import client.management.friend.FriendManager;
import client.management.module.ModuleManager;
import client.management.option.OptionManager;
import client.management.value.ValueManager;
import client.ui.alt.screens.account.AccountScreen;
import client.ui.clickgui.Draw;
import client.util.FontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;

public class Client {
    public static final String NAME = "Client";
    public static final double VERSION = 0.01;
    public static AccountScreen accountScreen = new AccountScreen();
    public static FontRenderer font;

    public static void init() throws Exception {
        Client.configureFont();
        ModuleManager.init();
        CommandManager.init();
        OptionManager.init();
        ValueManager.init();
        FriendManager.init();
        AccountManager.init();
        Hud.load();
        InventoryCleaner.load();
        Draw.load();
        new client.ui.clickgui.Gui();
        Display.setTitle((String)NAME + " " + VERSION + " | " + Minecraft.getMinecraft().getVersion());
    }

    private static void configureFont() {
        Minecraft mc = Minecraft.getMinecraft();
        font = new FontRenderer(mc.gameSettings, new ResourceLocation(NAME.toLowerCase() + "/font/ascii.png"), mc.renderEngine, false);
        if (mc.gameSettings.language != null) {
            mc.fontRendererObj.setUnicodeFlag(mc.isUnicode());
            mc.fontRendererObj.setBidiFlag(mc.mcLanguageManager.isCurrentLanguageBidirectional());
        }
        mc.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)font);
    }
}

