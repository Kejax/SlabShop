package net.kejax.slabshop.database;

import net.kejax.slabshop.SlabShop;
import net.kejax.slabshop.shops.PlayerShop;
import net.kejax.slabshop.utils.BukkitSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.UUID;

public class SQLite{

    private Connection conn;

    public SQLite() {
    }

    public void connect() {

        File directory = SlabShop.plugin.getDataFolder();

        if (!directory.exists()) {
            directory.mkdir();
        }

        String path = directory.getAbsolutePath() + "/database.db";

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + path);

            Statement stmt = conn.createStatement();

            stmt.execute("CREATE TABLE IF NOT EXISTS settings (" +
                    "key TEXT UNIQUE," +
                    "value TEXT" +
                    ")"
            );


            stmt.execute("CREATE TABLE IF NOT EXISTS player_shops (" +
                    "owner TEXT," +
                    "world TEXT," +
                    "x INT," +
                    "y INT," +
                    "z INT," +
                    "item TEXT," +
                    "buy_price REAL," +
                    "sell_price REAl," +
                    "buy_only BOOL," +
                    "sell_only BOOL," +
                    "amount INT," +
                    "UNIQUE ( x, y, z)" +
                    ")"
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public HashMap<Location, PlayerShop> loadAllPlayerShopsLocations() throws SQLException {
        String sql = "SELECT owner, world, x, y, z, item, buy_price, sell_price, buy_only, sell_only, amount FROM player_shops";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        HashMap<Location, PlayerShop> playerShops = new HashMap<>();
        PlayerShop playerShop;

        OfflinePlayer player;
        Location location;
        ItemStack itemStack;
        double buyPrice;
        double sellPrice;
        boolean buyOnly;
        boolean sellOnly;
        int amount;

        while (resultSet.next()) {

            player = Bukkit.getOfflinePlayer(UUID.fromString(resultSet.getString("owner")));
            location = new Location(
                    Bukkit.getWorld(
                            resultSet.getString("world")
                    ),
                    resultSet.getDouble("x"),
                    resultSet.getDouble("y"),
                    resultSet.getDouble("z")
            );
            itemStack = BukkitSerializer.deserializeItemStack(resultSet.getString("item"));
            buyPrice = resultSet.getDouble("buy_price");
            sellPrice = resultSet.getDouble("sell_price");
            buyOnly = resultSet.getBoolean("buy_only");
            sellOnly = resultSet.getBoolean("sell_only");
            amount = resultSet.getInt("amount");

            playerShop = new PlayerShop(player, location, itemStack, amount, buyPrice, sellPrice, buyOnly, sellOnly);
            playerShops.put(location, playerShop);
        }

        return playerShops;
    }

    public HashMap<OfflinePlayer, PlayerShop> loadAllPlayerShopsOfflinePlayer() throws SQLException {
        String sql = "SELECT owner, world, x, y, z, item, buy_price, sell_price, buy_only, sell_only, amount FROM player_shops";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        HashMap<OfflinePlayer, PlayerShop> playerShops = new HashMap<>();
        PlayerShop playerShop;

        OfflinePlayer player;
        Location location;
        ItemStack itemStack;
        double buyPrice;
        double sellPrice;
        boolean buyOnly;
        boolean sellOnly;
        int amount;

        while (resultSet.next()) {

            player = Bukkit.getOfflinePlayer(UUID.fromString(resultSet.getString("owner")));
            location = new Location(
                    Bukkit.getWorld(
                            resultSet.getString("world")
                    ),
                    resultSet.getDouble("x"),
                    resultSet.getDouble("y"),
                    resultSet.getDouble("z")
            );
            itemStack = BukkitSerializer.deserializeItemStack(resultSet.getString("item"));
            buyPrice = resultSet.getDouble("buy_price");
            sellPrice = resultSet.getDouble("sell_price");
            buyOnly = resultSet.getBoolean("buy_only");
            sellOnly = resultSet.getBoolean("sell_only");
            amount = resultSet.getInt("amount");

            playerShop = new PlayerShop(player, location, itemStack, amount, buyPrice, sellPrice, buyOnly, sellOnly);
            playerShops.put(player, playerShop);
        }

        return playerShops;
    }

    public void savePlayerShop(PlayerShop shop) throws SQLException {
        String sql = "INSERT INTO player_shops (owner, world, x, y, z, item, buy_price, sell_price, buy_only, sell_only, amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, shop.getOfflinePlayer().getUniqueId().toString()); // set Player
        preparedStatement.setString(2, shop.getLocation().getWorld().getName());
        preparedStatement.setInt(3, shop.getLocation().getBlockX()); // Set X
        preparedStatement.setInt(4, shop.getLocation().getBlockY()); // Set Y
        preparedStatement.setInt(5, shop.getLocation().getBlockZ()); // Set Z
        preparedStatement.setString(6, BukkitSerializer.serializeItemStack(shop.getItemStack()));
        preparedStatement.setDouble(7, shop.getBuyPrice());
        preparedStatement.setDouble(8, shop.getSellPrice());
        preparedStatement.setBoolean(9, shop.getBuyOnly());
        preparedStatement.setBoolean(10, shop.getSellOnly());
        preparedStatement.setInt(11, 0);

        preparedStatement.execute();
    }

    /*
     *   public PlayerShop loadPlayerShop(Location location) throws SQLException {
     *   String sql = "SELECT (owner, x, y, z, item, buy_price, sell_price, buy_only, sell_only, amount FROM player_shops WHERE x = ?, y = ?, z = ?";
     *   PreparedStatement preparedStatement = conn.prepareStatement(sql);
     *   preparedStatement.setInt(1, location.getBlockX());
     *   preparedStatement.setInt(2, location.getBlockY());
     *   preparedStatement.setInt(3, location.getBlockZ());
     *
     *   ResultSet resultSet = preparedStatement.executeQuery();

    }*/
}
