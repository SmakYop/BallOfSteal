package net.pvpwarcraft.ballofsteal.game;

import net.pvpwarcraft.ballofsteal.BOS;
import net.pvpwarcraft.ballofsteal.player.GamePlayer;
import net.pvpwarcraft.ballofsteal.team.Team;
import net.pvpwarcraft.ballofsteal.utils.BWorldEdit;
import net.pvpwarcraft.ballofsteal.utils.GameUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class GameManager {

    public String PREFIX = "§7[§e§lBallOfSteal§7] ";

    public void startServer(){
        GameState.setState(GameState.LOBBY);
        GameUtils.scoreboard = BOS.getInstance().getServer().getScoreboardManager().getNewScoreboard();

        if(BOS.getInstance().getGameConfig().getNumberOfBallsSpawn() > BOS.getInstance().getGameConfig().getBallsLocation().size()){
            BOS.getInstance().log("ERREUR: Nombre de balls superieur au nombre de points de spawn.");
            return;
        }

        List<Location> locations = new ArrayList<>(BOS.getInstance().getGameConfig().getBallsLocation());

        Random randomBalls = new Random();
        for(int i=1;i<=BOS.getInstance().getGameConfig().getNumberOfBallsSpawn();i++){
            int schematicID = (1+randomBalls.nextInt(BOS.getInstance().getGameConfig().getNumberOfSchematics()));
            File file = new File(BOS.getInstance().getDataFolder() + "/ball/" + schematicID + ".schematic");
            if(!file.exists()){
                BOS.getInstance().log("ERREUR: Schematic introuvable (" + schematicID + ")");
                continue;
            }

            Random randomLocation = new Random();
            int location = randomLocation.nextInt(BOS.getInstance().getGameConfig().getBallsLocation().size());
            Location ballLocation = locations.get(location);
            BOS.getInstance().log("Loading schematic " + schematicID + " at " + ballLocation.toString());
            BWorldEdit.pasteSchematic("" + schematicID, ballLocation);
        }
    }

    public void startGameDetection(){
        if(Bukkit.getOnlinePlayers().size() >= 2 && !BOS.getInstance().getTimerManager().countdownGameStarted)
            BOS.getInstance().getTimerManager().startCountdownGameTimer();
    }

    public void startGame(){
        GameState.setState(GameState.IN_GAME);
        randomizeTeams();
        for(GamePlayer gamePlayer : GamePlayer.getPlayers().values()){
            gamePlayer.getPlayer().teleport(gamePlayer.getTeam().getSpawnLocation());
            gamePlayer.getManager().sendGameItems();
            gamePlayer.getManager().getScoreboard().gameUpdate();
        }
        BOS.getInstance().getTimerManager().startGameTimer();
    }

    public void endGame(){
        List<Integer> teamsDiamonds = new ArrayList<>();
        for(Team teams : Team.getTeams().values())
            teamsDiamonds.add(teams.getDiamonds());

        if(verifyAllEqualUsingStream(teamsDiamonds)){
            startDeathMatch();
            return;
        }

        GameState.setState(GameState.END);
        BOS.getInstance().getTimerManager().startCountdownEndServerTimer();

        HashMap<Team, Integer> diamondsTeam = new HashMap<>();
        for(Team teams : Team.getTeams().values()){
            int diamonds = teams.getDiamonds();
            diamondsTeam.put(teams, diamonds);
        }
        Team team = Collections.max(diamondsTeam.entrySet(), Map.Entry.comparingByValue()).getKey();
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(BOS.getInstance().getGameManager().PREFIX + "§7Victoire de l'équipe " + team.getColor() + team.getName() + " §7avec §e" + team.getDiamonds() + " §7diamants!");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("");
        for(GamePlayer gamePlayer : GamePlayer.getPlayers().values()){
            gamePlayer.getPlayer().setAllowFlight(true);
            gamePlayer.getPlayer().setFlying(true);
            if(gamePlayer.getTeam() == team)
                BOS.getInstance().getGameUtils().spawnFirework(gamePlayer.getPlayer().getLocation());
        }
    }

    private void startDeathMatch(){
        GameState.setState(GameState.DEATHMATCH);
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("§cAucune équipe ne domine! Le §c§lDEATHMATCH §ccommence...");
        Bukkit.broadcastMessage("§7§oLa première équipe qui dépose un diamant dans son coffre remporte la partie!");
        Bukkit.broadcastMessage("");
        for(GamePlayer gamePlayer : GamePlayer.getPlayers().values())
            gamePlayer.getManager().getScoreboard().gameUpdate();
    }

    private static void randomizeTeams(){
        ArrayList<Player> noTeam = new ArrayList<>();
        ArrayList<Team> allteams = new ArrayList<>(Team.getTeams().values());
        for(Player players : Bukkit.getOnlinePlayers()){
            if(GamePlayer.get(players).getTeam() == null){
                noTeam.add(players);
            }

            while (!noTeam.isEmpty()){
                Random r = new Random();
                Team randomteam = allteams.get(r.nextInt(Team.getTeams().size()));
                if(randomteam.getPlayers().size() < 5){
                    randomteam.addPlayer(GamePlayer.get(players));
                    GamePlayer.get(players).setTeam(randomteam);
                    BOS.getInstance().getNametagUtils().updateName(players);
                    noTeam.remove(players);
                }
            }
        }
    }

    private boolean verifyAllEqualUsingStream(List<Integer> list){
        return list.stream().distinct().count() <= 1;
    }
}
