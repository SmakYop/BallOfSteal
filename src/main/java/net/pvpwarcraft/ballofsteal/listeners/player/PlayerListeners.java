package net.pvpwarcraft.ballofsteal.listeners.player;

import net.pvpwarcraft.ballofsteal.game.GameState;
import net.pvpwarcraft.ballofsteal.player.GamePlayer;
import net.pvpwarcraft.ballofsteal.team.Team;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.InventoryHolder;

public class PlayerListeners implements Listener{

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onOpenInventory(InventoryOpenEvent event) {
        if(!(GameState.isState(GameState.IN_GAME) || GameState.isState(GameState.DEATHMATCH)))
            return;

        for(Team team : Team.getTeams().values()) {
            InventoryHolder inventoryHolder = event.getInventory().getHolder();
            if(!(inventoryHolder instanceof Chest))
                return;
            if(team.getChestLocation().equals(((Chest) inventoryHolder).getLocation())) {
                if(GamePlayer.get((Player) event.getPlayer()).getTeam() != team) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage("§cVous ne pouvez pas ouvrir le coffre des autres équipes!");
                }
            }
        }
    }
}
