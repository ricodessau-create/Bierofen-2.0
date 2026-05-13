package de.bierofen.listener;

import de.bierofen.BierOfen;
import de.bierofen.furnace.FurnaceManager;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.FurnaceStartSmeltEvent;
import org.bukkit.inventory.ItemStack;

public class FurnaceSmeltListener implements Listener {

    private final FurnaceManager fm;

    public FurnaceSmeltListener() {
        this.fm = BierOfen.getInstance().getFurnaceManager();
    }

    // Speed-Fix: Kochzeit reduzieren statt Brennzeit erhöhen
    @EventHandler
    public void onStartSmelt(FurnaceStartSmeltEvent e) {
        Block block = e.getBlock();
        int level = fm.getLevel(block);
        double speedBonus = fm.getSpeedBonus(level);
        if (speedBonus <= 0) return;

        int reduced = (int) (e.getTotalCookTime() / (1.0 + speedBonus));
        e.setTotalCookTime(Math.max(1, reduced));
    }

    // Drop-Bonus: Bonus-Amount direkt auf das Result setzen → Trichter greift normal
    @EventHandler
    public void onSmelt(FurnaceSmeltEvent e) {
        Block block = e.getBlock();
        int level = fm.getLevel(block);
        int chance = fm.getBonusChance(level);
        if (chance <= 0) return;

        if (Math.random() * 100 < chance) {
            int bonusAmount = fm.getBonusDropsAmount();
            ItemStack result = e.getResult().clone();
            // Bonus auf bestehende Menge draufrechnen, nicht neu droppen
            result.setAmount(result.getAmount() + bonusAmount);
            e.setResult(result);
        }
    }
}
