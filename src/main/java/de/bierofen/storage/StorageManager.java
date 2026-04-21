package de.bierofen.storage;

import de.bierofen.util.BlockUtil;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StorageManager {

    private final File file;
    private FileConfiguration cfg;

    private final Map<String, Integer> levels = new HashMap<>();

    public StorageManager(File dataFolder) {
        this.file = new File(dataFolder, "storage.yml");
        load();
    }

    public void load() {
        if (!file.exists()) {
            try { file.createNewFile(); } catch (IOException ignored) {}
        }

        cfg = YamlConfiguration.loadConfiguration(file);
        levels.clear();

        if (cfg.isConfigurationSection("furnaces")) {
            for (String key : cfg.getConfigurationSection("furnaces").getKeys(false)) {
                levels.put(key, cfg.getInt("furnaces." + key));
            }
        }
    }

    public void save() {
        cfg.set("furnaces", null);

        for (Map.Entry<String, Integer> e : levels.entrySet()) {
            cfg.set("furnaces." + e.getKey(), e.getValue());
        }

        try { cfg.save(file); } catch (IOException ignored) {}
    }

    public int getLevel(Block block) {
        return levels.getOrDefault(BlockUtil.toKey(block), 1);
    }

    public void setLevel(Block block, int level) {
        levels.put(BlockUtil.toKey(block), level);
        save();
    }

    public boolean exists(Block block) {
        return levels.contains
