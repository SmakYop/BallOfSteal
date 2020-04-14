package net.pvpwarcraft.ballofsteal.listeners.player;

import net.pvpwarcraft.ballofsteal.BOS;
import net.pvpwarcraft.ballofsteal.game.GameState;
import net.pvpwarcraft.ballofsteal.player.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener implements Listener{

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLogin(AsyncPlayerPreLoginEvent event){
        if(!GameState.isState(GameState.LOBBY)){
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            event.setKickMessage("§cLa partie a déjà commencé : connexion impossible.");
            return;
        }

        event.setLoginResult(AsyncPlayerPreLoginEvent.Result.ALLOWED);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        GamePlayer gamePlayer = new GamePlayer(player);

        if(BOS.getInstance().getGameConfig().getSpawnLocation() == null){
            BOS.getInstance().log("ERROR: Point de spawn non defini");
            return;
        }

        gamePlayer.getManager().sendLobbyItems();
        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(20);
        player.setFoodLevel(40);

        player.teleport(BOS.getInstance().getGameConfig().getSpawnLocation());
        event.setJoinMessage(BOS.getInstance().getGameManager().PREFIX + "§c" + player.getName() + " §7a rejoint la partie. §c(" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getServer().getMaxPlayers() + ")");
        BOS.getInstance().getGameManager().startGameDetection();

        for(GamePlayer gamePlayers : GamePlayer.getPlayers().values()) {
            gamePlayers.getManager().getScoreboard().connectionUpdate(true);
            BOS.getInstance().getNametagUtils().updateName(gamePlayers.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        GamePlayer.get(player).disconnect();

        if(GameState.isState(GameState.LOBBY)) {
            event.setQuitMessage(BOS.getInstance().getGameManager().PREFIX + "§c" + player.getName() + " §7a quitté la partie. §c(" + (Bukkit.getOnlinePlayers().size() - 1) + "/" + Bukkit.getServer().getMaxPlayers() + ")");
            for (GamePlayer gamePlayer : GamePlayer.getPlayers().values())
                gamePlayer.getManager().getScoreboard().connectionUpdate(false);
        }

        event.setQuitMessage(null);
    }
}
