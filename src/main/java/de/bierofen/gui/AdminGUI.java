package de.bierofen.gui;

import de.bierofen.BierOfen;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class AdminGUI implements InventoryHolder {

    private Inventory inv;

    public void open(Player p) {
        this.inv = Bukkit.createInventory(this, 27,
                BierOfen.getInstance().getConfig().getString("gui.admin.title"));
        p.openInventory(inv);
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
