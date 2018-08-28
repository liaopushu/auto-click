package com.autoclick.main.impl;

import com.autoclick.main.ConfigHandler;
import com.autoclick.utils.ConfigEntry;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;

public final class ConfigHandlerImp implements ConfigHandler {
	private static ConfigHandler ch = new ConfigHandlerImp();
	private static final ConfigEntry configEntry = new ConfigEntry();
	private static final Gson gson = new Gson();
	private static String filePath;
	private static String fileName = "config.json";
	private static File file;

	public static final ConfigHandler getInstance() {
		if (ch == null) {
			ch = new ConfigHandlerImp();
		}

		try {
			file = new File(ConfigHandler.class.getClassLoader().getResource(fileName).getPath());
			if (!file.exists() && file.createNewFile()) {
				setConfigToFile(new ConfigEntry());
			}
		} catch (Exception var3) {
			file = new File(fileName);

			try {
				if (file.createNewFile()) {
					setConfigToFile(new ConfigEntry());
				}
			} catch (IOException var2) {
				var2.printStackTrace();
			}

			var3.printStackTrace();
		}

		return ch;
	}

	public ConfigEntry getConfigFromFile(File file) {
		if (file == null) {
			return null;
		} else {
			return file.isFile() ? setFileIntoConfigEntry(file) : null;
		}
	}

	private static final ConfigEntry setFileIntoConfigEntry(File file) {
		ConfigEntry ce = new ConfigEntry();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(file));
			br.lines().forEach((line) -> {
				sb.append(line);
			});
		} catch (FileNotFoundException var13) {
			var13.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException var12) {
					var12.printStackTrace();
				}
			}

		}

		if (sb.length() > 0) {
			ce = (ConfigEntry) gson.fromJson(sb.toString(), ce.getClass());
		}

		return ce;
	}

	public static boolean setConfigToFile(ConfigEntry ce) {
		String configString = gson.toJson(ce);
		BufferedWriter bw = null;
		if (file != null & file.isFile() && file.canWrite()) {
			try {
				bw = new BufferedWriter(new FileWriter(file));
				bw.write(configString);
				bw.flush();
			} catch (IOException var12) {
				var12.printStackTrace();
				return false;
			} finally {
				try {
					if (bw != null) {
						bw.close();
					}
				} catch (IOException var11) {
					var11.printStackTrace();
				}

			}

			return true;
		} else {
			return false;
		}
	}

	public boolean applyConfig(ConfigEntry ce) {
		if (ce != null) {
			configEntry.setControlKey(ce.isControlKey());
			configEntry.setShiftKey(ce.isShiftKey());
			configEntry.setMouseButton(ce.getMouseButton());
			configEntry.setKey(ce.getKey());
			configEntry.setDelay(ce.getDelay());
			return setConfigToFile(configEntry);
		} else {
			return false;
		}
	}

	public void setConfigFileName(String fileName) {
		fileName = fileName;
	}

	public ConfigEntry getConfigFromFile() {
		if (file == null) {
			file = new File(filePath);
		}

		return this.getConfigFromFile(file);
	}
}