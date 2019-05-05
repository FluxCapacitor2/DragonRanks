package me.fluxcapacitor.dragonranks;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RanksCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("ranks")) {
            for (int i = 0; i < (Ladder.ladder.length - 1); i++) {
                Utils.sendMessage(commandSender, ((commandSender instanceof Player && RankupCommand.getCurrentRank((Player) commandSender) == Ladder.ladder[i]) ? ChatColor.YELLOW : ChatColor.WHITE) + Ladder.ladder[i] + ChatColor.AQUA + " -> " + ChatColor.WHITE + Ladder.ladder[i + 1] + ChatColor.GRAY + ": " + ChatColor.GREEN + RankupCommand.econ.format(Ladder.costs[i]), false);
            }
            return true;
        }
        return false;
    }
}
