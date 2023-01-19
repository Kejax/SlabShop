package net.kejax.slabshop.inventories;

import net.kejax.slabshop.SlabShop;
import net.kejax.slabshop.abc.InventoryGui;
import net.kejax.slabshop.shops.PlayerShop;
import net.kejax.slabshop.utils.ItemBuilder;
import net.kejax.slabshop.utils.ShoppingMode;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ShopGuiInventory implements InventoryGui {


    private static final ItemBuilder buyItem = new ItemBuilder(Material.EMERALD);
    private static final ItemBuilder sellItem = new ItemBuilder(Material.REDSTONE);

    private final PlayerShop shop;

    private ShoppingMode shoppingMode;

    private int amount = 1;

    private final Inventory inventory;

    private Player player;

    public ShopGuiInventory(PlayerShop shop, Player player) {

        // TODO Make shop inventory loader

        this.shop = shop;

        inventory = Bukkit.createInventory(this, 36, shop.getOfflinePlayer().getName() + "'s Shop");

        setShoppingMode(ShoppingMode.ADD);

        inventory.setItem(13, shop.getItemStack());

        inventory.setItem(20, sellItem.setTitle("Sell for $" + ((double) amount * shop.getSellPrice())).build());
        inventory.setItem(24, buyItem.setTitle("Buy for $" + ((double) amount * shop.getBuyPrice())).build());

    }

    public void setShoppingMode(ShoppingMode mode) {
        shoppingMode = mode;
        updateInventory();
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);

        if (event.getCurrentItem() == null) {
            return;
        }

        InventoryAction inventoryAction = event.getAction();

        if (inventoryAction == InventoryAction.PICKUP_ALL && event.getSlot() == 13) {

            if (shoppingMode == ShoppingMode.ADD && amount < 65 && shop.getAmount() > amount) {
                amount++;
            } else if (shoppingMode == ShoppingMode.REMOVE && amount > 1) {
                amount--;
            }

        } else if (inventoryAction == InventoryAction.DROP_ONE_SLOT && event.getSlot() == 13) {
            if (shoppingMode == ShoppingMode.ADD) {
                setShoppingMode(ShoppingMode.REMOVE);
            } else { setShoppingMode(ShoppingMode.ADD); }

        }

        updateInventory();


    }

    public void updateInventory() {

        // Setup new ItemBuilder for changes
        ItemBuilder item = new ItemBuilder(shop.getItemStack());

        // Set amount of the item equals the selected amount of the user
        item.setAmount(amount);

        // Update everything regarding the ShoppingMode
        if (shoppingMode == ShoppingMode.ADD) {

            // Set Lore of the item for ShoppingMode.ADD
            item.setLore(
                    "Left click to add 1",
                    "Shift + Left click to add 4",
                    "Right click to add 16",
                    "Q to change to remove mode"
            );

        } else if (shoppingMode == ShoppingMode.REMOVE) {

            // Set Lore of the item for ShoppingMode.REMOVE
            item.setLore(
                    "Left click to remove 1",
                    "Shift + Left click to remove 4",
                    "Right click to remove 16",
                    "Q to change to add mode"
            );

        }

        // Update the glass panes
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, shoppingMode.getItem());
        }

        // Set the Item to be sold
        inventory.setItem(13, item.build());


    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
