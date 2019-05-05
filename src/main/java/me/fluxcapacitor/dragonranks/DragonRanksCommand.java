package me.fluxcapacitor.dragonranks;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;


public class DragonRanksCommand implements CommandExecutor {
    public static boolean displayHelp(CommandSender sender) {
        //Display help
        Utils.sendMessage(sender, ChatColor.translateAlternateColorCodes('&', "&b/dragonranks version &3- Shows information about DragonRanks." +
                "\n&b/dragonranks help&3 - Show this page." +
                "\n&b/rank&3 - Check the rank of you or another player." +
                "\n&b/ranks&3 - Show all available ranks." +
                "\n&b/rankup&3 - Advance to the next rank."), false);
        return true;
    }

    public static boolean displayVersion(CommandSender sender) {
        PluginDescriptionFile pdf = Utils.pdf;
        Utils.sendMessage(sender, "Version " + ChatColor.GREEN + pdf.getVersion() + ChatColor.RESET + " by " + ChatColor.GREEN + pdf.getAuthors());
        return true;
    }

    public static boolean displayUsage(CommandSender sender) {
        Utils.sendMessage(sender, ChatColor.RED + "Usage: /dragonranks [help|version]", false);
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName();
        if (commandName.equalsIgnoreCase("dragonrankup") || commandName.equalsIgnoreCase("dru") || commandName.equalsIgnoreCase("dr") || commandName.equalsIgnoreCase("dragonranks")) {
            //Yup, that's ours. Handle it.
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("help")) {
                    return displayHelp(sender);
                } else if (args[0].equalsIgnoreCase("version")) {
                    //Display version
                    return displayVersion(sender);
                } else {
                    //Display usage
                    return displayUsage(sender);
                }
            } else {
                //Display info message
                return displayVersion(sender);
            }
        }
        //Not our problem, return it to some other plugin or class
        return false;
    }
}
