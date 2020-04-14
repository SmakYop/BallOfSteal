package net.pvpwarcraft.ballofsteal.team;

import net.pvpwarcraft.ballofsteal.BOS;
import net.pvpwarcraft.ballofsteal.utils.BCuboid;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;

public enum  TeamInfos {

    BLUE("blue", "Bleue", ChatColor.BLUE, DyeColor.BLUE, 1),
    RED("red", "Rouge", ChatColor.RED, DyeColor.RED, 2),
    GREEN("green", "Verte", ChatColor.DARK_GREEN, DyeColor.GREEN, 3),
    ORANGE("orange", "Orange", ChatColor.GOLD, DyeColor.ORANGE, 4);

    private String id;
    private String name;
    private ChatColor color;
    private DyeColor bannerColor;
    private int sbPower;

    TeamInfos(String id, String name, ChatColor color, DyeColor bannerColor, int sbPower){
        this.id = id;
        this.name = name;
        this.color = color;
        this.bannerColor = bannerColor;
        this.sbPower = sbPower;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public DyeColor getBannerColor() {
        return bannerColor;
    }

    public int getSbPower() {
        return sbPower;
    }

    public Location getSpawnLocation(){
        return BOS.getInstance().getGameConfig().getSpawnTeamLocation(this);
    }

    public Location getChestLocation(){
        return BOS.getInstance().getGameConfig().getChestTeamLocation(this);
    }
}
