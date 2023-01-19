package net.kejax.slabshop.shops;

import net.kejax.slabshop.SlabShop;
import net.kejax.slabshop.abc.Manager;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.sql.SQLException;
import java.util.HashMap;

public class ShopManager implements Manager {

    private HashMap<Location, PlayerShop> playerShopsLocations;
    private HashMap<OfflinePlayer, PlayerShop> playerShopsOfflinePlayer;

    @Override
    public void load() {

        try {
            playerShopsLocations = SlabShop.sqLite.loadAllPlayerShopsLocations();
            playerShopsOfflinePlayer = SlabShop.sqLite.loadAllPlayerShopsOfflinePlayer();
        } catch (SQLException e) {
            System.out.println("[%s] Disabling due to corrupted database!");
            SlabShop.pluginManager.disablePlugin(SlabShop.plugin);
        }

    }

    @Override
    public void unLoad() {

    }

    public void addPlayerShop(PlayerShop playerShop) {
        playerShopsLocations.put(playerShop.getLocation(), playerShop);
        playerShopsOfflinePlayer.put(playerShop.getOfflinePlayer(), playerShop);
    }

    public PlayerShop getPlayerShop(Location location) {
        try {
            return playerShopsLocations.get(location);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public PlayerShop getPlayerShop(OfflinePlayer player) throws NullPointerException {
        try {
            return playerShopsOfflinePlayer.get(player);
        } catch (NullPointerException e) {
            return null;
        }
    }
}
