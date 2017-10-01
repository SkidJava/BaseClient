package client.module.render.hud.components;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import client.event.EventTarget;
import client.event.events.misc.EventKeyboard;
import client.event.events.render.EventRender2D;
import client.manager.managers.ModuleManager;
import client.manager.managers.SettingManager;
import client.module.Module;
import client.module.render.hud.HUD;
import client.setting.Setting;
import client.utils.ColorUtils;
import client.utils.RenderUtils;
import net.minecraft.client.Minecraft;

public class TabGUI extends HUD {

	private ArrayList<Category> categoryValues;
	private int currentCategoryIndex, currentModIndex, currentSettingIndex;
	private boolean editMode;

	private int screen;

	public TabGUI() {
		this.categoryValues = new ArrayList();
		this.currentCategoryIndex = 0;
		this.currentModIndex = 0;
		this.currentSettingIndex = 0;
		this.editMode = false;
		this.screen = 0;
		for (Category c : Module.Category.values()) {
			this.categoryValues.add(c);
		}
	}

	@EventTarget
	public void onRedner(EventRender2D e) {
		this.renderTopString(5, 5);
		int startX = 8;
		int startY = (5 + hudName.getHeight()) + 2;
		RenderUtils.drawBorderedRect(startX - 3, startY, startX + this.getWidestCategory() + 5,
				startY + this.categoryValues.size() * (hud.getHeight() + 2), 0.5f, ColorUtils.backColor, Color.BLACK.getRGB());
		RenderUtils.drawRect(startX - 3, startY, startX,
				startY + this.categoryValues.size() * (hud.getHeight() + 2), ColorUtils.arrayColor);
		for (Category c : this.categoryValues) {
			if (this.getCurrentCategorry().equals(c)) {
				RenderUtils.drawRect(startX, startY, startX + this.getWidestCategory() + 5, startY + hud.getHeight() + 2,
						ColorUtils.selectedColor);
			}

			String name = c.name();
			hud.drawStringWithShadow(name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase(),
					startX + 2 + (this.getCurrentCategorry().equals(c) ? 2 : 0), startY + 2, -1);
			startY += hud.getHeight() + 2;
		}

		if (screen == 1 || screen == 2) {
			int startModsX = startX + this.getWidestCategory() + 7;
			int startModsY = ((5 + hud.getHeight()) + 2) + currentCategoryIndex * (hud.getHeight() + 2);
			RenderUtils.drawBorderedRect(startModsX, startModsY, startModsX + this.getWidestMod() + 6,
					startModsY + this.getModsForCurrentCategory().size() * (hud.getHeight() + 2), 0.5f, ColorUtils.backColor, Color.BLACK.getRGB());
			for (Module m : getModsForCurrentCategory()) {
				if (this.getCurrentModule().equals(m)) {
					RenderUtils.drawRect(startModsX, startModsY, startModsX + this.getWidestMod() + 6,
							startModsY + hud.getHeight() + 2, ColorUtils.selectedColor);
				}
				if (client.getManagers().getManager(SettingManager.class).getSettingsForModule(m) != null)
					RenderUtils.drawRect(startModsX + this.getWidestMod() + 4, startModsY, startModsX + this.getWidestMod() + 6,
						startModsY + hud.getHeight() + 2, ColorUtils.settingsColor);
				hud.drawStringWithShadow(m.getName(), startModsX + 2 + (this.getCurrentModule().equals(m) ? 2 : 0),
						startModsY + 2, m.isToggled() ? ColorUtils.enabledColor : ColorUtils.disabledColor);
				startModsY += hud.getHeight() + 2;
			}
		}
		if (screen == 2) {
			Minecraft.getMinecraft().ingameGUI.getChatGUI().clearChatMessages();
			int startSettingX = startX + this.getWidestCategory() + this.getWidestMod() + 15;
			int startSettingY = ((5 + hud.getHeight()) + 2) + (currentCategoryIndex * (hud.getHeight() + 2)) + currentModIndex * (hud.getHeight() + 2);

			RenderUtils.drawBorderedRect(startSettingX, startSettingY, startSettingX + this.getWidestSetting() + 5,
					startSettingY + this.getSettingForCurrentMod().size() * (hud.getHeight() + 2), 0.5f, ColorUtils.backColor, Color.BLACK.getRGB());
			for (Setting s : this.getSettingForCurrentMod()) {

				if (this.getCurrentSetting().equals(s)) {
					RenderUtils.drawRect(startSettingX, startSettingY, startSettingX + this.getWidestSetting() + 5,
							startSettingY + hud.getHeight() + 2, ColorUtils.selectedColor);
				}
				if (s.isBoolean()) {
					hud.drawStringWithShadow(s.getName() + ": " + s.getRawBooleanValue(),
							startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
							editMode && this.getCurrentSetting().equals(s) ? ColorUtils.enabledColor : ColorUtils.disabledColor);
				} else if (s.isDigit()) {
					hud.drawStringWithShadow(s.getName() + ": " + s.getCurrentValue(),
							startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
							editMode && this.getCurrentSetting().equals(s) ? ColorUtils.enabledColor : ColorUtils.disabledColor);
				} else {
					hud.drawStringWithShadow(s.getName() + ": " + s.getCurrentOption(),
							startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
							editMode && this.getCurrentSetting().equals(s) ? ColorUtils.enabledColor : ColorUtils.disabledColor);
				}
				startSettingY += hud.getHeight() + 2;
			}
		}

	}

