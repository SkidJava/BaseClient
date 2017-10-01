package client.module.render.hud.components;

import java.util.ArrayList;

import client.event.EventTarget;
import client.event.events.render.EventRender2D;
import client.manager.managers.ModuleManager;
import client.manager.managers.SettingManager;
import client.module.Module;
import client.module.render.hud.HUD;
import client.utils.ColorUtils;
import client.utils.RenderUtils;
import client.utils.TimeHelper;

public class Arraylist extends HUD {

	private float blueColor = 255, changeTime = 2000;
	public TimeHelper flagTime = new TimeHelper();
	public boolean colorFlag = false;

	private float speed = 500;
	private DrawPos pos = DrawPos.TOP;
	public TimeHelper fpsTimeHelper = new TimeHelper();

	@EventTarget
	public void onRender(EventRender2D e) {
		/*if (flagTime.hasReached(changeTime)) {
			colorFlag = ! colorFlag;
			flagTime.reset();
		}
		blueColor = colorFlag ? blueColor - 0.05f * (fpsTimeHelper.getCurrentMS() - fpsTimeHelper.getLastMS()): blueColor + 0.5f * (fpsTimeHelper.getCurrentMS() - fpsTimeHelper.getLastMS());
		if (blueColor > 255) blueColor = 255;
		else if (blueColor < 100) blueColor = 100;
		hud.drawStringWithShadow("test", 90, 4, new Color(0, 0, (int)blueColor, 255).getRGB());*/


		if (!client.getManagers().getManager(SettingManager.class).getSetting(HUD.class, "ArrayList")
				.getBooleanValue()) {
			ArrayList<Module> mods = client.getManagers().getManager(ModuleManager.class).getVisibleMods();

			mods.sort((o1, o2) -> hud.getStringWidth(o2.getName()) - hud.getStringWidth(o1.getName()));
			int y = pos == DrawPos.TOP ? 2 : e.height - hud.getHeight();
			float tempX = 0;
			for (Module m : mods) {
				m.anim = m.anim + hud.getStringWidth(m.getName()) / speed
								* (fpsTimeHelper.getCurrentMS() - fpsTimeHelper.getLastMS());
				if (m.anim > hud.getStringWidth(m.getName())) m.anim = hud.getStringWidth(m.getName());

				RenderUtils.drawRect(e.width - m.anim - 5, y - 2, e.width - 1, y + (hud.getHeight()),
						ColorUtils.backColor);
				RenderUtils.drawRect(e.width - m.anim - 5 - 1, y - 2, e.width - m.anim - 5, y + (hud.getHeight()) + 1,
						ColorUtils.arrayColor);
				RenderUtils.drawRect(e.width - 1, y - 2, e.width, y + (hud.getHeight()), ColorUtils.arrayColor);
				RenderUtils.drawRect(e.width - tempX - 5, pos == DrawPos.TOP ? y - 2 : y + hud.getHeight(),
						e.width - m.anim - 5, pos == DrawPos.TOP ? y - 1 : y + hud.getHeight() + 1,
						ColorUtils.arrayColor);
				hud.drawStringWithShadow(m.getName(), e.width - m.anim - 3, y + 1, ColorUtils.arrayColor);
				tempX = m.anim;
				y = pos == DrawPos.TOP ? y + hud.getHeight() + 2 : y - hud.getHeight() - 2;
			}

			if (mods.size() != 0) {
				RenderUtils.drawRect(e.width - mods.get(0).anim - 5 - 1f, pos == DrawPos.TOP ? 0 : e.height, e.width,
						pos == DrawPos.TOP ? 1 : e.height - 1, ColorUtils.arrayColor);
				RenderUtils.drawRect(e.width - tempX - 5 - 1f, pos == DrawPos.TOP ? y - 2 : y + hud.getHeight(),
						e.width, pos == DrawPos.TOP ? y - 1 : y + hud.getHeight() + 1, ColorUtils.arrayColor);
			}

			fpsTimeHelper.reset();
		}

	}

	public enum DrawPos {
		TOP, BOTTOM,
	}
}
