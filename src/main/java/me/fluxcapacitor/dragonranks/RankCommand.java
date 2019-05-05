package me.fluxcapacitor.dragonranks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("rank")) {
            Player player;
            if (args.length > 0) {
                //Get player specified in args[0]
                player = Bukkit.getPlayer(args[0]);
            } else {
                if (sender instanceof Player) {
                    player = (Player) sender;
                } else {
                    Utils.log(ChatColor.RED + "You must be specify a player to use this command in the console.");
                    return true;
                }
            }

            if (player == null) {
                Utils.sendMessage(sender, ChatColor.RED + "That player could not be found.");
                return true;
            }

            //Now we have a player object to look at...
            //Get their rank
            String rank = RankupCommand.getCurrentRank(player);
            String needToRankUp = RankupCommand.econ.format(0.0);
            if (RankupCommand.getRankIndex(rank) != (Ladder.ladder.length - 1)) {
                needToRankUp = RankupCommand.econ.format(Math.max(Ladder.costs[RankupCommand.getRankIndex(RankupCommand.getCurrentRank(player))] - RankupCommand.econ.getBalance(player), 0));
            }
            String nextRank = (RankupCommand.getRankIndex(rank) != (Ladder.ladder.length - 1)) ? Ladder.ladder[RankupCommand.getRankIndex(rank) + 1] : null;

            if (player.getName() == sender.getName()) {
                //Use "you"
                Utils.sendMessage(sender, ChatColor.AQUA + "Your current rank is " + ChatColor.WHITE + rank + ChatColor.AQUA + ".");
                if (nextRank != null)
                    Utils.sendMessage(sender, ChatColor.AQUA + "You need " + ChatColor.WHITE + needToRankUp + ChatColor.AQUA + " more to rank up.");
                if (nextRank == null) Utils.sendMessage(sender, ChatColor.AQUA + "You are at the last rank.");
                else
                    Utils.sendMessage(sender, ChatColor.AQUA + "Your next rank is " + ChatColor.WHITE + nextRank + ChatColor.AQUA + ".");
            } else {
                //Use "they"
                Utils.sendMessage(sender, ChatColor.AQUA + player.getName() + "'s current rank is " + ChatColor.WHITE + rank + ChatColor.AQUA + ".");
                if (nextRank != null)
                    Utils.sendMessage(sender, ChatColor.AQUA + "They need " + ChatColor.WHITE + needToRankUp + ChatColor.AQUA + " more to rank up.");
                if (nextRank == null) Utils.sendMessage(sender, ChatColor.AQUA + "They are at the last rank.");
                else
                    Utils.sendMessage(sender, ChatColor.AQUA + "Their next rank is " + ChatColor.WHITE + nextRank + ChatColor.AQUA + ".");
            }
            return true;
        }
        return false;
    }
}
