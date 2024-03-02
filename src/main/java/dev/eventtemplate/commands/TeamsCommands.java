package dev.eventtemplate.commands;

import dev.eventtemplate.*;
import dev.eventtemplate.teams.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import javax.swing.*;
import java.util.*;
import java.util.stream.*;

public class TeamsCommands {
    public static boolean joinGroup(CommandSender sender, String[] args) {
        Player p = Bukkit.getPlayer(args[2]);
        Groups g = Groups.valueOf(args[3].toUpperCase());
        if (p == null) {
            sender.sendMessage(ChatColor.RED + "Player is not online!");
            return true;
        }
        return Main.getLpApi().joinGroup(p, g);
    }
    public static boolean leaveGroup(CommandSender sender, String[] args) {
        Player p = Bukkit.getPlayer(args[2]);
        if (p == null) {
            sender.sendMessage(ChatColor.RED + "Player is not online!");
            return true;
        }
        return Main.getLpApi().leaveGroup(p);
    }
    public static boolean listGroups(CommandSender sender) {
        Map<Groups, List<Player>> map = Bukkit.getOnlinePlayers().stream().collect(Collectors.groupingBy(Main.getLpApi()::getGroup));
        for (Groups g : Groups.values()) {
            int count = map.get(g).size();
            StringBuilder builder = new StringBuilder();
            sender.sendMessage(ChatColor.DARK_AQUA + ChatColor.BOLD.toString() + "Teams:");
            sender.sendMessage(ChatColor.GOLD + g.getDisplayName() + " (" + count +"):");
            for (Player p : map.get(g)) {
                builder.append(p.getName()).append(", ");
            }
            sender.sendMessage(ChatColor.GRAY + builder.toString());
        }
        return true;
    }
    public static boolean randomizeGroups(CommandSender sender) {
        List<Groups> groups = Arrays.asList(Groups.values());
        int upCounter = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (upCounter == (groups.size() - 1))
                upCounter = 0;
            Main.getLpApi().joinGroup(p, groups.get(upCounter));
            upCounter++;
        }
        return true;
    }
}
