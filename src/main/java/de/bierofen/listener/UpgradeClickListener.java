package de.bierofen.listener;

import de.bierofen.BierOfen;
import de.bierofen.furnace.FurnaceManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class UpgradeClickListener implements Listener {

    private final FurnaceManager fm;

    public UpgradeClickListener() {
        this.fm = BierOfen.getInstance().getFurnaceManager();
    }

    private boolean isValidFurnace(Material type) {
        return type == Material.FURNACE ||
               type == Material.BLAST_FURNACE ||
               type == Material.SMOKER;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        if (!(e.getWhoClicked() instanceof Player player)) return;

        Block block = BierOfen.getInstance().getStorageManager().getSelectedFurnace(player);
        if (block == null) {
            player.sendMessage("§cBitte klicke zuerst einen Ofen an.");
            return;
        }

        Material type = block.getType();
        if (!isValidFurnace(type)) {
            player.sendMessage("§cDas ist kein gültiger Ofen.");
            return;
        }

        int level = fm.getLevel(block);
        if (fm.isMaxLevel(level)) {
            player.sendMessage("§cMaximales Level erreicht.");
            return;
        }

        fm.setLevel(block, level + 1);
        player.sendMessage("§aOfen wurde verbessert!");
    }
}
