package de.bierofen.listener;

import de.bierofen.BierOfen;
import de.bierofen.furnace.FurnaceManager;
import de.bierofen.util.StarUtil;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;

public class FurnaceOpenListener implements Listener {

    private final FurnaceManager fm;

    public FurnaceOpenListener() {
        this.fm = BierOfen.getInstance().getFurnaceManager();
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();
        if (!(holder instanceof Furnace furnace)) return;

        Block block = furnace.getBlock();
        int level = fm.getLevel(block);
        String title = StarUtil.getFurnaceName(level, block.getType());

        // Titel sofort im Inventar-Packet setzen (Paper 1.20+)
        e.getView().setTitle(title);

        // customName nachziehen, falls Ofen noch keinen hat (alte DB-Einträge)
        if (furnace.getCustomName() == null) {
            fm.applyCustomName(block, level);
        }
    }
}
