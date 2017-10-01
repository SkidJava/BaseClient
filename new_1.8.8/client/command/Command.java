package client.command;

import client.Client;

public interface Command {

	Client client = Client.getClient();

	boolean run(String[] args);

	String usage();

}
