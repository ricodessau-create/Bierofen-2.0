package de.bierrang.plugin.listener;

import de.bierrang.plugin.BierOfen;
import de.bierrang.plugin.furnace.FurnaceManager;
import de.bierrang.plugin.storage.StorageManager;
import de.bierrang.plugin.gui.UpgradeGUI;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class FurnaceListener implements Listener {

    private final BierOfen plugin;
    private final FurnaceManager manager;
    private final StorageManager storage;

    public FurnaceListener(BierOfen plugin, FurnaceManager manager, StorageManager storage) {
        this.plugin = plugin;
        this.manager = manager;
        this.storage = storage;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Block b = e.getBlockPlaced();

        if (b.getType() == Material.FURNACE || b.getType() == Material.BLAST_FURNACE || b.getType() == Material.SMOKER) {
            storage.setLevel(b, 1);
            e.getPlayer().sendMessage(plugin.msg("furnace-placed").replace("%level%", "1"));
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Block b = e.getBlock();

        if (storage.exists(b)) {
            storage.remove(b);
            e.getPlayer().sendMessage(plugin.msg("furnace-broken"));
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;

        Block b = e.getClickedBlock();
        Player p = e.getPlayer();

        if (!storage.exists(b)) return;

        new UpgradeGUI(plugin, manager, b).open(p);
    }
}
