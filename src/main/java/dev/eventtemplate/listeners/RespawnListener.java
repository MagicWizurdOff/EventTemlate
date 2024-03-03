package dev.eventtemplate.listeners;

import dev.eventtemplate.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;

public class RespawnListener implements Listener {
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Location lost = Main.getSpawnConfig().getConfig().getLocation("lost");
        if (lost == null)
            return;
        if (Main.getBoolSettings("hardcore"))
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () ->
                    e.getPlayer().teleport(lost)
                    , 3L
            );
        else
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () ->
                    e.getPlayer().teleport(Main.getLocation(Main.getActiveEvent(), Main.getLpApi().getGroup(e.getPlayer()))
                    ), 3L
            );
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (Main.getBoolSettings("items_drop"))
            e.getDrops().clear();
    }
}
