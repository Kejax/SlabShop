package net.kejax.slabshop;

import net.kejax.slabshop.commands.CommandManager;
import net.kejax.slabshop.database.SQLite;
import net.kejax.slabshop.listeners.ListenerManager;
import net.kejax.slabshop.shops.ShopManager;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class SlabShop extends JavaPlugin {

    public static final String PREFIX = ChatColor.BOLD.toString() + ChatColor.GOLD + "[SlabShop] " + ChatColor.RESET; //Global Prefix in Chat

    public static PluginManager pluginManager = Bukkit.getPluginManager(); //PluginManager

    public static final Logger logger = Logger.getLogger("Minecraft");

    public static SlabShop plugin;

    // Setup Vault
    public static Economy economy = null;
    public static Permission permission = null;
    public static Chat chat = null;

    // Manager
    public static ListenerManager listenerManager;
    public static CommandManager commandManager;
    public static ShopManager shopManager;

    // SQLite
    public static SQLite sqLite;

    @Override
    public void onLoad() {
        plugin = this;
        sqLite = new SQLite();
        sqLite.connect();

    }

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            logger.severe(String.format("[%s] Disabled due to no loaded Vault dependency!", getDescription().getName()));
            pluginManager.disablePlugin(this);
            return;
        }

        setupPermissions();
        setupChat();

        listenerManager = new ListenerManager();
        commandManager = new CommandManager();
        shopManager = new ShopManager();

        listenerManager.load();
        commandManager.load();
        shopManager.load();
    }

    @Override
    public void onDisable() {
        logger.info(String.format("[%s] Disabled Version v%s", getDescription().getName(), getDescription().getVersion()));
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return true;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp != null) {
            permission = rsp.getProvider();
            return true;
        }
        return false;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        if (rsp != null) {
            chat = rsp.getProvider();
            return true;
        }
        return false;
    }
}