	private void renderTopString(int x, int y) {
		hudName.drawStringWithShadow(client.name, x, y, ColorUtils.nameColor);
		hudS.drawStringWithShadow("v" + client.version, x + hudName.getStringWidth(client.name) + 2, y, ColorUtils.verColor);
	}

	private void up() {
		if (this.currentCategoryIndex > 0 && this.screen == 0) {
			this.currentCategoryIndex--;
		} else if (this.currentCategoryIndex == 0 && this.screen == 0) {
			this.currentCategoryIndex = this.categoryValues.size() - 1;
		}

		else if (this.currentModIndex > 0 && this.screen == 1) {
			this.currentModIndex--;
		} else if (this.currentModIndex == 0 && this.screen == 1) {
			this.currentModIndex = this.getModsForCurrentCategory().size() - 1;
		}

		else if (this.currentSettingIndex > 0 && this.screen == 2 && !this.editMode) {
			this.currentSettingIndex--;
		} else if (this.currentSettingIndex == 0 && this.screen == 2 && !this.editMode) {
			this.currentSettingIndex = this.getSettingForCurrentMod().size() - 1;
		}

		if (editMode) {
			Setting s = this.getCurrentSetting();
			if (s.isBoolean()) {
				s.setBooleanValue(!s.getRawBooleanValue());
			} else if (s.isDigit()) {
				if (s.isOnlyInt()) {
					s.setCurrentValue(s.getCurrentValue() + 1);
				} else {
					s.setCurrentValue(s.getCurrentValue() + 0.1);
				}

			} else {
				try {
					s.setCurrentOption(s.getOptions().get(s.getCurrentOptionIndex() - 1));
				} catch (Exception e) {
					s.setCurrentOption(s.getOptions().get(s.getOptions().size() - 1));
				}

			}
		}

	}

	private void down() {
		if (this.currentCategoryIndex < this.categoryValues.size() - 1 && this.screen == 0) {
			this.currentCategoryIndex++;
		} else if (this.currentCategoryIndex == this.categoryValues.size() - 1 && this.screen == 0) {
			this.currentCategoryIndex = 0;
		}

		else if (this.currentModIndex < this.getModsForCurrentCategory().size() - 1 && this.screen == 1) {
			this.currentModIndex++;
		} else if (this.currentModIndex == this.getModsForCurrentCategory().size() - 1 && this.screen == 1) {
			this.currentModIndex = 0;
		}

		else if (this.currentSettingIndex < this.getSettingForCurrentMod().size() - 1 && this.screen == 2
				&& !this.editMode) {
			this.currentSettingIndex++;
		} else if (this.currentSettingIndex == this.getSettingForCurrentMod().size() - 1 && this.screen == 2
				&& !this.editMode) {
			this.currentSettingIndex = 0;
		}

		if (editMode) {
			Setting s = this.getCurrentSetting();
			if (s.isBoolean()) {
				s.setBooleanValue(!s.getRawBooleanValue());
			} else if (s.isDigit()) {
				if (s.isOnlyInt()) {
					s.setCurrentValue(s.getCurrentValue() - 1);
				} else {
					s.setCurrentValue(s.getCurrentValue() - 0.1);
				}

			} else {
				try {
					s.setCurrentOption(s.getOptions().get(s.getCurrentOptionIndex() + 1));
				} catch (Exception e) {
					s.setCurrentOption(s.getOptions().get(0));
				}

			}
		}
	}

