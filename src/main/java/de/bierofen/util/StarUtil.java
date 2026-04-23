package de.bierofen.util;

public class StarUtil {

    public static String getStars(int level) {
        int max = 5;
        int clamped = Math.max(1, Math.min(level, max));

        StringBuilder sb = new StringBuilder("§6[");
        for (int i = 1; i <= max; i++) {
            if (i <= clamped) sb.append("★");
            else sb.append("☆");
        }
        sb.append("§6]");
        return sb.toString();
    }
}
