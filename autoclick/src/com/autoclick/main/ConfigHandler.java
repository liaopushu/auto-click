package com.autoclick.main;

import com.autoclick.utils.ConfigEntry;
import java.io.File;

public interface ConfigHandler {
	void setConfigFileName(String var1);

	ConfigEntry getConfigFromFile(File var1);

	ConfigEntry getConfigFromFile();

	boolean applyConfig(ConfigEntry var1);
}