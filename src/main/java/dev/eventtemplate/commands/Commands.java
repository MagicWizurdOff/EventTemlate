package dev.eventtemplate.commands;

import dev.eventtemplate.*;
import dev.eventtemplate.configs.*;
import dev.eventtemplate.events.*;
import dev.eventtemplate.teams.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;

public class Commands implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.isOp() && !sender.hasPermission("war.admin"))
            return true;
        if (args.length == 0)
            return unknown(sender);
//        if (!(sender instanceof Player)) {
//            (ChatColor.RED + "Only players can execute this command!");
//            return true;
//        }
        return switch (args[0].toLowerCase()) {
            case "help" -> helpCommand(sender);
            case "teams" -> teamsCommand(sender, args);
            case "event" -> eventCommand(sender, args);
            case "reload" -> reloadCommand(sender);
            case "spawn" -> spawnCommand(sender, args);
            case "title" -> titleCommand(args);
            default -> unknown(sender);
        };

    }

    private boolean notPlayerError(CommandSender sender) {
        Main.message(sender,ChatColor.RED + "This command can be only run by a player!");
        return true;
    }

    private boolean spawnCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player))
            return notPlayerError(sender);
        Player p = ((Player) sender).getPlayer();
        Events e = Events.valueOf(args[1].toUpperCase());
        Groups g = Groups.valueOf(args[2].toUpperCase());
        Main.getSpawnConfig().getConfig().set(e.getName() + "." + g.getName(), p.getLocation());
        Main.getSpawnConfig().saveConfig();
        return true;
    }

    private boolean eventCommand(CommandSender sender, String[] args) {
        Events.valueOf(args[1].toUpperCase()).getEvent().start();
        Main.message(sender, ChatColor.GREEN + "Event " + args[1] + " has started!");
        return true;
    }
    private boolean titleCommand(String[] args) {

        return true;
    }
    private boolean unknown(CommandSender sender) {
        Main.message(sender, ChatColor.RED + "Unknown argument. Use /help to show valid arguments!");
        return true;
    }

    private boolean teamsCommand(CommandSender sender, String[] args) {
        return switch (args[1]) {
            case "join" -> TeamsCommands.joinGroup(sender, args);
            case "leave" -> TeamsCommands.leaveGroup(sender, args);
            case "list" -> TeamsCommands.listGroups(sender);
            case "random" -> TeamsCommands.randomizeGroups(sender);
            default -> unknown(sender);
        };
    }

    private boolean helpCommand(CommandSender sender) {
        File helpTxt;
        try {
            helpTxt = new File(Main.getInstance().getDataFolder().getPath() + "/help.txt");
            Scanner myReader = new Scanner(helpTxt);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Main.message(sender, ChatColor.translateAlternateColorCodes('&', data));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return true;
    }
    private boolean reloadCommand(CommandSender sender) {
        for (String s : Main.getConfigMap().keySet())
            Main.getConfigMap().get(s).reload();
        Main.message(sender, ChatColor.GREEN + "Reload complete!");
        return true;
    }

    private final String[] args0 = new String[] {
            "help", "teams", "event", "reload", "spawn"
    };
    private final String[] teams1 = new String[] {
            "join", "leave", "list", "random"
    };
    private final String[] events = Arrays.stream(Events.values()).map(Events::getId).map(String::valueOf).toArray(String[]::new);
    private final String[] groups = Arrays.stream(Groups.values()).map(Groups::getName).toArray(String[]::new);

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1)
            return Arrays.stream(args0)
                    .filter(subcommand -> subcommand.startsWith(args[0]))
                    .toList();
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("teams"))
                return Arrays.stream(teams1)
                        .filter(subcommand -> subcommand.startsWith(args[1]))
                        .toList();
            if (args[0].equalsIgnoreCase("event"))
                return Arrays.stream(events)
                        .filter(subcommand -> subcommand.startsWith(args[1]))
                        .toList();
            if (args[0].equalsIgnoreCase("spawn"))
                return Arrays.stream(Events.values())
                        .map(Events::getName)
                        .filter(subcommand -> subcommand.startsWith(args[1]))
                        .toList();
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("teams")) {
                if (args[1].equalsIgnoreCase("join") || args[1].equalsIgnoreCase("leave"))
                    return null;
            }
            if (args[0].equalsIgnoreCase("spawn"))
                return Arrays.stream(groups)
                        .filter(subcommand -> subcommand.startsWith(args[2]))
                        .toList();
        }
        if (args.length == 4) {
            if (args[0].equalsIgnoreCase("teams") && args[1].equalsIgnoreCase("join"))
                    return Arrays.stream(groups)
                            .filter(subcommand -> subcommand.startsWith(args[3]))
                            .toList();
        }
        return Collections.emptyList();
    }
}
