package de.bierofen.listener;

import de.bierofen.BierOfen;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getView().getTitle() == null) return;

        String title = e.getView().getTitle();

        if (title.equals(BierOfen.getInstance().getConfig().getString("gui.upgrade.title"))
                || title.equals(BierOfen.getInstance().getConfig().getString("gui.admin.title"))
                || title.equals(BierOfen.getInstance().getConfig().getString("gui.wiki.title"))) {

            e.setCancelled(true);
        }
    }
}
