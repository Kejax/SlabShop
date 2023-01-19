package net.kejax.slabshop.abc;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface Shop {

    public Inventory getInventory();

    public void openInventory(Player player);

}
