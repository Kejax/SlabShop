package net.kejax.slabshop.listeners;

import net.kejax.slabshop.abc.InventoryGui;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Inventory inventory = event.getInventory();

        InventoryAction action = event.getAction();

        InventoryHolder inventoryHolder = inventory.getHolder();

        if (inventoryHolder instanceof InventoryGui) {
            ((InventoryGui) inventoryHolder).onClick(event);
        }

    }
}