	private void right(int key) {
		if (this.screen == 0 && getModsForCurrentCategory().size() != 0) {
            this.screen = 1;
        } else if (this.screen == 1 && key == Keyboard.KEY_RETURN) {
            this.getCurrentModule().toggle();
        } else if (this.screen == 1 && this.getSettingForCurrentMod() != null && this.getCurrentModule() != null) {
        	this.editMode = false;
            this.screen = 2;
        } else if (this.screen == 2 && key == Keyboard.KEY_RETURN) {
            this.editMode = !this.editMode;
        }

	}

	private void left() {
		if (this.screen == 1) {
			this.screen = 0;
			this.currentModIndex = 0;
		} else if (this.screen == 2) {
			this.screen = 1;
			this.currentSettingIndex = 0;
		}

	}

	@EventTarget
	public void onKey(EventKeyboard e) {
		switch (e.getKey()) {
		case Keyboard.KEY_UP:
			this.up();
			break;
		case Keyboard.KEY_DOWN:
			this.down();
			break;
		case Keyboard.KEY_RIGHT:
			this.right(Keyboard.KEY_RIGHT);
			break;
		case Keyboard.KEY_LEFT:
			this.left();
			break;
		case Keyboard.KEY_RETURN:
			this.right(Keyboard.KEY_RETURN);
			break;
		}
	}

	private Setting getCurrentSetting() {
		return getSettingForCurrentMod().get(currentSettingIndex);

	}

	private ArrayList<Setting> getSettingForCurrentMod() {
		return client.getManagers().getManager(SettingManager.class).getSettingsForModule(getCurrentModule());
	}

	private Category getCurrentCategorry() {
		return this.categoryValues.get(this.currentCategoryIndex);
	}

	private Module getCurrentModule() {
		return getModsForCurrentCategory().get(currentModIndex);
	}

	private ArrayList<Module> getModsForCurrentCategory() {
		ArrayList<Module> mods = new ArrayList();
		Category c = getCurrentCategorry();
		for (Module m : client.getManagers().getManager(ModuleManager.class).getMods()) {
			if (m.getCategory().equals(c)) {
				mods.add(m);
			}
		}
		return mods;
	}

	private int getWidestSetting() {
		int width = 0;
		for (Setting s : getSettingForCurrentMod()) {
			String name;
			if (s.isBoolean()) {
				name = s.getName() + ": " + s.getRawBooleanValue();

			} else if (s.isDigit()) {
				name = s.getName() + ": " + s.getCurrentValue();
			} else {
				name = s.getName() + ": " + s.getCurrentOption();
			}
			if (hud.getStringWidth(name) > width) {
				width = hud.getStringWidth(name);
			}
		}
		return width;
	}

	private int getWidestMod() {
		int width = 0;
		for (Module m : client.getManagers().getManager(ModuleManager.class).getMods()) {
			int cWidth = hud.getStringWidth(m.getName());
			if (cWidth > width) {
				width = cWidth;
			}
		}
		return width;
	}

	private int getWidestCategory() {
		int width = 0;
		for (Category c : this.categoryValues) {
			String name = c.name();
			int cWidth = hud.getStringWidth(
					name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase());
			if (cWidth > width) {
				width = cWidth;
			}
		}
		return width;
	}

}
