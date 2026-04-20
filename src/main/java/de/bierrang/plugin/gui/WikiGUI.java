package de.bierrang.plugin.gui;

import de.bierrang.plugin.BierOfen;
import de.bierrang.plugin.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class WikiGUI implements InventoryHolder {

    private final BierOfen plugin;
    private Inventory inv;

    public WikiGUI(BierOfen plugin) {
        this.plugin = plugin;
    }

    public void open(Player p) {
        this.inv = Bukkit.createInventory(this, 27, plugin.getConfig().getString("gui.wiki.title"));
        fill();
        p.openInventory(inv);
    }

    private void fill() {
        inv.setItem(13, new ItemBuilder(Material.BOOK)
                .setName("§eWas ist ein BierOfen?")
                .setLore(
                        "§7Ein Ofen, der gelevelt werden kann.",
                        "§7Höheres Level = mehr Speed & Drops."
                )
                .build());
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
