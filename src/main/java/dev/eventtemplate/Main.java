package dev.eventtemplate;

import dev.eventtemplate.commands.*;
import dev.eventtemplate.configs.*;
import dev.eventtemplate.events.*;
import dev.eventtemplate.listeners.*;
import dev.eventtemplate.teams.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Main extends JavaPlugin {
    private static Main main;
    private static LuckPermsApi lpApi;
    private static Map<String, Config> configMap;
    private static long period;
    private static Events activeEvent;
    private static Config spawnConfig;
    private static Config teamConfig;
    private static final String PREFIX = ChatColor.translateAlternateColorCodes('&', "&6[&3EventTemplate&6]&r");

    @Override
    public void onEnable() {
        // Plugin startup logic
        main = this;
        lpApi = new LuckPermsApi();
        configMap = new HashMap<>();
        period = 2L;
        spawnConfig = new Config("spawn");
        teamConfig = new Config("teams", true);

        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new RespawnListener(), this);
        getCommand("war").setExecutor(new Commands());
        getCommand("war").setTabCompleter(new Commands());

    }
    public static Config getTeamConfig() {
        return teamConfig;
    }
    public static Config getSpawnConfig() {
        return spawnConfig;
    }

    public static Map<String, Config> getConfigMap() {
        return configMap;
    }

    public static void log(String msg) {
        Bukkit.getConsoleSender().sendMessage(PREFIX + " " + ChatColor.translateAlternateColorCodes('&', msg));
    }
    public static void message(Player p, String msg) {
        p.sendMessage(PREFIX + " " + ChatColor.translateAlternateColorCodes('&', msg));
    }
    public static void message(CommandSender p, String msg) {
        p.sendMessage(PREFIX + " " + ChatColor.translateAlternateColorCodes('&', msg));
    }
    public static long getPeriod() {
        return period;
    }
    public static Events getActiveEvent() {
        return activeEvent;
    }

    public static void setActiveEvent(Events activeEvent) {
        Main.activeEvent = activeEvent;
    }

    public static Main getInstance() {
        return main;
    }
    public static LuckPermsApi getLpApi() {
        return lpApi;
    }
    public static boolean getBoolSettings(String path) {
        return Main.getInstance().getConfig().getBoolean("settings." + path);
    }
    public static int getIntSettings(String path) {
        return Main.getInstance().getConfig().getInt("settings." + path);
    }
    public static double getDoubleSettings(String path) {
        return Main.getInstance().getConfig().getDouble("settings." + path);
    }
    public static String getStringSettings(String path) {
        return Main.getInstance().getConfig().getString("settings." + path);
    }
    public static Location getLocation(Events e, Groups g) {
        return getSpawnConfig().getConfig().getLocation(e.getName() + "." + g.getName());
    }
}
