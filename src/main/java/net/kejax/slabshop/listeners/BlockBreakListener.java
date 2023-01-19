package net.kejax.slabshop.listeners;

import net.kejax.slabshop.SlabShop;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void BlockFormEvent(BlockFormEvent event) {

        if (SlabShop.shopManager.getPlayerShop(event.getBlock().getLocation()) != null) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void BlockExplodeEvent(BlockExplodeEvent event) {

        event.blockList().removeIf(block -> SlabShop.shopManager.getPlayerShop(block.getLocation()) != null);

    }

    @EventHandler
    public void EntityExplodeEvent(EntityExplodeEvent event) {

        event.blockList().removeIf(block -> SlabShop.shopManager.getPlayerShop(block.getLocation()) != null);

    }

    @EventHandler
    public void BlockPistonRetractEvent(BlockPistonRetractEvent event) {

        for (Block block : event.getBlocks()) {
            if (SlabShop.shopManager.getPlayerShop(block.getLocation()) != null) {
                event.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void BlockPistonExtendEvent(BlockPistonExtendEvent event) {

        for (Block block : event.getBlocks()) {
            if (SlabShop.shopManager.getPlayerShop(block.getLocation()) != null) {
                event.setCancelled(true);
            }
        }

    }

    @EventHandler
    void BlockBurnEvent(BlockBurnEvent event) {
        if (SlabShop.shopManager.getPlayerShop(event.getBlock().getLocation()) != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    void BlockBreakEvent(BlockBreakEvent event) {

        if (SlabShop.shopManager.getPlayerShop(event.getBlock().getLocation()) != null) {
            event.setCancelled(true);
        }

    }
}
