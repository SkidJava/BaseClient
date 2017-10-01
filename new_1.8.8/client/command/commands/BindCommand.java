package client.command.commands;

import org.lwjgl.input.Keyboard;

import client.command.Command;
import client.manager.managers.ModuleManager;
import client.module.Empty;
import client.module.Module;

public class BindCommand implements Command {

	@Override
	public boolean run(String[] args) {
		if (args.length == 3) {
			Module m = client.getManagers().getManager(ModuleManager.class).getModule(args[1]);
			if (!(m instanceof Empty)) {
				m.setKeyCode(Keyboard.getKeyIndex(args[2].toUpperCase()));
				client.tellPlayer(m.getName() + " has been bound to " + args[2] + ".");
			} else {
				client.tellPlayer(args[1] + " not found.");
			}
			return true;
		}
		return false;
	}

	@Override
	public String usage() {
		return "USAGE: -bind [module] [key]";
	}

}
