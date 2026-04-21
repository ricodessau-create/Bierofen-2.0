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

    public int getLevel(Block block) {
        return storage.getLevel(block);
    }

    public double getSpeedBonus(int level) {
        int value = plugin.getConfig().getInt("speed." + level);
        return value / 100.0;
    }

    public int getBonusDrops(int level) {
        int chance = plugin.getConfig().getInt("drop." + level);

        if (chance == 0) return 0;

        if (rnd.nextInt(100) < chance) {
            return 1 + rnd.nextInt(4); // 1–4 Extra Drops
        }
        return 0;
    }
}
