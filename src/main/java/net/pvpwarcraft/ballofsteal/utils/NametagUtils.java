package net.pvpwarcraft.ballofsteal.utils;

import net.pvpwarcraft.ballofsteal.player.GamePlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class NametagUtils {

    public Team removeName(Player player) {
        Team team = GameUtils.scoreboard.getPlayerTeam(player);
        if (team != null) {
            team.unregister();
        }
        return team;
    }

    public void updateName(Player player){
        Team team = removeName(player);
        String teamName;
        if(GamePlayer.get(player).getTeam() == null)
            teamName = player.getName();
        else
            teamName = GamePlayer.get(player).getTeam().getSbPower() + "_" + player.getName();

        while (teamName.length() > 16)
            teamName = teamName.substring(0, teamName.length() - 1);
        while (GameUtils.scoreboard.getTeam(teamName) != null)
            teamName.substring(0, teamName.length() - 1);

        team = GameUtils.scoreboard.registerNewTeam(teamName);
        if(GamePlayer.get(player).getTeam() == null){
            team.setDisplayName(player.getName());
            team.addPlayer(player);
            player.setScoreboard(GameUtils.scoreboard);
        }else{
            team.setPrefix(GamePlayer.get(player).getTeam().getColor() + "");
            player.setDisplayName(GamePlayer.get(player).getTeam().getColor() + player.getName() + ChatColor.WHITE);

            team.setDisplayName(player.getName());
            team.addPlayer(player);
            player.setScoreboard(GameUtils.scoreboard);
        }
    }
}
