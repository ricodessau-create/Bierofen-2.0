package de.bierofen.listener;

import de.bierofen.gui.WikiGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class WikiClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getInventory().getHolder() instanceof WikiGUI)) return;

        e.setCancelled(true);
    }
}
