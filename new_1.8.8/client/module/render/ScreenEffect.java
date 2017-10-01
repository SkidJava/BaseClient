package client.module.render;

import org.lwjgl.input.Keyboard;

import client.manager.managers.SettingManager;
import client.module.Module;
import client.setting.Setting;

public class ScreenEffect extends Module {

	public ScreenEffect() {
		super("ScreenEffect", Keyboard.KEY_NONE, Category.RENDER, false);

		client.getManagers().getManager(SettingManager.class).addSetting(new Setting(this, "DamageEffect", true));
	}
}
