package de.bierofen.listener;

import de.bierofen.BierOfen;
import de.bierofen.manager.FurnaceManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;

public class FurnaceListener implements Listener {

    private final FurnaceManager manager;

    public FurnaceListener() {
        this.manager = BierOfen.getInstance().getFurnaceManager();
    }

    @EventHandler
    public void onBurn(FurnaceBurnEvent e) {
        Block block = e.getBlock();
        int level = manager.getLevel(block);

        double speedBonus = manager.getSpeedBonus(level); // z.B. 1.20 für 120%

        Furnace furnace = (Furnace) block.getState();
        Material input = furnace.getInventory().getSmelting().getType();

        double multiplier = 1.0;

        // NORMALER OFEN
        if (block.getType() == Material.FURNACE) {
            multiplier = 1.0 + speedBonus;
        }

        // SMOKER → Essen schneller, Rest langsamer
        if (block.getType() == Material.SMOKER) {
            if (isFood(input)) {
                multiplier = 2.0 + speedBonus; // Essen schneller
            } else {
                multiplier = 0.5; // Debuff
            }
        }

        // BLAST FURNACE → Erze & Raw schneller, Essen langsamer
        if (block.getType() == Material.BLAST_FURNACE) {
            if (isOreOrRaw(input)) {
                multiplier = 2.0 + speedBonus;
            } else {
                multiplier = 0.5; // Debuff
            }
        }

        int newBurnTime = (int) (e.getBurnTime() * multiplier);
        e.setBurnTime(newBurnTime);
    }

    @EventHandler
    public void onSmelt(FurnaceSmeltEvent e) {
        Block block = e.getBlock();
        int level = manager.getLevel(block);

        // Loot Chance
        int bonus = manager.getBonusDrops(level);

        if (bonus > 0) {
            e.setResult(new org.bukkit.inventory.ItemStack(e.getResult().getType(), 1 + bonus));
        }
    }

    private boolean isFood(Material m) {
        return m.isEdible();
    }

    private boolean isOreOrRaw(Material m) {
        return m.name().endsWith("_ORE") ||
               m.name().startsWith("RAW_") ||
               m.name().endsWith("_INGOT");
    }
}
