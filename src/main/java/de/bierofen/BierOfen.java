package de.bierofen;

import de.bierofen.command.BierOfenCommand;
import de.bierofen.listener.GuiClickListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BierOfen extends JavaPlugin {

    private static BierOfen instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        getCommand("bierofen").setExecutor(new BierOfenCommand());
        getCommand("bieradmin").setExecutor(new BierOfenCommand());
        getCommand("bierwiki").setExecutor(new BierOfenCommand());

        Bukkit.getPluginManager().registerEvents(new GuiClickListener(), this);

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
