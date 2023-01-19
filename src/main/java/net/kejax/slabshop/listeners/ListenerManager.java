package net.kejax.slabshop.listeners;

import net.kejax.slabshop.SlabShop;
import net.kejax.slabshop.abc.Manager;

public class ListenerManager implements Manager {

    public ListenerManager() {
    }

    @Override
    public void load() {
        SlabShop.pluginManager.registerEvents(new ClickListener(), SlabShop.plugin);
        SlabShop.pluginManager.registerEvents(new InventoryListener(), SlabShop.plugin);
        SlabShop.pluginManager.registerEvents(new BlockBreakListener(), SlabShop.plugin);
    }

    @Override
    public void unLoad() {

    }
}
