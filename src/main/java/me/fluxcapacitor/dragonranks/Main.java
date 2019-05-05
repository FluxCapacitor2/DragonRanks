package me.fluxcapacitor.dragonranks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public PluginDescriptionFile pdf = this.getDescription();

    @Override
    public void onEnable() {
        //Register commands
        Utils.log("Registering commands...");
        this.getCommand("dragonranks").setExecutor(new DragonRanksCommand());
        this.getCommand("rankup").setExecutor(new RankupCommand());
        this.getCommand("rank").setExecutor(new RankCommand());
        this.getCommand("ranks").setExecutor(new RanksCommand());
        Utils.log("Registering placeholders...");
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new RankPlaceholderHook().register();
        }
        Utils.log("Setting up economy...");
        RankupCommand.setupEconomy();
        Utils.log("Getting plugin info...");
        //Add our plugin info (from plugin.yml) to me.fluxcapacitor.dragonranks.Utils.pdf (for use in me.fluxcapacitor.dragonranks.DragonRanksCommand & others)
        Utils.pdf = pdf;
        //Tell the console that it worked :D
        Utils.log("Plugin enabled.");
    }

    @Override
    public void onDisable() {
        //Bye bye
        Utils.log("Plugin disabled.");
    }
}