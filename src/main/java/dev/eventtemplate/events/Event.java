package dev.eventtemplate.events;

import dev.eventtemplate.*;
import dev.eventtemplate.configs.*;
import dev.eventtemplate.items.*;
import dev.eventtemplate.teams.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

import java.util.*;
import java.util.logging.*;

import static org.bukkit.ChatColor.*;

public class Event {

    public void start() {

    }
    public void giveKits(Player p, Kit kit) {
        kit.giveKit(p);
    }
    public void teleport(List<Player> players, Location loc) {
        new BukkitRunnable() {
            int counter = 0;
            final int max = players.size() - 1;
            @Override
            public void run() {
                if (counter == max) {
                    cancel();
                }
                players.get(counter).teleport(loc);
                counter++;
            }
        }.runTaskTimer(Main.getInstance(), 0, Main.getPeriod());
    }
    public Location getLocation(Events e, Groups g) {
        return Main.getSpawnConfig().getConfig().getLocation(e.getName() + "." + g.getName());
    }
    public boolean check() {
        FileConfiguration config = Main.getSpawnConfig().getConfig();
        Main.log(translateAlternateColorCodes('&',GOLD + "Starting check!"));
        for (Events e : Events.values()) {
            for (Groups g : Groups.values()) {
                String path = e.getName() + "." + g.getName();
                Main.log(DARK_AQUA + "Location: " + path);
                if (config.getLocation(path) == null) {
                    Main.log(RED + "Failed");
                    return false;
                }
                Main.log(GREEN + "Succeed");
            }
        }
        return true;
    }
}
