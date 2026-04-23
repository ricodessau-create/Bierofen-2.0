package de.bierofen.listener;

import de.bierofen.BierOfen;
import de.bierofen.furnace.FurnaceManager;
import de.bierofen.gui.AdminGUI;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class AdminClickListener implements Listener {

    private final FurnaceManager fm;
    private final BierOfen plugin;

    public AdminClickListener() {
        this.plugin = BierOfen.getInstance();
        this.fm = plugin.getFurnaceManager();
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
        if (!(e.getInventory().getHolder() instanceof AdminGUI)) return;

        e.setCancelled(true);

        Block block = plugin.getStorageManager().getSelectedFurnace(player);
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

        switch (e.getRawSlot()) {
            case 11 -> { // Level +1
                if (!plugin.getConfig().getBoolean("settings.allow-admin-free-upgrade", true)) return;
                if (!fm.isMaxLevel(level)) {
                    fm.setLevel(block, level + 1);
                    player.sendMessage(plugin.getConfig().getString("messages.admin-upgrade-success",
                            "§aDer Ofen wurde kostenlos auf Level §e%level% §agesetzt.")
                            .replace("%level%", String.valueOf(level + 1)));
                } else {
                    player.sendMessage(plugin.getConfig().getString("messages.max-level",
                            "§eDieser Ofen ist bereits auf dem Max-Level."));
                }
            }
            case 15 -> { // Level -1
                if (level > 1) {
                    fm.setLevel(block, level - 1);
                    player.sendMessage("§aLevel reduziert auf " + (level - 1));
                }
            }
            case 13 -> { // Reset
                fm.setLevel(block, 1);
                player.sendMessage("§cOfen auf Level 1 zurückgesetzt.");
            }
            default -> {
            }
        }
    }
}
