package client.module;

import org.lwjgl.input.Keyboard;

public class Empty extends Module {

	public Empty() {
		super("Empty", Keyboard.KEY_NONE, Category.NONE, false);
	}
}
