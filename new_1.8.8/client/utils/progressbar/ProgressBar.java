package client.utils.progressbar;

import client.utils.RenderUtils;

public class ProgressBar {

	private float x, y, width, height, progress;
	private String progressName;
	private int color;

	public ProgressBar(float x, float y, float width, float height, float progress, String progressName, int color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.progress = progress;
		this.progressName = progressName;
		this.color = color;
	}

	public void render() {
		RenderUtils.drawProgressBar(x, y, width, height, progress, progressName, color);
	}
}
