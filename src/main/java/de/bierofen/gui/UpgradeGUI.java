package de.bierofen.gui;

import de.bierofen.BierOfen;
import de.bierofen.furnace.FurnaceManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class UpgradeGUI implements InventoryHolder {

    private Inventory inv;
    private final Block furnace;

    public UpgradeGUI(Block furnace) {
        this.furnace = furnace;
    }

    public void open(Player p) {
        this.inv = Bukkit.createInventory(this, 27,
                BierOfen.getInstance().getConfig().getString("gui.upgrade.title", "BierOfen Upgrade"));

        FurnaceManager fm = BierOfen.getInstance().getFurnaceManager();
        int level = fm.getLevel(furnace);

        ItemStack levelItem = new ItemStack(Material.NETHER_STAR);
        ItemMeta lm = levelItem.getItemMeta();
        lm.setDisplayName("§eLevel: §6" + level);
        lm.setLore(Arrays.asList(
                "§7Speed: §e" + BierOfen.getInstance().getConfig().getInt("levels.speed." + level, 0) + "%",
                "§7Drop: §e" + BierOfen.getInstance().getConfig().getInt("levels.drop." + level, 0) + "%"
        ));
        levelItem.setItemMeta(lm);
        inv.setItem(13, levelItem);

        int next = level + 1;
        int cost = BierOfen.getInstance().getConfig().getInt("levels.cost." + next, 0);

        ItemStack upgrade = new ItemStack(Material.EMERALD);
        ItemMeta um = upgrade.getItemMeta();
        um.setDisplayName("§aUpgrade durchführen");
        um.setLore(Arrays.asList(
                "§7Kosten: §e" + cost + "$",
                "§7Klicke zum Upgraden."
        ));
        upgrade.setItemMeta(um);
        inv.setItem(15, upgrade);

        if (fm.isMaxLevel(level)) {
            ItemStack max = new ItemStack(Material.BARRIER);
            ItemMeta mm = max.getItemMeta();
            mm.setDisplayName("§cMax-Level erreicht");
            mm.setLore(Arrays.asList("§7Dieser Ofen kann nicht weiter verbessert werden."));
            max.setItemMeta(mm);
            inv.setItem(15, max);
        }

        p.openInventory(inv);
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    public Block getFurnace() {
        return furnace;
    }
}
