package de.bierofen.listener;

import de.bierofen.BierOfen;
import de.bierofen.furnace.FurnaceManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;

public class FurnaceSmeltListener implements Listener {

    private boolean isOreOrRaw(Material m) {
        return m.name().endsWith("_ORE") ||
               m.name().startsWith("RAW_") ||
               m == Material.RAW_COPPER ||
               m == Material.RAW_IRON ||
               m == Material.RAW_GOLD;
    }

    @EventHandler
    public void onSmelt(FurnaceSmeltEvent e) {

        Block block = e.getBlock();

        if (!(block.getState() instanceof Furnace furnace)) {
            return;
        }

        FurnaceInventory inv = furnace.getInventory();

        FurnaceManager fm = BierOfen.getInstance().getFurnaceManager();
        int level = fm.getLevel(block);

        if (level <= 1) return;

        Material input = inv.getSmelting() != null ? inv.getSmelting().getType() : Material.AIR;
        if (!isOreOrRaw(input)) return;

        int chance = fm.getBonusChance(level);
        if (chance <= 0) return;

        if (BierOfen.getInstance().getServer().getRandom().nextInt(100) >= chance) return;

        int extra = fm.getBonusDropsAmount();

        ItemStack result = e.getResult().clone();
        result.setAmount(extra);

        inv.addItem(result);
    }
}
