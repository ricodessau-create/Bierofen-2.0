package de.bierofen.furnace;

import de.bierofen.BierOfen;
import de.bierofen.storage.StorageManager;
import org.bukkit.block.Block;

import java.util.Random;

public class FurnaceManager {

    private final StorageManager storage;
    private final BierOfen plugin;
    private final Random rnd = new Random();

    public FurnaceManager(StorageManager storage) {
        this.storage = storage;
        this.plugin = BierOfen.getInstance();
    }

    // LEVEL SYSTEM
    public int getLevel(Block block) {
        return storage.getLevel(block);
    }

    public void setLevel(Block block, int level) {
        storage.setLevel(block, level);
    }

    public boolean isMaxLevel(int level) {
        int max = plugin.getConfig().getInt("settings.max-level", 5);
        return level >= max;
    }

    // SPEED BONUS (in % aus config.levels.speed.X)
    public double getSpeedBonus(int level) {
        int value = plugin.getConfig().getInt("levels.speed." + level, 0);
        return value / 100.0;
    }

    // DROP BONUS (Chance in % aus config.levels.drop.X)
    public int getBonusChance(int level) {
        return plugin.getConfig().getInt("levels.drop." + level, 0);
    }

    // Anzahl Extra-Drops (1–4)
    public int getBonusDropsAmount() {
        return 1 + rnd.nextInt(4);
    }
}
