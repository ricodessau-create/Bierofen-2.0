package de.bierofen;

import de.bierofen.command.BierOfenCommand;
import de.bierofen.listener.GuiClickListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BierOfen extends JavaPlugin {

    private static BierOfen instance;

    // Diese Felder werden in Block 3 befüllt – aber NICHT mehr die Main-Klasse verändert
    private Object storageManager;
    private Object furnaceManager;

    @Override
    public void onEnable() {
        instance = this;

        // Config laden
        saveDefaultConfig();

        // Commands registrieren
        getCommand("bierofen").setExecutor(new BierOfenCommand());
        getCommand("bieradmin").setExecutor(new BierOfenCommand());
        getCommand("bierwiki").setExecutor(new BierOfenCommand());

        // Listener registrieren (nur GUI in Block 2)
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

    // Getter für spätere Blöcke (Storage, FurnaceManager)
    public Object getStorageManager() {
        return storageManager;
    }

    public Object getFurnaceManager() {
        return furnaceManager;
    }

    // Setter werden in Block 3 genutzt
    public void setStorageManager(Object storageManager) {
        this.storageManager = storageManager;
    }

    public void setFurnaceManager(Object furnaceManager) {
        this.furnaceManager = furnaceManager;
    }
}
