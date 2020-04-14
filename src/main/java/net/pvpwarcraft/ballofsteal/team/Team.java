package net.pvpwarcraft.ballofsteal.team;

import net.pvpwarcraft.ballofsteal.player.GamePlayer;
import net.pvpwarcraft.ballofsteal.utils.BCuboid;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Team {

    private String name;
    private ChatColor color;
    private DyeColor bannerColor;
    private List<GamePlayer> players;
    private Location spawnLocation;
    private Location chestLocation;
    private int maxPlayers;
    private int diamonds;
    private int sbPower;

    private static HashMap<String, Team> teams = new HashMap<>();

    public Team(String name){
        this.name = name;
        this.maxPlayers = 5;
        this.diamonds = 0;
        this.players = new ArrayList<>();
        teams.put(this.name, this);
    }

    public static Team getTeam(String name){
        return teams.get(name);
    }

    public static HashMap<String, Team> getTeams(){
        return teams;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }

    public DyeColor getBannerColor() {
        return bannerColor;
    }

    public void setBannerColor(DyeColor color) {
        this.bannerColor = color;
    }

    public List<GamePlayer> getPlayers() {
        return players;
    }

    public void addPlayer(GamePlayer gamePlayer){
        this.players.add(gamePlayer);
    }

    public void removePlayer(GamePlayer gamePlayer){
        this.players.remove(gamePlayer);
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public Location getChestLocation() {
        return chestLocation;
    }

    public void setChestLocation(Location chestLocation) {
        this.chestLocation = chestLocation;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getDiamonds() {
        return diamonds;
    }

    public void addDiamonds(int diamonds){
        this.diamonds = this.diamonds + diamonds;
    }

    public int getSbPower() {
        return sbPower;
    }

    public void setSbPower(int sbPower) {
        this.sbPower = sbPower;
    }
}
