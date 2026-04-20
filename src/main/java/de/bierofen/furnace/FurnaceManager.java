package de.bierofen.furnace;

import de.bierofen.BierOfen;
import de.bierofen.storage.StorageManager;
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

    public boolean isMaxLevel(int level) {
        return level >= plugin.getConfig().getInt("settings.max-level");
    }
}
