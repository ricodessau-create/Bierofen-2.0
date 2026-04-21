package de.bierofen.listener;

import de.bierofen.BierOfen;
import de.bierofen.gui.UpgradeGUI;
import de.bierofen.upgrade.FurnaceContext;
import de.bierofen.upgrade.UpgradeHandler;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class UpgradeClickListener implements Listener {

    private final UpgradeHandler handler = new UpgradeHandler();

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getInventory().getHolder() instanceof UpgradeGUI)) return;

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();

        if (e.getSlot() != 15) return;

        Block furnace = FurnaceContext.getLastFurnace(p);

        if (furnace == null) {
            p.sendMessage(BierOfen.getInstance().getConfig().getString("messages.not-a-furnace"));
            return;
        }

        boolean success = handler.upgrade(furnace);

        if (!success) {
            p.sendMessage(BierOfen.getInstance().getConfig().getString("messages.max-level"));
            return;
        }

        int newLevel = BierOfen.getInstance().getFurnaceManager().getLevel(furnace);

        p.sendMessage(
                BierOfen.getInstance().getConfig().getString("messages.upgrade-success")
                        .replace("%level%", String.valueOf(newLevel))
        );

        p.closeInventory();
    }
}
