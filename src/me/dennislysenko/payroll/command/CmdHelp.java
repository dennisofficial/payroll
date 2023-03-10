package me.dennislysenko.payroll.command;

import me.dennislysenko.payroll.type.Command;

public class CmdHelp extends Command {

	public CmdHelp() {
		LABEL = "HELP";
		USAGE = "/help";
		DESCRIPTION = "Shows all the commands.";
	}
	
	@Override
	public void execute(String[] args) {
		for (Command command : Command.getCommands()) {
			System.out.println(command.getUsage() + " - " + command.getDescription());
		}
	}

}
