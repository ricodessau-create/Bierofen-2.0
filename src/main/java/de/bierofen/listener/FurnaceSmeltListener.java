package de.bierofen.listener;

import de.bierofen.BierOfen;
import de.bierofen.furnace.FurnaceManager;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class FurnaceSmeltListener implements Listener {

    @EventHandler
    public void onSmelt(FurnaceSmeltEvent e) {

        Block block = e.getBlock();

        // BlockState casten
        if (!(block.getState() instanceof Furnace)) {
            return;
        }

        Furnace furnace = (Furnace) block.getState();
        FurnaceInventory inv = furnace.getInventory();

        FurnaceManager fm = BierOfen.getInstance().getFurnaceManager();
        int level = fm.getLevel(block);

        if (level <= 1) return;

        int chance = BierOfen.getInstance().getConfig().getInt("levels.drop." + level, 0);
        if (chance <= 0) return;

        // Chance prüfen
        if (ThreadLocalRandom.current().nextInt(100) >= chance) return;

        // Extra-Drops: 1 bis 4
        int extra = ThreadLocalRandom.current().nextInt(1, 5);

        ItemStack result = e.getResult().clone();
        result.setAmount(extra);

        // Extra-Drops ins Ergebnisfach legen
        inv.addItem(result);
    }
}
