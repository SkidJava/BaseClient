package client.manager;

public abstract class Manager {

	private int loadLevel;
	protected boolean loading = false;
	protected boolean endLoading = false;

	public Manager(int loadLevel) {
		this.loadLevel = loadLevel;
	}

	public int getLoadLevel() {
		return loadLevel;
	}

	public boolean getLoading() {
		return loading;
	}

	public boolean getEndLoading() {
		return endLoading;
	}

	public void setLoading(boolean loading) {
		this.loading = loading;
	}

	public abstract void load();

	public void loadStart() {
		loading = true;
		endLoading = false;
	}

	public void loadEnd() {
		loading = false;
		endLoading = true;
	}
}
