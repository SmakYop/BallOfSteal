package net.pvpwarcraft.ballofsteal.player;

import net.pvpwarcraft.ballofsteal.kits.BKits;
import net.pvpwarcraft.ballofsteal.team.Team;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class GamePlayer {

    private Player player;
    private int kills;
    private boolean alive;
    private PlayerManager playerManager;
    private Team team;
    private BKits kit;

    private static HashMap<Player, GamePlayer> players = new HashMap<>();

    public GamePlayer(Player player){
        this.player = player;
        this.kills = 0;
        this.alive = true;
        this.playerManager = new PlayerManager(this);
        players.put(player, this);
    }

    public static GamePlayer get(Player player){
        return players.get(player);
    }

    public static HashMap<Player, GamePlayer> getPlayers(){
        return players;
    }

    public Player getPlayer() {
        return player;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void addKill(){
        this.kills++;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public PlayerManager getManager() {
        return playerManager;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setKit(BKits kit) {
        this.kit = kit;
    }

    public BKits getKit() {
        return kit;
    }

    public void disconnect(){
        players.remove(this.player);
    }
}
