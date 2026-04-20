package de.bierrang.plugin.gui;

import de.bierrang.plugin.BierOfen;
import de.bierrang.plugin.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class UpgradeGUI implements InventoryHolder {

    private final BierOfen plugin;
    private Inventory inv;

    public UpgradeGUI(BierOfen plugin) {
        this.plugin = plugin;
    }

    public void open(Player p) {
        this.inv = Bukkit.createInventory(this, 27, plugin.getConfig().getString("gui.upgrade.title"));
        fill(p);
        p.openInventory(inv);
    }

    private void fill(Player p) {
        int level = 1;
        int cost = plugin.getConfig().getInt("levels.cost." + level);
        int speed = plugin.getConfig().getInt("levels.speed." + level);
        int drop = plugin.getConfig().getInt("levels.drop." + level);

        inv.setItem(11, new ItemBuilder(Material.NETHER_STAR)
                .setName("§eLevel: §6" + level)
                .setLore(
                        "§7Speed: §e" + speed + "%",
                        "§7Drop: §e" + drop + "%",
                        "§7Kosten: §e" + cost + "$"
                )
                .build());

        inv.setItem(15, new ItemBuilder(Material.EMERALD)
                .setName("§aUpgrade durchführen")
                .setLore(
                        "§7Kosten: §e" + cost + "$",
                        "§7Klicke zum Upgraden."
                )
                .build());
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
