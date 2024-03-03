package dev.eventtemplate.events.events;

import dev.eventtemplate.*;
import dev.eventtemplate.events.*;
import dev.eventtemplate.teams.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

import java.util.*;

public class Event0 extends Event {
    @Override
    public void start() {
        if (!check())
            return;
        Main.setActiveEvent(Events.EVENT0);
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        Events e = Main.getActiveEvent();
        new BukkitRunnable() {
            final int max = players.size() - 1;
            int counter = 0;
            @Override
            public void run() {
                if (counter == max)
                    cancel();
                Player p = players.get(counter);
                Groups g = Main.getLpApi().getGroup(p);
                e.getKitsInUse().get(g).getKit().giveKit(p);
                p.teleport(getLocation(e, g));
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                    p.setHealth(20);
                    p.setFoodLevel(20);
                    p.setGameMode(GameMode.ADVENTURE);
                }, 3L);
                counter++;
            }
        }.runTaskTimer(Main.getInstance(), 0, Main.getPeriod());
    }
}
