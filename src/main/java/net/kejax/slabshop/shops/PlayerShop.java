package net.kejax.slabshop.shops;

import net.kejax.slabshop.SlabShop;
import net.kejax.slabshop.abc.Shop;
import net.kejax.slabshop.inventories.ShopGuiInventory;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;

public class PlayerShop implements Shop {

    private OfflinePlayer player; // The Owner of the Shop

    private Location location; // The Location of the shop

    private ItemStack itemStack; // The item of the shop that's being sold
    private Integer amount; // The amount of items that are available in the shop

    private Double buyPrice; // The money a player has to pay when buying it
    private Double sellPrice; // The money a player gets when selling it

    private Boolean buyOnly; // If true, players can only buy items
    private Boolean sellOnly; // If true players can only sell items

    public PlayerShop(OfflinePlayer player, Location location, ItemStack itemStack, Integer amount, Double buyPrice, Double sellPrice, Boolean buyOnly, Boolean sellOnly) throws IllegalArgumentException {

        if (buyOnly && sellOnly) {
            throw new IllegalArgumentException();
        }

        this.player = player;

        this.location = location;

        this.itemStack = itemStack;
        this.amount = amount;

        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;

        this.buyOnly = buyOnly;
        this.sellOnly = sellOnly;

    }

    public PlayerShop(OfflinePlayer player, Location location, ItemStack itemstack, Integer amount, Double buyPrice, Double sellPrice) {

        this.player = player;

        this.location = location;

        this.itemStack = itemstack;
        this.amount = amount;

        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;

        this.buyOnly = false;
        this.sellOnly = false;

    }

    public PlayerShop(Location location) {
        this.location = location;
    }

    public boolean save() {
        try {
            SlabShop.sqLite.savePlayerShop(this);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    @Override
    public void openInventory(Player player) {
        player.openInventory(new ShopGuiInventory(this, player).getInventory());
    }

    public Boolean getBuyOnly() {
        return buyOnly;
    }

    public Boolean getSellOnly() {
        return sellOnly;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Integer getAmount() {
        return amount;
    }

    public Location getLocation() {
        return location;
    }

    public OfflinePlayer getOfflinePlayer() {
        return player;
    }
}
