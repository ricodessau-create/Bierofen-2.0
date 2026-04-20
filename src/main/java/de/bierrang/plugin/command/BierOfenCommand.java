package de.bierrang.plugin.command;

import de.bierrang.plugin.BierOfen;
import de.bierrang.plugin.gui.AdminGUI;
import de.bierrang.plugin.gui.UpgradeGUI;
import de.bierrang.plugin.gui.WikiGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BierOfenCommand implements CommandExecutor {

    private final BierOfen plugin;

    public BierOfenCommand(BierOfen plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Nur Spieler können diesen Befehl nutzen.");
            return true;
        }

        Player p = (Player) sender;

        switch (cmd.getName().toLowerCase()) {

            case "bierofen":
                new UpgradeGUI(plugin).open(p);
                return true;

            case "bieradmin":
                new AdminGUI(plugin).open(p);
                return true;

            case "bierwiki":
                new WikiGUI(plugin).open(p);
                return true;
        }

        return true;
    }
}
