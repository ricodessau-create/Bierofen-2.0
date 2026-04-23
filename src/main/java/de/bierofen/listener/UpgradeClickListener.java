package de.bierofen.listener;

import de.bierofen.BierOfen;
import de.bierofen.furnace.FurnaceManager;
import de.bierofen.gui.UpgradeGUI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class UpgradeClickListener implements Listener {

    private final FurnaceManager fm;
    private final BierOfen plugin;

    public UpgradeClickListener() {
        this.plugin = BierOfen.getInstance();
        this.fm = plugin.getFurnaceManager();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        if (!(e.getWhoClicked() instanceof Player player)) return;
        if (!(e.getInventory().getHolder() instanceof UpgradeGUI gui)) return;

        e.setCancelled(true);

        // Nur Upgrade-Slot (15)
        if (e.getRawSlot() != 15) return;

        Block block = gui.getFurnace();
        if (block == null) {
            player.sendMessage("§cKein Ofen gefunden.");
            return;
        }

        int level = fm.getLevel(block);
        if (fm.isMaxLevel(level)) {
            player.sendMessage(plugin.getConfig().getString("messages.max-level", "§cMax-Level erreicht."));
            return;
        }

        int next = level + 1;
        int cost = plugin.getConfig().getInt("levels.cost." + next, 0);

        Economy econ = BierOfen.econ;
        if (econ == null) {
            // Fallback: kostenlos, wenn kein Vault
            fm.setLevel(block, next);
            player.sendMessage(plugin.getConfig().getString("messages.upgrade-success",
                    "§aDein Ofen wurde auf Level §e" + next + " §aupgegradet!")
                    .replace("%level%", String.valueOf(next)));
            player.closeInventory();
            return;
        }

        double balance = econ.getBalance(player);
        if (balance < cost) {
            player.sendMessage(plugin.getConfig().getString("messages.not-enough-money",
                    "§cDu hast nicht genug Geld."));
            return;
        }

        econ.withdrawPlayer(player, cost);
        fm.setLevel(block, next);

        String msg = plugin.getConfig().getString("messages.upgrade-success",
                "§aDein Ofen wurde auf Level §e%level% §aupgegradet!");
        player.sendMessage(msg.replace("%level%", String.valueOf(next)));

        player.closeInventory();
    }
}
