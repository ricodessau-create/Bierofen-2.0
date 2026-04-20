package de.bierrang.plugin;

import de.bierrang.plugin.command.BierOfenCommand;
import de.bierrang.plugin.listener.GuiClickListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BierOfen extends JavaPlugin {

    private static BierOfen instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        getCommand("bierofen").setExecutor(new BierOfenCommand(this));
        getCommand("bieradmin").setExecutor(new BierOfenCommand(this));
        getCommand("bierwiki").setExecutor(new BierOfenCommand(this));

        Bukkit.getPluginManager().registerEvents(new GuiClickListener(this), this);

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
