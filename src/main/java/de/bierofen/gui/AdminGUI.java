package de.bierofen.gui;

import de.bierofen.BierOfen;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class AdminGUI implements InventoryHolder {

    private Inventory inv;

    public void open(Player p) {
        this.inv = Bukkit.createInventory(this, 27,
                BierOfen.getInstance().getConfig().getString("gui.admin.title"));

        // Level +1
        inv.setItem(11, createItem(Material.EMERALD, "§aLevel +1",
                "§7Erhöht das Level des Ofens um 1"));

        // Level -1
        inv.setItem(15, createItem(Material.REDSTONE, "§cLevel -1",
                "§7Verringert das Level des Ofens um 1"));

        // Reset
        inv.setItem(13, createItem(Material.BARRIER, "§4Reset",
                "§7Setzt den Ofen auf Level 1 zurück"));

        // Info
        inv.setItem(22, createItem(Material.BOOK, "§eInfo",
                "§7Admin-Werkzeuge für BierOfen"));

        p.openInventory(inv);
    }

    private ItemStack createItem(Material m, String name, String... lore) {
        ItemStack i = new ItemStack(m);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        im.setLore(Arrays.asList(lore));
        i.setItemMeta(im);
        return i;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
