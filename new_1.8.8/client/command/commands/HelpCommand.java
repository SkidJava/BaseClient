package client.command.commands;

import client.command.Command;
import client.manager.managers.CommandManager;

public class HelpCommand implements Command{

	@Override
	public boolean run(String[] args) {
		client.tellPlayer("Here are the list of commands:");
		for(Command c : client.getManagers().getManager(CommandManager.class).getCommands().values()) {
			client.tellPlayer(c.usage());
		}
		return true;
	}

	@Override
	public String usage() {
		return "USAGE: -help";
	}

}
