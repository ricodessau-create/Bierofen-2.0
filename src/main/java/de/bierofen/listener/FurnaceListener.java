package de.bierofen.listener;

import de.bierofen.BierOfen;
import de.bierofen.furnace.FurnaceManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
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

    private boolean isOreOrRaw(Material m) {
        return m.name().endsWith("_ORE") ||
               m.name().startsWith("RAW_") ||
               m == Material.RAW_COPPER ||
               m == Material.RAW_IRON ||
               m == Material.RAW_GOLD;
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

        if (type == Material.FURNACE) {
            multiplier = 1.0 + speedBonus;
        }

        if (type == Material.SMOKER) {
            if (input.isEdible()) {
                multiplier = 2.0 + speedBonus;
            } else {
                multiplier = 0.5; // Debuff bei falscher Befüllung
            }
        }

        if (type == Material.BLAST_FURNACE) {
            if (isOreOrRaw(input)) {
                multiplier = 2.0 + speedBonus;
            } else {
                multiplier = 0.5; // Debuff bei falscher Befüllung
            }
        }

        e.setBurnTime((int) (e.getBurnTime() * multiplier));
    }
}
