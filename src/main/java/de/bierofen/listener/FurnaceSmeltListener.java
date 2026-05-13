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

    /**
     * SPEED-FIX: Reduziert die Kochzeit (Ticks) anhand des Level-Bonus.
     * Vanilla-Kochzeit: 200 Ticks (Ofen), 100 Ticks (Blast/Smoker).
     * Mit 50 % Speed-Bonus → dividiert durch 1,5 → 133 bzw. 67 Ticks.
     */
    @EventHandler
    public void onStartSmelt(FurnaceStartSmeltEvent e) {
        Block block = e.getBlock();
        int level = fm.getLevel(block);
        double speedBonus = fm.getSpeedBonus(level);
        if (speedBonus <= 0) return;

        int reduced = (int) (e.getTotalCookTime() / (1.0 + speedBonus));
        e.setTotalCookTime(Math.max(1, reduced));
    }

    /** DROP-BONUS: Zufällige Extra-Drops nach dem Schmelzen. */
    @EventHandler
    public void onSmelt(FurnaceSmeltEvent e) {
        Block block = e.getBlock();
        int level = fm.getLevel(block);
        int chance = fm.getBonusChance(level);
        if (chance <= 0) return;

        if (Math.random() * 100 < chance) {
            ItemStack bonus = e.getResult().clone();
            bonus.setAmount(fm.getBonusDropsAmount());
            block.getWorld().dropItemNaturally(block.getLocation().add(0.5, 1, 0.5), bonus);
        }
    }
}
