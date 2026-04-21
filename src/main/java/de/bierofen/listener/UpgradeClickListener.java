package de.bierofen.listener;

import de.bierofen.BierOfen;
import de.bierofen.furnace.FurnaceManager;
import de.bierofen.gui.UpgradeGUI;
import de.bierofen.upgrade.FurnaceContext;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class UpgradeClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (!(e.getInventory().getHolder() instanceof UpgradeGUI)) return;

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        Block furnace = FurnaceContext.getLastFurnace(p);

        if (furnace == null) {
            p.sendMessage("§cKein Ofen ausgewählt.");
            return;
        }

        FurnaceManager fm = BierOfen.getInstance().getFurnaceManager();
        int level = fm.getLevel(furnace);

        int next = level + 1;

        // Max-Level prüfen
        if (next > BierOfen.getInstance().getConfig().getInt("settings.max-level")) {
            p.sendMessage("§eDieser Ofen ist bereits auf dem Max-Level.");
            return;
        }

        // Kosten aus Config
        int cost = BierOfen.getInstance().getConfig().getInt("levels.cost." + next);

        // Admin kostenlos?
        boolean adminFree = BierOfen.getInstance().getConfig().getBoolean("settings.allow-admin-free-upgrade")
                && p.hasPermission("bierofen.admin");

        // Economy vorhanden?
        Economy econ = BierOfen.econ;

        if (!adminFree && econ != null) {

            // Geld prüfen
            if (!econ.has(p, cost)) {
                p.sendMessage("§cDu hast nicht genug Geld.");
                return;
            }

            // Geld abziehen
            econ.withdrawPlayer(p, cost);
        }

        // Level erhöhen
        fm.setLevel(furnace, next);

        p.sendMessage("§aDein Ofen wurde auf Level §e" + next + " §aupgegradet!");

        p.closeInventory();
    }
}
