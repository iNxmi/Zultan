package com.nami.plugin.modules.coords.cmd.run;

import java.util.Map;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.nami.api.plugin.module.command.APICommand;
import com.nami.api.plugin.module.command.APICommandExecutor;
import com.nami.api.plugin.module.command.response.Response;
import com.nami.api.util.DataContainer;
import com.nami.api.util.MessageType;
import com.nami.plugin.Plugin;

public class RUN_List implements APICommandExecutor {

	private DataContainer<String, Map<String, Integer>> data;

	public RUN_List(DataContainer<String, Map<String, Integer>> data) {
		this.data = data;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Response onCommand(APICommand apiCommand, CommandSender sender, Command command, String label,
			String[] args) {
		for (Map.Entry<String, Map<String, Integer>> e : data.getData().entrySet())
			Plugin.logger.send(MessageType.NONE, sender,
					e.getKey() + " X: " + e.getValue().get("x") + " Y: " + e.getValue().get("y") + " Z: "
							+ e.getValue().get("z") + " Dim: "
							+ World.Environment.getEnvironment(e.getValue().get("d")));

		return Response.SUCCESS;
	}

}
