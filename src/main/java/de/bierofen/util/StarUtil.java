package de.bierofen.util;

import de.bierofen.BierOfen;
import org.bukkit.Material;

public class StarUtil {

    public static String getStars(int level) {
        int max = BierOfen.getInstance().getConfig().getInt("settings.max-level", 5);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level;  i++) sb.append("§6★");
        for (int i = level; i < max; i++) sb.append("§8★");
        return sb.toString();
    }

    public static String getFurnaceName(int level, Material type) {
        String typeName = switch (type) {
            case BLAST_FURNACE -> "Hochofen";
            case SMOKER        -> "Räucherofen";
            default            -> "Ofen";
        };
        return getStars(level) + " §e" + typeName;
    }
}
