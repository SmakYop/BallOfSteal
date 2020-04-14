package net.pvpwarcraft.ballofsteal.player;

import net.pvpwarcraft.ballofsteal.BOS;
import net.pvpwarcraft.ballofsteal.game.GameState;
import net.pvpwarcraft.ballofsteal.kits.BKits;
import net.pvpwarcraft.ballofsteal.scoreboard.Scoreboard;
import net.pvpwarcraft.ballofsteal.team.Team;
import org.bukkit.Bukkit;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerScoreboard {

    private GamePlayer gamePlayer;
    private Scoreboard scoreboard;
    private org.bukkit.scoreboard.Scoreboard bukkitScoreboard;

    PlayerScoreboard(GamePlayer gamePlayer){
        this.gamePlayer = gamePlayer;
        this.scoreboard = new Scoreboard(gamePlayer.getPlayer(), "Gamescore");
        this.scoreboard.create();
        this.bukkitScoreboard = BOS.getInstance().getServer().getScoreboardManager().getNewScoreboard();
        this.sendLobbyScoreboard();
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public org.bukkit.scoreboard.Scoreboard getBukkitScoreboard() {
        return bukkitScoreboard;
    }

    private void sendLobbyScoreboard(){
        this.scoreboard.setObjectiveName(" §6§lBall of Steal  ");
        this.scoreboard.setLine(9, "  ");
        this.scoreboard.setLine(8, "§7Map: §e" + BOS.getInstance().getGameConfig().getMapName());
        this.scoreboard.setLine(7, "§7Joueurs: §e");
        this.scoreboard.setLine(6, " ");
        this.scoreboard.setLine(5, "§7Début dans: §e" + BOS.getInstance().getTimerManager().countdownGameTimer + " sec");
        this.scoreboard.setLine(4, "§7Kit: §c✖");
        this.scoreboard.setLine(3, "§7Équipe: §c✖");
        this.scoreboard.setLine(2, "");
        this.scoreboard.setLine(1, "§6mc.pvp-warcraft.net");
    }

    public void connectionUpdate(boolean connection){
        this.scoreboard.removeLine(7);
        int online = connection ? Bukkit.getOnlinePlayers().size() : (Bukkit.getOnlinePlayers().size()-1);
        this.scoreboard.setLine(7, "§7Joueurs: §e" + online + "/" + Bukkit.getServer().getMaxPlayers());
    }

    public void kitUpdate(BKits kit){
        this.scoreboard.removeLine(4);
        this.scoreboard.setLine(4, "§7Kit: §e" + kit.getName());
    }

    public void teamUpdate(Team team){
        this.scoreboard.removeLine(3);
        this.scoreboard.setLine(3, "§7Équipe: " + team.getColor() + team.getName());
    }

    public void updateLobbyTimer(){
        this.scoreboard.removeLine(5);
        this.scoreboard.setLine(5, "§7Début dans: §e" + BOS.getInstance().getTimerManager().countdownGameTimer + " sec");
    }

    public void gameUpdate(){
        for(int i=3;i<=9;i++)
            this.scoreboard.removeLine(i);

        int i = 3;
        for(Team team : Team.getTeams().values()){
            Character firstLetter = team.getName().charAt(0);
            scoreboard.setLine(i, team.getColor() + "[" + firstLetter + "] §7Diamants: §b" + team.getDiamonds());
            i++;
        }

        String time = new SimpleDateFormat("mm:ss").format(new Date(BOS.getInstance().getTimerManager().gameTimer * 1000));
        this.scoreboard.setLine(i, " ");
        if(GameState.isState(GameState.DEATHMATCH))
            this.scoreboard.setLine(i+1, "§7Temps restant: §cDEATHMATCH");
        else this.scoreboard.setLine(i+1, "§7Temps restant: §e" + time);
        this.scoreboard.setLine(i+2, "  ");
    }
}
