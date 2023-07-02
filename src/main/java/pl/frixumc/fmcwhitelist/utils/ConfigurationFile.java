package pl.frixumc.fmcwhitelist.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ConfigurationFile {
    private File configFile;
    private FileConfiguration config;

    public ConfigurationFile(String filePath) {
        this.configFile = new File(filePath);
        this.config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void set(String key, Object value) {
        config.set(key, value);
    }

    public Object get(String key) {
        return config.get(key);
    }

    public void addDefaults(Map<String, Object> defaults) {
        for (Map.Entry<String, Object> entry : defaults.entrySet()) {
            if (!config.contains(entry.getKey())) {
                config.set(entry.getKey(), entry.getValue());
            }
        }
    }

    public void save() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}