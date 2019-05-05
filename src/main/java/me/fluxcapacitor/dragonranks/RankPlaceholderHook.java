package me.fluxcapacitor.dragonranks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class RankPlaceholderHook extends PlaceholderExpansion {
    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return Utils.pdf.getAuthors().toString();
    }

    @Override
    public String getIdentifier() {
        return "dragonranks";
    }

    @Override
    public String getVersion() {
        return "0.1";
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {
        Player onlinePlayer = (Player) player;
        // %dragonranks_rank% or %dragonranks_currentrank%
        if (identifier.equalsIgnoreCase("Rank") || identifier.equalsIgnoreCase("CurrentRank")) {
            return RankupCommand.getCurrentRank(onlinePlayer);
        }

        // %dragonranks_nextrank%
        if (identifier.equalsIgnoreCase("NextRank")) {
            return Ladder.ladder[RankupCommand.getRankIndex(RankupCommand.getCurrentRank(onlinePlayer))];
        }

        // %dragonranks_costtorankup%
        if (identifier.equalsIgnoreCase("CostToRankUp")) {
            return RankupCommand.econ.format(Math.max(Ladder.costs[RankupCommand.getRankIndex(RankupCommand.getCurrentRank(onlinePlayer))] - RankupCommand.econ.getBalance(player), 0));
        }

        // %dragonranks_totalrankupcost%
        if (identifier.equalsIgnoreCase("TotalRankUpCost")) {
            return RankupCommand.econ.format(Ladder.costs[RankupCommand.getRankIndex(RankupCommand.getCurrentRank(onlinePlayer))]);
        }

        return null;
    }
}
