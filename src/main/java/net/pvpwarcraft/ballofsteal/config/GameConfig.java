package net.pvpwarcraft.ballofsteal.config;

import net.pvpwarcraft.ballofsteal.BOS;
import net.pvpwarcraft.ballofsteal.team.TeamInfos;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class GameConfig {

    private File file = new File(BOS.getInstance().getDataFolder(), "config.yml");
    private FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    public String getMapName(){
        return configuration.getString("map-name");
    }

    public Location getSpawnLocation(){
        return new Location(Bukkit.getWorld(configuration.getString("spawn-location.world")), configuration.getDouble("spawn-location.x"), configuration.getDouble("spawn-location.y"), configuration.getDouble("spawn-location.z"));
    }

    public Location getSpawnTeamLocation(TeamInfos team){
        return new Location(Bukkit.getWorld(configuration.getString(team.getId() + ".spawn.world")), configuration.getDouble(team.getId() + ".spawn.x"), configuration.getDouble(team.getId() + ".spawn.y"), configuration.getDouble(team.getId() + ".spawn.z"));
    }

    public Location getChestTeamLocation(TeamInfos team){
        return new Location(Bukkit.getWorld(configuration.getString(team.getId() + ".chest.world")), configuration.getDouble(team.getId() + ".chest.x"), configuration.getDouble(team.getId() + ".chest.y"), configuration.getDouble(team.getId() + ".chest.z"));
    }

    public int getMaxBuildY(){
        return configuration.getInt("max-build-y");
    }

    public void createSpawnCuboid(){
        Location firstCorner = new Location(Bukkit.getWorld(configuration.getString("spawn-cuboid.first-corner.world")), configuration.getInt("spawn-cuboid.first-corner.z"), configuration.getInt("spawn-cuboid.first-corner.y"), configuration.getInt("spawn-cuboid.first-corner.z"));
        Location secondCorner = new Location(Bukkit.getWorld(configuration.getString("spawn-cuboid.second-corner.world")), configuration.getInt("spawn-cuboid.second-corner.z"), configuration.getInt("spawn-cuboid.second-corner.y"), configuration.getInt("spawn-cuboid.second-corner.z"));
        BOS.getInstance().getCuboidManager().createSpawnCuboid(firstCorner, secondCorner);
    }

    public int getNumberOfSchematics(){
        return configuration.getInt("number-of-schematics");
    }

    public int getNumberOfBallsSpawn(){
        return configuration.getInt("number-of-balls-spawn");
    }

    public List<Location> getBallsLocation(){
        return (List<Location>)configuration.getList("locations");
    }
}
