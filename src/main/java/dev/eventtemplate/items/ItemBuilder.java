package dev.eventtemplate.items;


import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.banner.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;

import java.util.*;

public class ItemBuilder {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder setSkullOwner(Player owner) {
        return setSkullOwner(owner.getName());
    }

    public ItemBuilder setSkullOwner(String owner) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta instanceof SkullMeta) {
            ((SkullMeta) itemMeta).setOwningPlayer(Bukkit.getOfflinePlayer(owner));
            itemStack.setItemMeta(itemMeta);
        }
        return this;
    }


    public ItemBuilder(Material material) {
        this(material, 1, (byte) 0);
    }
    public ItemBuilder(Material material, int amount) {
        this(material, amount, (byte) 0);
    }

    public ItemBuilder(Material material, int amount, byte data) {
        itemStack = new ItemStack(material, amount, data);
        itemMeta = itemStack.getItemMeta();
    }
    public ItemBuilder(Material mat1, Material mat2) {
        itemStack = new ItemStack(new Random().nextInt(2) == 1 ? mat1 : mat2);
        itemMeta = itemStack.getItemMeta();
    }
    public ItemBuilder(Material mat1, Material mat2, int amount) {
        itemStack = new ItemStack(new Random().nextInt(2) == 1 ? mat1 : mat2, amount);
        itemMeta = itemStack.getItemMeta();
    }
    public ItemBuilder(Material mat1, Material mat2, int amount, byte data) {
        itemStack = new ItemStack(new Random().nextInt(2) == 1 ? mat1 : mat2, amount, data);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, byte data) {
        this(material, 1, data);
    }

    public ItemBuilder setData(byte data) {
        itemStack.setDurability(data);
        return this;
    }
    public ItemBuilder setUnbreakable() {
        itemMeta.setUnbreakable(true);
        return this;
    }
    public ItemBuilder randomize(Material mat1, Material mat2) {

        return this;
    }

    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }
    public ItemBuilder shieldPattern(DyeColor baseColor, Pattern... patterns) {
        if (itemStack.getType().name().toLowerCase().contains("shield")) {
            BlockStateMeta meta = (BlockStateMeta) itemMeta;
            BlockState state = meta.getBlockState();
            Banner bannerState = (Banner) state;
            bannerState.setBaseColor(baseColor);
            bannerState.setPatterns(Arrays.stream(patterns).toList());
            bannerState.update();
            meta.setBlockState(bannerState);
        }
        return this;
    }
    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        getItemMeta().addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        getItemMeta().removeEnchant(enchantment);
        return this;
    }

    public ItemBuilder hideEnchants() {
        addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder showEnchants() {
        removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... itemFlags) {
        getItemMeta().addItemFlags(itemFlags);
        return this;
    }

    public ItemBuilder setDisplayName(String displayName) {
        getItemMeta().setDisplayName(displayName);
        return this;
    }

    public ItemBuilder addLore(String text) {
        List<String> itemLores = new ArrayList<>();
        if (getItemMeta().getLore() != null && getItemMeta().getLore().size() > 0) {
            itemLores = getItemMeta().getLore();
        }
        itemLores.add(text);
        getItemMeta().setLore(itemLores);
        return this;
    }

    public ItemBuilder addLore(String... lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> metaLore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        metaLore.addAll(Arrays.asList(lore));
        itemMeta.setLore(metaLore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        getItemMeta().setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder setLores(String... lores) {
        getItemMeta().setLore(Arrays.asList(lores));
        return this;
    }

    public ItemBuilder removeItemFlags(ItemFlag... itemFlag) {
        getItemMeta().removeItemFlags(itemFlag);
        return this;
    }

    public ItemBuilder setGlowing(boolean glowing) {
        if (glowing) {
            addEnchantment(Enchantment.LURE, 1);
            hideEnchants();
        } else {
            removeEnchantment(Enchantment.LURE);
            hideEnchants();
        }
        return this;
    }

    public ItemMeta getItemMeta() {
        return itemMeta;
    }

    public ItemStack getItemStack() {
        return build();
    }

    public ItemStack build() {
        itemStack.setItemMeta(getItemMeta());
        return itemStack;
    }

    public ItemBuilder giveItem(Inventory inv) {
        inv.addItem(getItemStack());
        return this;
    }

    public ItemBuilder giveItem(Inventory inv, int position) {
        inv.setItem(position, getItemStack());
        return this;
    }

}