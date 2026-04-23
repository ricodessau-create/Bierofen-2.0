package de.bierofen.listener;

import de.bierofen.gui.AdminGUI;
import de.bierofen.gui.UpgradeGUI;
import de.bierofen.gui.WikiGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;

        // Nur unsere GUIs blockieren – NICHT den Ofen!
        if (e.getInventory().getHolder() instanceof AdminGUI ||
            e.getInventory().getHolder() instanceof UpgradeGUI ||
            e.getInventory().getHolder() instanceof WikiGUI) {

            e.setCancelled(true);
        }
    }
}
