package net.pvpwarcraft.ballofsteal.listeners.player;


import net.pvpwarcraft.ballofsteal.BOS;
import net.pvpwarcraft.ballofsteal.game.GameState;
import net.pvpwarcraft.ballofsteal.kits.KitInventory;
import net.pvpwarcraft.ballofsteal.player.GamePlayer;
import net.pvpwarcraft.ballofsteal.team.TeamInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener{

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        GamePlayer gamePlayer = GamePlayer.get(player);

        if(GameState.isState(GameState.LOBBY)){
            if (event.getItem() == null || event.getItem().getType().equals(Material.AIR))
                return;

            if ((event.getItem().getType().equals(Material.NETHER_STAR)) && (event.getAction().toString().contains("RIGHT")))
                new TeamInventory(gamePlayer);

            if ((event.getItem().getType().equals(Material.NAME_TAG)) && (event.getAction().toString().contains("RIGHT")))
                new KitInventory(gamePlayer);

            if ((event.getItem().getType().equals(Material.BED)) && (event.getAction().toString().contains("RIGHT")))
                BOS.getInstance().getGameUtils().sendToLobby(player);

        }else if(GameState.isState(GameState.IN_GAME) || GameState.isState(GameState.DEATHMATCH))
            if (event.getItem() == null || event.getItem().getType().equals(Material.AIR) || !event.getItem().getType().equals(Material.NAME_TAG))
                return;
            if ((event.getItem().getType().equals(Material.NAME_TAG)) && (event.getAction().toString().contains("RIGHT")))
                new KitInventory(gamePlayer);
    }
}
