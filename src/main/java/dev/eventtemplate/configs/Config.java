package dev.eventtemplate.configs;

import com.google.common.base.*;
import dev.eventtemplate.*;
import org.bukkit.configuration.*;
import org.bukkit.configuration.file.*;

import java.io.*;

public class Config {
    private File customConfigFile;
    private FileConfiguration customConfig;
    private final String name;
    public Config(String name) {
        this.name = name;
        create();
        register();
    }
    public FileConfiguration getConfig() {
        return customConfig;
    }
    public void create() {
        customConfigFile = new File(Main.getInstance().getDataFolder(), name + ".yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            Main.getInstance().saveResource(name + ".yml", false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            Main.getInstance().getLogger().warning(e.getLocalizedMessage());
        }
    }
    public void saveConfig() {
        try {
            customConfig.save(customConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void reload() {
        FileConfiguration newConfig = YamlConfiguration.loadConfiguration(customConfigFile);
        final InputStream defConfigStream = Main.getInstance().getResource(name + ".yml");
        if (defConfigStream == null) {
            return;
        }
        newConfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
    }
    public void register() {
        Main.getConfigMap().put(name, this);
    }
}
