package de.bierofen;

import de.bierofen.command.BierOfenCommand;
import de.bierofen.listener.GuiClickListener;
import de.bierofen.listener.FurnaceListener;
import de.bierofen.listener.UpgradeClickListener;
import de.bierofen.listener.AdminClickListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BierOfen extends JavaPlugin {

    private static BierOfen instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        // Commands
        getCommand("bierofen").setExecutor(new BierOfenCommand());
        getCommand("bieradmin").setExecutor(new BierOfenCommand());
        getCommand("bierwiki").setExecutor(new BierOfenCommand());

        // Listener
        Bukkit.getPluginManager().registerEvents(new GuiClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new FurnaceListener(), this);
        Bukkit.getPluginManager().registerEvents(new UpgradeClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new AdminClickListener(), this);

        getLogger().info("BierOfen 2.0 wurde aktiviert.");
    }

    @Override
    public void onDisable() {
        getLogger().info("BierOfen 2.0 wurde deaktiviert.");
    }

    public static BierOfen getInstance() {
        return instance;
    }
}
