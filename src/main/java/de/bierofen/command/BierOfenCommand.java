package de.bierofen.command;

import de.bierofen.gui.AdminGUI;
import de.bierofen.gui.UpgradeGUI;
import de.bierofen.gui.WWikiGUI;
import de.bierofen.upgrade.FurnaceContext;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BierOfenCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Nur Spieler können diesen Befehl nutzen.");
            return true;
        }

        Player p = (Player) sender;

        switch (cmd.getName().toLowerCase()) {

            case "bierofen":
                Block last = FurnaceContext.getLastFurnace(p);
                new UpgradeGUI(last).open(p);
                return true;

            case "bieradmin":
                new AdminGUI().open(p);
                return true;

            case "bierwiki":
                new WWikiGUI().open(p);
                return true;

            default:
                return true;
        }
    }
}
