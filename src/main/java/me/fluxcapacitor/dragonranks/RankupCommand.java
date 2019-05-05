package me.fluxcapacitor.dragonranks;

import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.Node;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class RankupCommand implements CommandExecutor {
    public static Economy econ = null;

    public static String getCurrentRank(Player player) {
        String rank = Ladder.ladder[0];
        for (int i = 0; i < Ladder.ladder.length; i++) {
            if (player.hasPermission(Ladder.permissionPrefix + Ladder.ladder[i])) {
                rank = Ladder.ladder[i];
            }
        }
        return rank;
    }

    public static int getRankIndex(String rank) {
        int rankIndex = 0;
        for (int i = 0; i < Ladder.ladder.length; i++) {
            if (Ladder.ladder[i] == rank) {
                rankIndex = i;
            }
        }
        return rankIndex;
    }

    public static int rankUp(Player player) {
        try {
            //Get the player's current rank
            String rank = getCurrentRank(player);
            int rankIndex = getRankIndex(rank);
            if (rankIndex == Ladder.ladder.length) {
                return 2;
            }
            if (econ.getBalance(player) >= Ladder.costs[rankIndex]) {
                econ.withdrawPlayer(player, Ladder.costs[rankIndex]);
                //Rank them up by assigning the permission node 1 up from their current node
                UUID uuid = player.getUniqueId();
                LuckPermsApi api = Utils.luckPermsAPI();

                String permission = Ladder.permissionPrefix + Ladder.ladder[rankIndex + 1];
                Node node = api.getNodeFactory().newBuilder(permission).build();
                api.getUser(uuid).setPermission(node);
/*
                permission = "worldguard.bypass." + me.fluxcapacitor.dragonranks.Ladder.ladder[rankIndex + 1];
                node = api.getNodeFactory().newBuilder(permission).build();
                api.getUser(uuid).setPermission(node);*/
                return 0;
            } else {
                return 1;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return 2;
        }
    }

    public static boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        //if (command.getName().equalsIgnoreCase("rankup")) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;
            //me.fluxcapacitor.dragonranks.Utils.sendMessage(sender, ChatColor.AQUA + "Current rank: " + ChatColor.RESET + getCurrentRank((Player) sender));
            int result = rankUp(sender);
            if (result == 0) {
                Utils.sendMessage(sender, ChatColor.GREEN + "You have ranked up to " + getCurrentRank(sender) + " for " + econ.format(Ladder.costs[getRankIndex(getCurrentRank(sender)) - 1]) + "!");
            } else if (result == 1) {
                Utils.sendMessage(sender, ChatColor.RED + "You do not have enough money. You need " + econ.format(Ladder.costs[getRankIndex(getCurrentRank(sender))] - econ.getBalance(sender)) + " more to rank up.");
            } else if (result == 2) {
                Utils.sendMessage(sender, ChatColor.RED + "You already have the highest rank!");
            }
            return true;
        } else {
            Utils.sendMessage(commandSender, ChatColor.RED + "You must be a player to use this command!");
            return true;
        }
        //}
        //return false;
    }
}
