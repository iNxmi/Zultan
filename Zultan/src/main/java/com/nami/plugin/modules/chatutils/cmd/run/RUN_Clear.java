package com.nami.plugin.modules.chatutils.cmd.run;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.nami.api.cmd.APICommand;
import com.nami.api.cmd.APICommandExecutor;
import com.nami.api.cmd.response.Response;
import com.nami.api.util.MessageType;
import com.nami.plugin.Plugin;

public class RUN_Clear implements APICommandExecutor {

	// TODO shit is deprecated!!
	@SuppressWarnings("deprecation")
	@Override
	public Response onCommand(APICommand apiCommand, CommandSender sender, Command command, String label,
			String[] args) {

		for (int i = 0; i < 100; i++)
			Bukkit.broadcastMessage("");
		Plugin.logger.broadcast(MessageType.NONE, "The Chat has been cleared!");

		return Response.SUCCESS;
	}

}