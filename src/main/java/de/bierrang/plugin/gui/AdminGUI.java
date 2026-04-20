package de.bierrang.plugin.gui;

import de.bierrang.plugin.BierOfen;
import de.bierrang.plugin.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class AdminGUI implements InventoryHolder {

    private final BierOfen plugin;
    private Inventory inv;

    public AdminGUI(BierOfen plugin) {
        this.plugin = plugin;
    }

    public void open(Player p) {
        this.inv = Bukkit.createInventory(this, 27, plugin.getConfig().getString("gui.admin.title"));
        fill();
        p.openInventory(inv);
    }

    private void fill() {
        inv.setItem(13, new ItemBuilder(Material.BARRIER)
                .setName("§cAdmin-Funktionen folgen")
                .setLore("§7In dieser Version nur Platzhalter.")
                .build());
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
