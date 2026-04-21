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

public class WikiGUI implements InventoryHolder {

    private Inventory inv;

    public void open(Player p) {
        this.inv = Bukkit.createInventory(this, 27,
                BierOfen.getInstance().getConfig().getString("gui.wiki.title", "BierOfen Wiki"));

        inv.setItem(11, create(Material.PAPER, "§eLevel-Kosten",
                "§7Zeigt die Kosten pro Level an."));
        inv.setItem(13, create(Material.FURNACE, "§eSpeed-Bonus",
                "§7Zeigt den Geschwindigkeitsbonus pro Level."));
        inv.setItem(15, create(Material.IRON_INGOT, "§eDrop-Bonus",
                "§7Zeigt den Drop-Bonus pro Level."));
        inv.setItem(22, create(Material.BOOK, "§bBierOfen-Wiki",
                "§7Alle Infos über das BierOfen-System."));

        p.openInventory(inv);
    }

    private ItemStack create(Material m, String name, String... lore) {
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
