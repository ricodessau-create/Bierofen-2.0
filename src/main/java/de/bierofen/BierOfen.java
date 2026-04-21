package de.bierofen;

import de.bierofen.command.BierOfenCommand;
import de.bierofen.listener.GuiClickListener;
import de.bierofen.listener.FurnaceListener;
import de.bierofen.listener.UpgradeClickListener;
import de.bierofen.listener.AdminClickListener;
import de.bierofen.listener.WikiClickListener;
import de.bierofen.listener.FurnaceSmeltListener;
import de.bierofen.storage.StorageManager;
import de.bierofen.furnace.FurnaceManager;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class BierOfen extends JavaPlugin {

    private static BierOfen instance;
    public static Economy econ = null;

    private StorageManager storageManager;
    private FurnaceManager furnaceManager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        setupEconomy();

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
        Bukkit.getPluginManager().registerEvents(new FurnaceSmeltListener(), this);

        getLogger().info("BierOfen aktiviert.");
    }

    @Override
    public void onDisable() {
        getLogger().info("BierOfen deaktiviert.");
    }

    private void setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            getLogger().warning("Vault nicht gefunden! Economy deaktiviert.");
            return;
        }

        RegisteredServiceProvider<Economy> rsp =
                getServer().getServicesManager().getRegistration(Economy.class);

        if (rsp == null) {
            getLogger().warning("Kein Economy-Provider gefunden!");
            return;
        }

        econ = rsp.getProvider();
        getLogger().info("Economy erfolgreich geladen: " + econ.getName());
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
