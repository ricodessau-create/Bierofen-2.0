package de.bierrang.plugin.listener;

import de.bierrang.plugin.BierOfen;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiClickListener implements Listener {

    private final BierOfen plugin;

    public GuiClickListener(BierOfen plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getView().getTitle() == null) return;

        String title = e.getView().getTitle();

        if (title.equals(plugin.getConfig().getString("gui.upgrade.title"))
                || title.equals(plugin.getConfig().getString("gui.admin.title"))
                || title.equals(plugin.getConfig().getString("gui.wiki.title"))) {
            e.setCancelled(true);
        }
    }
}
