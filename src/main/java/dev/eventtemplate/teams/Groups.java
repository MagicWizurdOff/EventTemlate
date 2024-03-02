package dev.eventtemplate.teams;

import org.bukkit.*;

public enum Groups {
    TEAM("team", "Team", "group.team", ChatColor.RESET);

    Groups(String name, String displayName, String permissionName, ChatColor color) {
        this.name = name;
        this.displayName = displayName;
        this.permissionName = permissionName;
        this.color = color;
    }

    private final String name;
    private final String displayName;
    private final String permissionName;
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

    public ChatColor getColor() {
        return color;
    }
}
