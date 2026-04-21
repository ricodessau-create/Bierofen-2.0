package de.bierofen.upgrade;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FurnaceContext {

    private static final Map<UUID, Block> LAST_FURNACE = new HashMap<>();

    public static void setLastFurnace(Player player, Block block) {
        if (block == null) {
            LAST_FURNACE.remove(player.getUniqueId());
        } else {
            LAST_FURNACE.put(player.getUniqueId(), block);
        }
    }

    public static Block getLastFurnace(Player player) {
        return LAST_FURNACE.get(player.getUniqueId());
    }
}
