package net.kejax.slabshop.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ShoppingMode {
    ADD(new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setTitle("Add mode").build()),
    REMOVE(new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setTitle("Remove mode").build());

    private final ItemStack item;

    private ShoppingMode(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

}
