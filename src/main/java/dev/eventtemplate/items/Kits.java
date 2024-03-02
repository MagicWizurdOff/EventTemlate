package dev.eventtemplate.items;

import dev.eventtemplate.*;
import org.bukkit.*;
import org.bukkit.block.banner.*;
import org.bukkit.enchantments.*;

import static org.bukkit.Material.*;
import static org.bukkit.Material.ARROW;

public enum Kits {
    DEFAULT(new Kit(
            new ItemBuilder(AIR).build()
    ));

    private final Kit kit;
    Kits(Kit kit) {
        this.kit = kit;
    }

    public Kit getKit() {
        return kit;
    }
}
