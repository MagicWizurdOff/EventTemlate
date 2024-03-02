package dev.eventtemplate.items;

import org.bukkit.entity.*;
import org.bukkit.inventory.*;

import java.util.*;

public class Kit {
    private final ItemStack[] items;
    public Kit(ItemStack... items) {
        this.items = items;
    }
    public void giveKit(Player p) {
        PlayerInventory inv = p.getInventory();
        inv.clear();
        for (ItemStack i : items) {
            String typeName = i.getType().name().toLowerCase();
            if (typeName.endsWith("_helmet"))
                inv.setHelmet(i);
            else if (typeName.endsWith("_chestplate"))
                inv.setChestplate(i);
            else if (typeName.endsWith("_leggings"))
                inv.setLeggings(i);
            else if (typeName.endsWith("_boots"))
                inv.setBoots(i);
            else if (typeName.contains("shield"))
                inv.setItemInOffHand(i);
            else
                inv.addItem(i);
        }
    }
}
