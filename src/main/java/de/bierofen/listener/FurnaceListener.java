package de.bierrang.plugin.listener;

import de.bierrang.plugin.BierOfen;
import de.bierrang.plugin.furnace.FurnaceManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;

public class FurnaceListener implements Listener {

    private final FurnaceManager manager;

    public FurnaceListener() {
        this.manager = BierOfen.getInstance().getFurnaceManager();
    }

    private boolean isValidFurnace(Material type) {
        return type == Material.FURNACE ||
               type == Material.BLAST_FURNACE ||
               type == Material.SMOKER;
    }

    @EventHandler
    public void onBurn(FurnaceBurnEvent e) {
        Block block = e.getBlock();
        Material type = block.getType();

        if (!isValidFurnace(type)) return;

        int level = manager.getLevel(block);
        double speedBonus = manager.getSpeedBonus(level);

        Furnace furnace = (Furnace) block.getState();
        ItemStack smelting = furnace.getInventory().getSmelting();
        Material input = smelting != null ? smelting.getType() : Material.AIR;

        double multiplier = 1.0;

        // NORMALER OFEN
        if (type == Material.FURNACE) {
            multiplier = 1.0 + speedBonus;
        }

        // SMOKER
        if (type == Material.SMOKER) {
            if (input.isEdible()) {
                multiplier = 2.0 + speedBonus;
            } else {
                multiplier = 0.5;
            }
        }

        // BLAST FURNACE
        if (type == Material.BLAST_FURNACE) {
            if (isOreOrRaw(input)) {
                multiplier = 2.0 + speedBonus;
            } else {
                multiplier = 0.5;
            }
        }

        e.setBurnTime((int) (e.getBurnTime() * multiplier));
    }

    @EventHandler
    public void onSmelt(FurnaceSmeltEvent e) {
        Block block = e.getBlock();
        Material type = block.getType();

        if (!isValidFurnace(type)) return;

        int level = manager.getLevel(block);
        int bonus = manager.getBonusDrops(level);

        if (bonus > 0) {
            e.setResult(new ItemStack(e.getResult().getType(), 1 + bonus));
        }
    }

    private boolean isOreOrRaw(Material m) {
        return m.name().endsWith("_ORE") ||
               m.name().startsWith("RAW_") ||
               m == Material.RAW_COPPER ||
               m == Material.RAW_IRON ||
               m == Material.RAW_GOLD;
    }
}
