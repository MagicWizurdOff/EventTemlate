package dev.eventtemplate.teams;

import org.bukkit.*;

public enum Groups {
    TEAM("team", "Team", "group.team", "&c&lTEAM", 2 , ChatColor.RESET);

    Groups(String name, String displayName, String permissionName,String prefix, int weight, ChatColor color) {
        this.name = name;
        this.displayName = displayName;
        this.permissionName = permissionName;
        this.color = color;
        this.prefix = prefix;
        this.weight = weight;
    }

    private final String name;
    private final String displayName;
    private final String permissionName;
    private final String prefix;
    private final int weight;
    private final ChatColor color;

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public int getWeight() {
        return weight;
    }

    public ChatColor getColor() {
        return color;
    }

    public String getPrefix() {
        return prefix;
    }
}
