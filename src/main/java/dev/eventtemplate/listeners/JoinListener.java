package dev.eventtemplate.listeners;

import dev.eventtemplate.*;
import dev.eventtemplate.commands.*;
import dev.eventtemplate.configs.*;
import dev.eventtemplate.teams.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

public class JoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Config teams = Main.getTeamConfig();
        e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&8[&a+&8] &7" + p.getName()));
        if (!Main.getBoolSettings("teams_on_join"))
            return;
        for (Groups g : Groups.values())
            if (teams.getConfig().getList(g.getName()).contains(p.getName()))
                Main.getLpApi().joinGroup(p, g);

    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4-&8] &7" + p.getName()));
    }
}
