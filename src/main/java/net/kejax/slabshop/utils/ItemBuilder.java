package net.kejax.slabshop.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemBuilder {

    private final ItemStack itemStack;

    private final ItemMeta itemMeta;

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder addGlowing() {
        // Adds glowing to the Item

        this.itemStack.addEnchantment((this.itemStack.getType() == Material.BOW) ? Enchantment.PROTECTION_ENVIRONMENTAL : Enchantment.ARROW_INFINITE, 1);

        this.itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        return this;
    }

    public ItemBuilder setAmount(int amount) {
        // Sets the Item's amount
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setTitle(String title) {
        // Sets the Item's title
        this.itemMeta.setDisplayName(title);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        // Sets the Item's lore
        this.itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return itemStack;
    }
}
