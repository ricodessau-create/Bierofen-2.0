package de.bierofen.upgrade;

import de.bierofen.BierOfen;
import de.bierofen.furnace.FurnaceManager;
import org.bukkit.block.Block;

public class UpgradeHandler {

    private final FurnaceManager manager;

    public UpgradeHandler() {
        this.manager = BierOfen.getInstance().getFurnaceManager();
    }

    public boolean upgrade(Block furnace) {
        int level = manager.getLevel(furnace);

        if (manager.isMaxLevel(level)) {
            return false;
        }

        int next = level + 1;
        manager.setLevel(furnace, next);
        return true;
    }
}
