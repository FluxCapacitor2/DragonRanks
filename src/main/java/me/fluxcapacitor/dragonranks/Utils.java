package me.fluxcapacitor.dragonranks;

import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import static org.bukkit.Bukkit.getServer;

public class Utils {
    public static String PREFIX = ChatColor.DARK_AQUA + "DragonRanks" + ChatColor.DARK_GRAY + " >> " + ChatColor.RESET;
    public static Server server = getServer();
    public static ConsoleCommandSender consoleSender = server.getConsoleSender();
    public static PluginDescriptionFile pdf;

    public static void log(String message) {
        consoleSender.sendMessage(PREFIX + message);
    }

    public static void sendMessage(Player sender, String message) {
        sender.sendMessage(PREFIX + message);
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(PREFIX + message);
    }

    public static void sendMessage(Player sender, String message, boolean showPrefix) {
        if (showPrefix == false) sender.sendMessage(message);
    }

    public static void sendMessage(CommandSender sender, String message, boolean showPrefix) {
        if (showPrefix == false) sender.sendMessage(message);
    }

    public static LuckPermsApi luckPermsAPI() {
        return LuckPerms.getApi();
    }
}
