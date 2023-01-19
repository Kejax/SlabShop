package net.kejax.slabshop.listeners;

import net.kejax.slabshop.SlabShop;
import net.kejax.slabshop.inventories.CreateShopInventory;
import net.kejax.slabshop.inventories.ShopGuiInventory;
import net.kejax.slabshop.shops.PlayerShop;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Slab;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClickListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event) {

        Action action = event.getAction();
        Player player = event.getPlayer();

        //Check if the item in hand is a Stick

        //Check if a block is right-clicked
        if (action == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock() == null) {
                return;
            }
            Block block = event.getClickedBlock();

            if (!(block.getBlockData() instanceof Slab)) {
                return;
            }

            if (!(((Slab) block.getBlockData()).getType() == Slab.Type.BOTTOM)) {
                return;
            }

            PlayerShop shop = null;

            try {
                shop = SlabShop.shopManager.getPlayerShop(block.getLocation());
            } catch (NullPointerException ignored) {}

            //Check if player is sneaking
            if (player.isSneaking()) {
                if (event.getItem() != null && event.getItem().getType() == Material.STICK) {
                    if (shop == null) {
                        player.openInventory(new CreateShopInventory(player, block.getLocation()).getInventory());
                    }
                }
            }

            if (shop != null) {
                shop.openInventory(player);
            }
        }

    }
}
