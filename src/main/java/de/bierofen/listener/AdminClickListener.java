package de.bierofen.listener;

import de.bierofen.BierOfen;
import de.bierofen.furnace.FurnaceManager;
import de.bierofen.gui.AdminGUI;
import de.bierofen.upgrade.FurnaceContext;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class AdminClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getInventory().getHolder() instanceof AdminGUI)) return;

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        Block furnace = FurnaceContext.getLastFurnace(p);

        if (furnace == null) {
            p.sendMessage("§cKein Ofen ausgewählt.");
            return;
        }

        FurnaceManager fm = BierOfen.getInstance().getFurnaceManager();
        int level = fm.getLevel(furnace);

        switch (e.getSlot()) {

            case 11: // +1
                fm.setLevel(furnace, level + 1);
                p.sendMessage("§aLevel erhöht auf §e" + (level + 1));
                break;

            case 15: // -1
                if (level > 1) {
                    fm.setLevel(furnace, level - 1);
                    p.sendMessage("§cLevel verringert auf §e" + (level - 1));
                } else {
                    p.sendMessage("§cLevel kann nicht unter 1 fallen.");
                }
                break;

            case 13: // Reset
                fm.setLevel(furnace, 1);
                p.sendMessage("§4Ofen zurückgesetzt auf Level 1.");
                break;

            default:
                break;
        }

        p.closeInventory();
    }
}
