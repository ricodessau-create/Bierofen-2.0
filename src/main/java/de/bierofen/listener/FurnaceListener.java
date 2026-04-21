package de.bierofen.listener;

import de.bierofen.BierOfen;
import de.bierofen.furnace.FurnaceManager;
import de.bierofen.upgrade.FurnaceContext;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class FurnaceListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;

        Block b = e.getClickedBlock();
        Material type = b.getType();

        if (type == Material.FURNACE || type == Material.BLAST_FURNACE || type == Material.SMOKER) {
            FurnaceContext.setLastFurnace(e.getPlayer(), b);
        }
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent e) {
        Inventory inv = e.getInventory();
        Player p = (Player) e.getPlayer();

        switch (inv.getType()) {
            case FURNACE:
            case BLAST_FURNACE:
            case SMOKER:
                break;
            default:
                return;
        }

        Block furnace = FurnaceContext.getLastFurnace(p);
        if (furnace == null) return;

        FurnaceManager fm = BierOfen.getInstance().getFurnaceManager();
        int level = fm.getLevel(furnace);

        String stars = generateStars(level);

        InventoryView view = p.getOpenInventory();
        view.setTitle(stars + " BierOfen");
    }

    private String generateStars(int level) {
        if (level < 1) level = 1;
        if (level > 5) level = 5;

        StringBuilder sb = new StringBuilder("[");
        for (int i = 1; i <= 5; i++) {
            if (i <= level) sb.append("★");
            else sb.append("☆");
        }
        sb.append("] ");
        return sb.toString();
    }
}
