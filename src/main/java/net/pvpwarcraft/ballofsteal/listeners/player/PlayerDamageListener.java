package net.pvpwarcraft.ballofsteal.listeners.player;

import net.pvpwarcraft.ballofsteal.BOS;
import net.pvpwarcraft.ballofsteal.game.GameState;
import net.pvpwarcraft.ballofsteal.player.GamePlayer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDamageListener implements Listener{

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event){
        if(GameState.isState(GameState.LOBBY) || GameState.isState(GameState.END))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDamageByPlayer(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof Player || event.getDamager() instanceof Player))
            return;

        if(GamePlayer.get((Player)event.getEntity()).getTeam() == GamePlayer.get((Player)event.getDamager()).getTeam()){
            event.setCancelled(true);
            event.getDamager().sendMessage("§cVous ne pouvez pas taper un membre de votre équipe!");
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(BOS.getInstance().getGameConfig().getSpawnLocation());
        GamePlayer.get(player).getManager().getPlayerTimer().startDeathTimer();
    }
}
