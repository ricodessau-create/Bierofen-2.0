package de.bierofen.furnace;

import de.bierofen.BierOfen;
import de.bierofen.storage.StorageManager;
import de.bierofen.util.StarUtil;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;

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
        applyCustomName(block, level);   // <-- NEU: Sterne am Block setzen
    }

    /** Setzt den sichtbaren Ofennamen (Sterne) direkt am Block-Entity. */
    public void applyCustomName(Block block, int level) {
        if (block.getState() instanceof Furnace furnace) {
            furnace.setCustomName(StarUtil.getFurnaceName(level, block.getType()));
            furnace.update();
        }
    }

    // MAX-LEVEL
    public boolean isMaxLevel(int level) {
        int max = plugin.getConfig().getInt("settings.max-level", 5);
        return level >= max;
    }

    // SPEED BONUS  (Dezimalwert, z. B. 0,5 für 50 %)
    public double getSpeedBonus(int level) {
        int value = plugin.getConfig().getInt("levels.speed." + level, 0);
        return value / 100.0;
    }

    // DROP BONUS (Chance in %)
    public int getBonusChance(int level) {
        return plugin.getConfig().getInt("levels.drop." + level, 0);
    }

    // Extra-Drops (1–4)
    public int getBonusDropsAmount() {
        return 1 + rnd.nextInt(4);
    }
}
