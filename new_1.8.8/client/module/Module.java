package client.module;

import java.util.ArrayList;

import client.Client;
import client.manager.managers.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class Module {

	protected Client client = Client.getClient();
	protected Minecraft mc = Minecraft.getMinecraft();
	protected EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

	private String name;

	private int keyCode;

	private boolean toggled;
	private boolean visible;

	private Category category;

	private ArrayList<Module> components = new ArrayList();

	public float anim = 0.0f;

	public Module(String name, int keyCode, Category category, boolean visible) {
		this.name = name;
		this.keyCode = keyCode;
		this.category = category;
		this.toggled = false;
		this.visible = visible;
	}

	public Module(String name, int keyCode, Category category) {
		this.name = name;
		this.keyCode = keyCode;
		this.category = category;
		this.toggled = false;
		this.visible = true;
	}

	public enum Category {
		COMBAT, MOVEMENT, RENDER, WORLD, PLAYER, NONE
	}

	public void toggle() {
		this.toggled = !this.toggled;
		if (this.toggled) {
			onEnable();
		} else {
			onDisable();
		}
	}

	public void onEnable() {
		anim = 0.0f;
		for(Module mod : components) client.getManagers().getManager(EventManager.class).register(mod);
		client.getManagers().getManager(EventManager.class).register(this);
	}

	public void onDisable() {
		for(Module mod : components) client.getManagers().getManager(EventManager.class).unregister(mod);
		client.getManagers().getManager(EventManager.class).unregister(this);
	}

	public boolean isVisible() {
		return this.visible;
	}

	public boolean isToggled() {
		return this.toggled;
	}

	public String getName() {
		return name;
	}

	public int getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

	public Category getCategory() {
		return category;
	}

	public void addComponent(Module mod) {
		components.add(mod);
	}

	public ArrayList<Module> getComponents() {
		return components;
	}

}
