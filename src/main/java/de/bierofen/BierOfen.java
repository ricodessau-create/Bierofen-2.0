package de.bierofen;

import de.bierofen.command.BierOfenCommand;
import de.bierofen.listener.GuiClickListener;
import de.bierofen.listener.FurnaceListener;
import de.bierofen.listener.UpgradeClickListener;
import de.bierofen.listener.AdminClickListener;
import de.bierofen.listener.WikiClickListener;
import de.bierofen.listener.FurnaceSmeltListener;
import de.bierofen.listener.FurnaceOpenListener;
import de.bierofen.listener.FurnaceSelectListener;
import de.bierofen.storage.StorageManager;
import de.bierofen.furnace.FurnaceManager;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class BierOfen extends JavaPlugin {

    private static BierOfen instance;
    public static Economy econ = null;

    private StorageManager storageManager;
    private FurnaceManager furnaceManager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        setupEconomy();

        storageManager = new StorageManager(getDataFolder());
        furnaceManager = new FurnaceManager(storageManager);

        registerRawBlockRecipes();

        getCommand("bierofen").setExecutor(new BierOfenCommand());
        getCommand("bieradmin").setExecutor(new BierOfenCommand());
        getCommand("bierwiki").setExecutor(new BierOfenCommand());

        Bukkit.getPluginManager().registerEvents(new GuiClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new FurnaceListener(), this);
        Bukkit.getPluginManager().registerEvents(new FurnaceSmeltListener(), this);
        Bukkit.getPluginManager().registerEvents(new UpgradeClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new AdminClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new WikiClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new FurnaceOpenListener(), this);
        Bukkit.getPluginManager().registerEvents(new FurnaceSelectListener(), this);

        getLogger().info("BierOfen aktiviert.");
    }

    private void registerRawBlockRecipes() {
        addBlastAndFurnaceRecipe("raw_copper_block_smelt", Material.RAW_COPPER_BLOCK, Material.COPPER_BLOCK, 0.7f, 200);
        addBlastAndFurnaceRecipe("raw_iron_block_smelt",   Material.RAW_IRON_BLOCK,   Material.IRON_BLOCK,   0.7f, 200);
        addBlastAndFurnaceRecipe("raw_gold_block_smelt",   Material.RAW_GOLD_BLOCK,   Material.GOLD_BLOCK,   0.7f, 200);
    }

    private void addBlastAndFurnaceRecipe(String keyBase, Material input, Material output, float xp, int cookTime) {
        ItemStack result = new ItemStack(output);
        RecipeChoice choice = new RecipeChoice.MaterialChoice(input);

        BlastingRecipe blast = new BlastingRecipe(
                new NamespacedKey(this, keyBase + "_blast"), result, choice, xp, cookTime / 2
        );
        FurnaceRecipe furnace = new FurnaceRecipe(
                new NamespacedKey(this, keyBase + "_furnace"), result, choice, xp, cookTime
        );

        Bukkit.addRecipe(blast);
        Bukkit.addRecipe(furnace);
    }

    @Override
    public void onDisable() {
        getLogger().info("BierOfen deaktiviert.");
    }

    private void setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            getLogger().warning("Vault nicht gefunden! Economy deaktiviert.");
            return;
        }

        RegisteredServiceProvider<Economy> rsp =
                getServer().getServicesManager().getRegistration(Economy.class);

        if (rsp == null) {
            getLogger().warning("Kein Economy-Provider gefunden!");
            return;
        }

        econ = rsp.getProvider();
        getLogger().info("Economy erfolgreich geladen: " + econ.getName());
    }

    public static BierOfen getInstance() {
        return instance;
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }

    public FurnaceManager getFurnaceManager() {
        return furnaceManager;
    }
}
