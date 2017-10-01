package client.manager.managers;

import java.util.HashMap;
import java.util.Map;

import client.Client;
import client.command.Command;
import client.command.commands.BindCommand;
import client.command.commands.HelpCommand;
import client.manager.Manager;

public class CommandManager extends Manager{

	private HashMap<String[], Command> commands;

	private String prefix;

	public CommandManager() {
		super(1);
		commands = new HashMap();
		prefix = "-";
	}

	@Override
	public void load() {
		commands.put(new String[] { "help", "h" }, new HelpCommand());
		commands.put(new String[] { "bind", "b" }, new BindCommand());

		loadEnd();
	}

	public boolean processCommand(String rawMessage) {
		if (!rawMessage.startsWith(prefix)) {
			return false;
		}

		boolean safe = rawMessage.split(prefix).length > 1;

		if (safe) {
			String beheaded = rawMessage.split(prefix)[1];

			String[] args = beheaded.split(" ");

			Command command = getCommand(args[0]);

			if (command != null) {
				if (!command.run(args)) {
					Client.getClient().tellPlayer(command.usage());
				}
			}
			else {
				Client.getClient().tellPlayer("Try -help.");
			}
		}
		else {
			Client.getClient().tellPlayer("Try -help.");
		}

		return true;
	}

	private Command getCommand(String name) {
		for (Map.Entry entry : commands.entrySet()) {
			String[] key = (String[]) entry.getKey();
			for (String s : key) {
				if (s.equalsIgnoreCase(name)) {
					return (Command) entry.getValue();
				}
			}

		}
		return null;
	}

	public HashMap<String[], Command> getCommands() {
		return commands;
	}

}
