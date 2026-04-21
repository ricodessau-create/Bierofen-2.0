package de.bierofen.manager;

import de.bierofen.BierOfen;
import org.bukkit.block.Block;

import java.util.Random;

public class FurnaceManager {

    private final BierOfen plugin;
    private final Random rnd = new Random();

    public FurnaceManager(BierOfen plugin) {
        this.plugin = plugin;
    }

    public int getLevel(Block block) {
        return block.getPersistentDataContainer().getOrDefault(
                plugin.key("level"),
                org.bukkit.persistence.PersistentDataType.INTEGER,
                1
        );
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
