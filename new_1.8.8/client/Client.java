package client;

import org.lwjgl.opengl.Display;

import com.mojang.realmsclient.gui.ChatFormatting;

import client.event.EventTarget;
import client.event.events.misc.EventKeyboard;
import client.gui.GuiLoading;
import client.manager.Managers;
import client.manager.managers.EventManager;
import client.manager.managers.ModuleManager;
import client.module.Module;
import client.utils.progressbar.ProgressBarList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.ChatComponentText;

public class Client {
	private static Client theClient = new Client();

	public final String name = "Client";
	public final double version = 1.0;

	private Managers managers;
	private Thread thread;

	public void onEnable() {
		Minecraft.getMinecraft().displayGuiScreen(new GuiLoading());

		managers = new Managers();
		thread = new Thread(new Runnable() {
			public void run() {
				managers.load();
			}
		});

		Display.setTitle(name + " | " + version);
	}

	public void onDisable() {
		managers.getManager(EventManager.class).unregister(this);
	}

	public static Client getClient() {
		return theClient;
	}

	public Managers getManagers() {
		return managers;
	}

	public void tellPlayer(String text) {
		Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(
				ChatFormatting.WHITE + "[" + ChatFormatting.RED + name + ChatFormatting.WHITE + "] " + text));
	}

	public void onUpdate() {
		ProgressBarList.render();

		if (thread.getState() == Thread.State.NEW)
			thread.start();
		else if (thread.getState() == Thread.State.TERMINATED && !managers.getLoading() && !managers.getEndLoading()) {
			managers.loadManagers();
			managers.getManager(EventManager.class).register(this);
		} else if (managers.getEndLoading())
			Minecraft.getMinecraft().displayGuiScreen(new GuiMainMenu());
	}

	@EventTarget
	public void onKey(EventKeyboard e) {
		for (Module m : managers.getManager(ModuleManager.class).getMods())
			if (m.getKeyCode() == e.getKey())
				m.toggle();
	}
}
