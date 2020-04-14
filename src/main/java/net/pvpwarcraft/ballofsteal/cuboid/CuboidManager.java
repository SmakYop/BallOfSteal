package net.pvpwarcraft.ballofsteal.cuboid;

import net.pvpwarcraft.ballofsteal.utils.BCuboid;
import org.bukkit.Location;

public class CuboidManager {

    private BCuboid spawnCuboid;

    public BCuboid createSpawnCuboid(Location l1, Location l2){
        BCuboid cuboid = new BCuboid(l1, l2);
        this.spawnCuboid = cuboid;
        return cuboid;
    }

    public BCuboid getSpawnCuboid() {
        return spawnCuboid;
    }
}
