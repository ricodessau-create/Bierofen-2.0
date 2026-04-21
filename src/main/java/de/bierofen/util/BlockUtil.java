package de.bierofen.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BlockUtil {

    public static String toKey(Block b) {
        Location l = b.getLocation();
        return l.getWorld().getName() + ":" + l.getBlockX() + "," + l.getBlockY() + "," + l.getBlockZ();
    }

    public static Block fromKey(String key) {
        try {
            String[] split = key.split(":");
            World w = Bukkit.getWorld(split[0]);

            String[] coords = split[1].split(",");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            int z = Integer.parseInt(coords[2]);

            return w.getBlockAt(x, y, z);
        } catch (Exception e) {
            return null;
        }
    }
}
