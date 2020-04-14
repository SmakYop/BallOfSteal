package net.pvpwarcraft.ballofsteal;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import net.pvpwarcraft.ballofsteal.command.GameCommand;
import net.pvpwarcraft.ballofsteal.config.GameConfig;
import net.pvpwarcraft.ballofsteal.cuboid.CuboidManager;
import net.pvpwarcraft.ballofsteal.game.GameManager;
import net.pvpwarcraft.ballofsteal.listeners.ListenersManager;
import net.pvpwarcraft.ballofsteal.team.TeamManager;
import net.pvpwarcraft.ballofsteal.timer.TimerManager;
import net.pvpwarcraft.ballofsteal.utils.*;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BOS extends JavaPlugin{

    private static BOS instance;
    private GameManager gameManager;
    private TimerManager timerManager;
    private GameConfig gameConfig;
    private TitleUtils titleUtils;
    private TeamManager teamManager;
    private NametagUtils nametagUtils;
    private WorldEditPlugin worldEditPlugin;
    private GameUtils gameUtils;
    private CuboidManager cuboidManager;

    @Override
    public void onEnable() {
        instance = this;

        this.gameManager = new GameManager();
        this.timerManager = new TimerManager();
        this.gameConfig = new GameConfig();
        this.titleUtils = new TitleUtils();
        this.teamManager = new TeamManager();
        this.nametagUtils = new NametagUtils();
        this.gameUtils = new GameUtils();
        this.cuboidManager = new CuboidManager();
        this.worldEditPlugin = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");

        saveDefaultConfig();

        new ListenersManager(this).registerEvents();
        this.getCommand("game").setExecutor(new GameCommand());
        this.gameManager.startServer();
        this.teamManager.loadTeams();
        this.gameConfig.createSpawnCuboid();
    }

    @Override
    public void onDisable() {
        new WorldRegen();
        instance = null;
    }

    public static BOS getInstance() {
        return instance;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public TimerManager getTimerManager() {
        return timerManager;
    }

    public GameConfig getGameConfig() {
        return gameConfig;
    }

    public TitleUtils getTitleUtils() {
        return titleUtils;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public NametagUtils getNametagUtils() {
        return nametagUtils;
    }

    public WorldEditPlugin getWorldEditPlugin() {
        return worldEditPlugin;
    }

    public GameUtils getGameUtils() {
        return gameUtils;
    }

    public CuboidManager getCuboidManager() {
        return cuboidManager;
    }

    public void log(String message){
        this.getLogger().info(message);
    }
}
