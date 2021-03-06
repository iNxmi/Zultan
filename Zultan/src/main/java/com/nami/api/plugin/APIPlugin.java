package com.nami.api.plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nami.api.base.MDL_Base;
import com.nami.api.plugin.module.APIModule;
import com.nami.api.util.Logger;

public abstract class APIPlugin extends JavaPlugin {

	private File folder;
	private File enabled;
	private Map<String, APIModule> modules;
	private ObjectMapper mapper = new ObjectMapper();
	
	public static Logger logger;

	public APIPlugin() {
		this.folder = new File(getDataFolder().getAbsolutePath().concat("/").concat(getDescription().getVersion()));
		this.enabled = new File(folder.getAbsolutePath().concat("/enabled.json"));
		this.modules = new HashMap<>();

		logger = new Logger(getDescription().getName());

		addModule(new MDL_Base(this));
	}

	@Override
	public void onEnable() {
		if (!folder.exists())
			folder.mkdirs();

		onPluginEnable();

		loadModules();

		try {
			enableModules();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract void onPluginEnable();

	@Override
	public void onDisable() {
		onPluginDisable();
	}

	public abstract void onPluginDisable();

	public void enableModules() throws IOException {
		enableModules(loadToEnable());
	}

	private Map<String, Boolean> loadToEnable() throws IOException {
		if (!enabled.exists())
			enabled.createNewFile();

		if (!jsonSyntaxCorrect(enabled) || Files.readAllBytes(enabled.toPath()).length <= 0)
			saveEnabled(State.DEFAULT);

		Map<String, Boolean> toEnable = mapper.readValue(Files.readAllBytes(enabled.toPath()),
				new TypeReference<HashMap<String, Boolean>>() {
				});

		return toEnable;
	}

	public void saveEnabled(State state) throws StreamWriteException, DatabindException, IOException {
		Map<String, Boolean> map = new HashMap<>();
		for (Map.Entry<String, APIModule> e : modules.entrySet()) {
			boolean value = false;
			switch (state) {
			case DEFAULT:
				value = e.getValue().isDefaultEnabled();
				break;
			case CURRENT:
				value = e.getValue().isEnabled();
				break;
			}
			map.put(e.getKey(), value);
		}

		mapper.writerWithDefaultPrettyPrinter().writeValue(enabled, map);
	}

	private boolean jsonSyntaxCorrect(File file) {
		try {
			mapper.readTree(file);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void addModule(APIModule module) {
		modules.put(module.getID(), module);
	}

	private void loadModules() {
		modules.forEach((k, v) -> v.load());
	}

	private void enableModules(Map<String, Boolean> toEnable) {
		toEnable.forEach((k, v) -> modules.get(k).setEnabled(v));
	}

	public Map<String, APIModule> getModules() {
		return modules;
	}

	public File getFolder() {
		return folder;
	}

	public enum State {
		DEFAULT, CURRENT;
	}

}
