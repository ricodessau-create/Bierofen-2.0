package de.bierrang.plugin.furnace;

import de.bierrang.plugin.BierOfen;
import de.bierrang.plugin.storage.StorageManager;
import org.bukkit.block.Block;

public class FurnaceManager {

    private final BierOfen plugin;
    private final StorageManager storage;

    public FurnaceManager(BierOfen plugin, StorageManager storage) {
        this.plugin = plugin;
        this.storage = storage;
    }

    public int getLevel(Block block) {
        return storage.getLevel(block);
    }

    public void setLevel(Block block, int level) {
        storage.setLevel(block, level);
    }

    public int getCost(int level) {
        return plugin.getConfig().getInt("levels.cost." + level);
    }

    public int getSpeedBonus(int level) {
        return plugin.getConfig().getInt("levels.speed." + level);
    }

    public int getDropBonus(int level) {
        return plugin.getConfig().getInt("levels.drop." + level);
    }

    public boolean isMaxLevel(int level) {
        return level >= plugin.getConfig().getInt("settings.max-level");
    }
}
