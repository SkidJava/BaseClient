package client.manager.managers;

import java.util.ArrayList;
import java.util.Set;

import org.reflections.Reflections;

import client.manager.Manager;
import client.module.Empty;
import client.module.Module;
import client.utils.progressbar.ProgressBar;
import client.utils.progressbar.ProgressBarList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ModuleManager extends Manager {

	private Module empty = new Empty();
	private ArrayList<Module> mods = new ArrayList();

	public ModuleManager() {
		super(3);
	}

	@Override
	public void load() {
		loadStart();
		new Thread(new Runnable() {
			@Override
			public void run() {
				Reflections reflect = new Reflections(new Object[] { Module.class });
				Set modules = reflect.getSubTypesOf(Module.class);
				float progress = 0;
				for (Object obj : modules) {
					Class objClass = (Class) obj;
					try {
						progress += 100.0f / modules.size();
						Module mod = (Module) objClass.newInstance();
						ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
						ProgressBar progressBar = new ProgressBar(scaledresolution.getScaledWidth() / 2, scaledresolution.getScaledHeight() / 2, 100, 10, progress, "Loading Module / " + mod.getName(), -1);
						ProgressBarList.addProgress(0, progressBar);

						if (objClass.getSuperclass() != Module.class || mod instanceof Empty) {
							Thread.sleep(10);
							continue;
						}

						mods.add(mod);

						Reflections reflect1 = new Reflections(new Object[] { mod.getClass() });
						Set components = reflect1.getSubTypesOf(mod.getClass());
						ProgressBarList.removeProgress(1);
						float progress1 = 0;
						for (Object obj1 : components) {
							Class objClass1 = (Class) obj1;
							Module mod1 = (Module) objClass1.newInstance();
							if (objClass1.getSuperclass() == mod.getClass()) {
								mod.addComponent(mod1);
								progress1 += 100.0f / components.size();
								ProgressBar progressBar1 = new ProgressBar(scaledresolution.getScaledWidth() / 2, scaledresolution.getScaledHeight() / 2 + 30, 100, 10, progress1, "Loading Components " + mod.getComponents().size() + " / " + components.size(), -1);
								ProgressBarList.addProgress(1, progressBar1);
								Thread.sleep(10);
							}
						}

						Thread.sleep(10);
					} catch (Exception e) {
						;
					}
				}

				mods.sort((o1, o2) -> o2.getName().length() - o1.getName().length());

				loadEnd();
			}
		}).start();
	}

	private void addModule(Module m) {
		this.mods.add(m);
	}

	public ArrayList<Module> getMods() {
		return this.mods;
	}

	public ArrayList<Module> getToggledMods() {
		ArrayList<Module> mods = new ArrayList();
		for (Module m : getMods()) {
			if (m.isToggled()) {
				mods.add(m);
			}
		}
		return mods;
	}

	public ArrayList<Module> getVisibleMods() {
		ArrayList<Module> mods = new ArrayList();
		for (Module m : getMods()) {
			if (m.isToggled() && !m.isVisible()) {
				mods.add(m);
			}
		}
		return mods;
	}

	public Module getModule(String name) {
		for (Module m : this.mods) {
			if (m.getName().equalsIgnoreCase(name))
				return m;
		}
		return empty;
	}

	public Module getModule(Class<? extends Module> mods) {
		for (Module m : this.mods) {
			if (m.getClass() == mods)
				return m;
		}
		return empty;
	}

}
