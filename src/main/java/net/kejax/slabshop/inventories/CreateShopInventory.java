package net.kejax.slabshop.inventories;

import net.kejax.slabshop.SlabShop;
import net.kejax.slabshop.abc.InventoryGui;
import net.kejax.slabshop.shops.PlayerShop;
import net.kejax.slabshop.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class CreateShopInventory implements InventoryGui {

    private Player owner; // Owner of the shop

    private Inventory inventory;

    public static ItemStack confirmButton = new ItemBuilder(Material.EMERALD_BLOCK).setTitle("Confirm").build();
    public static ItemStack cancelButton = new ItemBuilder(Material.REDSTONE_BLOCK).setTitle("Cancel").build();

    public ItemStack currentItem = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setTitle("Choose item in inventory").build();

    private Location location;

    public CreateShopInventory(Player owner, Location location) {
        this.owner = owner;
        this.location = location;

        this.inventory = Bukkit.createInventory(
                this,
                27,
                "Create Shop"
        );

        inventory.setItem(13, currentItem);
        inventory.setItem(19, cancelButton);
        inventory.setItem(25, confirmButton);
    }

    @Override
    public void onClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        event.setCancelled(true);

        Inventory inventory = event.getClickedInventory();

        if (inventory == null) {
            return;
        }

        if (inventory.getHolder() instanceof Player) {

            if (event.getCurrentItem() == null) {
                return;
            }

            currentItem = event.getCurrentItem().clone();
            currentItem.setAmount(1);
            updateInventory(player);
            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 1);
        } else {
            ItemStack item = event.getCurrentItem();

            if (item == null) {
                return;
            }

            if (item.equals(confirmButton)) {
                PlayerShop shop = new PlayerShop(Bukkit.getOfflinePlayer(player.getUniqueId()), location, currentItem, 10, 10.0, 10.0, false, false);
                boolean isCreated = shop.save();
                if (!isCreated) {
                    player.sendMessage("Due to an database error, the shop has not been created");
                    return;
                }
                SlabShop.shopManager.addPlayerShop(shop);
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 1);
                player.closeInventory();
                player.openInventory(shop.getInventory());

            } else if (item.equals(cancelButton)) {
                player.sendMessage("The shop creation has been cancelled");
                player.closeInventory();
            }
        }

    }

    public void updateInventory(Player player) {
        inventory.setItem(13, currentItem);
        //player.updateInventory(getInventory());
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
