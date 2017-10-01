package client.utils.progressbar;

import java.util.ArrayList;

public class ProgressBarList {

	private static ArrayList<ProgressBar> progressList = new ArrayList();

	public static void addProgress(int id, ProgressBar pb) {
		if (progressList.size() > id)
			progressList.remove(id);
		progressList.add(id, pb);
	}

	public static void removeProgress(int id) {
		if (progressList.size() > id)
			progressList.remove(id);
	}

	public static void render() {
		for (ProgressBar pb : progressList)
			pb.render();
	}
}
