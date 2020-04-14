package net.pvpwarcraft.ballofsteal.team;

import net.pvpwarcraft.ballofsteal.BOS;
import net.pvpwarcraft.ballofsteal.player.GamePlayer;

public class TeamManager {

    public void loadTeams(){
        for(TeamInfos teamInfos : TeamInfos.values()){
            Team team = new Team(teamInfos.getName());
            team.setColor(teamInfos.getColor());
            team.setBannerColor(teamInfos.getBannerColor());
            team.setSpawnLocation(teamInfos.getSpawnLocation());
            team.setChestLocation(teamInfos.getChestLocation());
        }
    }

    public void playerJoinTeam(Team team, GamePlayer gamePlayer){
        if(team.getPlayers().size() < team.getMaxPlayers()) {
            if(gamePlayer.getTeam() != null)
                gamePlayer.getTeam().removePlayer(gamePlayer);
            team.addPlayer(gamePlayer);
            gamePlayer.setTeam(team);
            gamePlayer.getManager().getScoreboard().teamUpdate(team);
            BOS.getInstance().getNametagUtils().updateName(gamePlayer.getPlayer());
            gamePlayer.getPlayer().sendMessage(BOS.getInstance().getGameManager().PREFIX + "§7Vous venez de rejoindre l'équipe: " + team.getColor() + team.getName() + ".");
        }else gamePlayer.getPlayer().sendMessage(BOS.getInstance().getGameManager().PREFIX + "§cCette équipe est complète, impossible de la rejoindre.");
    }
}
