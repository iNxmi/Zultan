package com.nami.api.modules.base.cmd.run;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import com.nami.api.cmd.APICommandExecutor;
import com.nami.api.cmd.response.Response;
import com.nami.api.sys.APIModule;
import com.nami.api.sys.APIPlugin;
import com.nami.api.util.MessageType;
import com.nami.plugin.Plugin;

public class RUN_List implements APICommandExecutor {

	@Override
	public Response onCommand(APIPlugin plugin, @NotNull CommandSender sender, @NotNull Command command,
			@NotNull String label, @NotNull String[] args) {

		for (APIModule m : plugin.getModules()) {
			boolean enabled = plugin.getActiveModules().getData().get(m.getName());
			Plugin.logger.send(MessageType.NONE, sender, (enabled ? "§a" : "§c") + m.getName() + ": " + enabled);
		}

		return Response.SUCCESS;
	}

}