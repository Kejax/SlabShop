package net.kejax.slabshop.abc;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public interface InventoryGui extends InventoryHolder {

    void onClick(InventoryClickEvent event);
}
