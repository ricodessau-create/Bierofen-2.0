package de.bierofen.listener;

import de.bierofen.BierOfen;
import de.bierofen.storage.StorageManager;
import de.bierofen.upgrade.FurnaceContext;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class FurnaceListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Block b = e.getBlockPlaced();

        if (b.getType() == Material.FURNACE
                || b.getType() == Material.BLAST_FURNACE
                || b.getType() == Material.SMOKER) {

            StorageManager storage = BierOfen.getInstance().getStorageManager();
            storage.setLevel(b, 1);

            e.getPlayer().sendMessage(
                    BierOfen.getInstance().getConfig().getString("messages.furnace-placed")
                            .replace("%level%", "1")
            );
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Block b = e.getBlock();

        StorageManager storage = BierOfen.getInstance().getStorageManager();

        if (storage.exists(b)) {
            storage.remove(b);
            e.getPlayer().sendMessage(
                    BierOfen.getInstance().getConfig().getString("messages.furnace-broken")
            );
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;

        Block b = e.getClickedBlock();
        Player p = e.getPlayer();

        Material type = b.getType();

        if (type == Material.FURNACE
                || type == Material.BLAST_FURNACE
                || type == Material.SMOKER) {

            FurnaceContext.setLastFurnace(p, b);
        }
    }
}
