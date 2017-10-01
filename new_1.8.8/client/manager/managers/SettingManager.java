package client.manager.managers;

import java.util.ArrayList;

import client.Client;
import client.manager.Manager;
import client.module.Module;
import client.setting.Setting;

public class SettingManager extends Manager{

	private ArrayList<Setting> settings = new ArrayList();

	public SettingManager() {
		super(2);
	}

	@Override
	public void load() {
		loadEnd();
	}

	public void addSetting(Setting s) {
		this.settings.add(s);
	}

	public Setting getSetting(Module m, String name) {
		for (Setting s : this.settings) {
			if (s.getModule().equals(m) && s.getName().equalsIgnoreCase(name)) {
				return s;
			}
		}
		return null;
	}

	public Setting getSetting(Class<? extends Module> m, String name) {
		for (Setting s : this.settings) {
			if (s.getModule().equals(Client.getClient().getManagers().getManager(ModuleManager.class).getModule(m)) && s.getName().equalsIgnoreCase(name)) {
				return s;
			}
		}
		return null;
	}

	public ArrayList<Setting> getSettings() {
		return this.settings;
	}

	public ArrayList<Setting> getSettingsForModule(Module m) {
		ArrayList<Setting> settings = new ArrayList();

		for (Setting s : this.settings) {
			if (s.getModule().equals(m)) {
				settings.add(s);
			}
		}

		if (settings.isEmpty()) {
			return null;
		}

		return settings;
	}

}
