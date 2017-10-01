package client.manager;

import java.util.ArrayList;
import java.util.Set;

import org.reflections.Reflections;

import client.utils.progressbar.ProgressBar;
import client.utils.progressbar.ProgressBarList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class Managers {

	private ArrayList<Manager> managers = new ArrayList();

	public void load() {
		Reflections reflect = new Reflections(new Object[]{Manager.class});
		Set managers = reflect.getSubTypesOf(Manager.class);
		float progress = 0;
		for (Object obj : managers) {
			Class objClass = (Class)obj;
			try {
				progress += 100.0f / managers.size();
				Manager manager = (Manager)objClass.newInstance();

				this.managers.add(manager);

				ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
				ProgressBar progressBar = new ProgressBar(scaledresolution.getScaledWidth() / 2, scaledresolution.getScaledHeight() / 2, 100, 10, progress, "Loading Managers " + this.managers.size() + " / " + managers.size(), -1);
				ProgressBarList.addProgress(0, progressBar);

				Thread.sleep(10);
			} catch (Exception e) {;}
		}

		this.managers.sort((o2, o1) -> o2.getLoadLevel() - o1.getLoadLevel());
	}

	public void loadManagers() {
		for(Manager m : this.managers) m.load();
	}

	public <T> T getManager(Class<? extends T> manager) {
		for (Manager m : this.managers) {
			if (m.getClass() == manager)
				return (T) m;
		}
		return null;
	}

	public float getManagerSize() {
		return this.managers.size();
	}

	public boolean getLoading() {
		boolean temp = false;
		for(Manager m : this.managers) {
			temp = temp || m.getLoading();
		}

		return temp;
	}

	public boolean getEndLoading() {
		boolean temp = this.managers.size() > 0 ? true : false;
		for(Manager m : this.managers) {
			temp = temp && m.getEndLoading();
		}

		return temp;
	}
}
