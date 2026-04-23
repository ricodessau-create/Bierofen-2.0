package de.bierofen.listener;

import de.bierofen.BierOfen;
import de.bierofen.storage.StorageManager;
import de.bierofen.upgrade.FurnaceContext;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class FurnaceSelectListener implements Listener {

    private final StorageManager storage;

    public FurnaceSelectListener() {
        this.storage = BierOfen.getInstance().getStorageManager();
    }

    private boolean isValidFurnace(Material type) {
        return type == Material.FURNACE ||
               type == Material.BLAST_FURNACE ||
               type == Material.SMOKER;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getClickedBlock() == null) return;

        Block block = e.getClickedBlock();
        if (!isValidFurnace(block.getType())) return;

        Player player = e.getPlayer();
        storage.setSelectedFurnace(player, block);
        FurnaceContext.setLastFurnace(player, block);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        storage.clearSelectedFurnace(p);
        FurnaceContext.setLastFurnace(p, null);
    }
}
