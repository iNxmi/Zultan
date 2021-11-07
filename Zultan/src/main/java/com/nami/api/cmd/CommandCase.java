package com.nami.api.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.nami.api.cmd.response.Response;
import com.nami.api.sys.APIPlugin;

public class CommandCase {

	private CommandRunnable executor;
	private String[] args;
	private String permission;
	private SenderScope scope;

	public CommandCase(CommandRunnable executor, String[] args, String permission, SenderScope scope) {
		this.executor = executor;
		this.args = args;
		this.permission = permission;
		this.scope = scope;
	}

	public Response execute(APIPlugin plugin, CommandSender sender, Command cmd, String label, String[] args) {
		if (this.args.length != args.length)
			return Response.NONE;

		for (int i = 0; i < args.length; i++)
			if (!(this.args[i].equalsIgnoreCase(args[i]) || this.args[i].equalsIgnoreCase("%")))
				return Response.SYNTAX_ERROR;

		if (sender instanceof Player && scope == SenderScope.CONSOLE && scope != SenderScope.BOTH)
			return Response.NOT_CONSOLE;

		if (sender instanceof ConsoleCommandSender && scope == SenderScope.PLAYER && scope != SenderScope.BOTH)
			return Response.NOT_PLAYER;

		if (!sender.hasPermission(permission) || !sender.hasPermission("*"))
			return Response.NO_PERM;

		return executor.onCommand(plugin, sender, cmd, label, args);
	}

}
