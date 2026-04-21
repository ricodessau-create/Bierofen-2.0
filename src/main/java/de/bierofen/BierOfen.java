package de.bierofen;

import de.bierofen.command.BierOfenCommand;
import de.bierofen.listener.GuiClickListener;
import de.bierofen.listener.FurnaceListener;
import de.bierofen.listener.UpgradeClickListener;
import de.bierofen.listener.AdminClickListener;
import de.bierofen.listener.WikiClickListener;
import de.bierofen.storage.StorageManager;
import de.bierofen.furnace.FurnaceManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BierOfen extends JavaPlugin {

    private static BierOfen instance;

    private StorageManager storageManager;
    private FurnaceManager furnaceManager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        storageManager = new StorageManager(getDataFolder());
        furnaceManager = new FurnaceManager(storageManager);

        getCommand("bierofen").setExecutor(new BierOfenCommand());
        getCommand("bieradmin").setExecutor(new BierOfenCommand());
        getCommand("bierwiki").setExecutor(new BierOfenCommand());

        Bukkit.getPluginManager().registerEvents(new GuiClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new FurnaceListener(), this);
        Bukkit.getPluginManager().registerEvents(new UpgradeClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new AdminClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new WikiClickListener(), this);

        getLogger().info("BierOfen aktiviert.");
    }

    @Override
    public void onDisable() {
        getLogger().info("BierOfen deaktiviert.");
    }

    public static BierOfen getInstance() {
        return instance;
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }

    public FurnaceManager getFurnaceManager() {
        return furnaceManager;
    }
}
