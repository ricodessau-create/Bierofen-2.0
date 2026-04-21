package de.bierrang.plugin.furnace;

import de.bierrang.plugin.BierOfen;
import de.bierrang.plugin.storage.StorageManager;
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
        int max = plugin.getConfig().getInt("max-level", 5);
        return level >= max;
    }

    // SPEED BONUS
    public double getSpeedBonus(int level) {
        int value = plugin.getConfig().getInt("speed." + level, 0);
        return value / 100.0;
    }

    // DROP BONUS
    public int getBonusDrops(int level) {
        int chance = plugin.getConfig().getInt("drop." + level, 0);

        if (chance == 0) return 0;

        if (rnd.nextInt(100) < chance) {
            return 1 + rnd.nextInt(4);
        }
        return 0;
    }
}
