package net.pvpwarcraft.ballofsteal.listeners.block;

import net.pvpwarcraft.ballofsteal.BOS;
import net.pvpwarcraft.ballofsteal.game.GameState;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class BlockListener implements Listener{

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        if(GameState.isState(GameState.LOBBY)) {
            event.setCancelled(true);
            return;
        }

        if(BOS.getInstance().getCuboidManager().getSpawnCuboid().contains(event.getBlock())){
            event.setCancelled(true);
            return;
        }

        if(event.getBlock().getLocation().getY() >= BOS.getInstance().getGameConfig().getMaxBuildY()) {
            event.setCancelled(true);
            return;
        }

        if(GameState.isState(GameState.IN_GAME) || GameState.isState(GameState.DEATHMATCH)){
            ItemStack block = event.getItemInHand();
            if(block.getType().equals(Material.STAINED_CLAY))
                event.getPlayer().setItemInHand(block);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(GameState.isState(GameState.LOBBY)) {
            event.setCancelled(true);
            return;
        }

        if(BOS.getInstance().getCuboidManager().getSpawnCuboid().contains(event.getBlock())){
            event.setCancelled(true);
            return;
        }

        if(event.getBlock().getLocation().getY() >= BOS.getInstance().getGameConfig().getMaxBuildY()) {
            event.setCancelled(true);
            return;
        }

        if(!(event.getBlock().getType().equals(Material.DIAMOND_ORE) || event.getBlock().getType().equals(Material.DIAMOND_BLOCK))){
            event.getBlock().setType(Material.AIR);
            return;
        }

        if(event.getBlock().getType().equals(Material.DIAMOND_ORE)) {
            event.getPlayer().getInventory().addItem(new ItemStack(Material.DIAMOND));
            event.getBlock().setType(Material.AIR);
            event.getPlayer().sendMessage(BOS.getInstance().getGameManager().PREFIX + "§7Diamant: §e+1");
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.NOTE_BASS, 100, 100);

        }else if(event.getBlock().getType().equals(Material.DIAMOND_BLOCK)){
            event.getBlock().setType(Material.AIR);

            Random random = new Random();
            int amount = (5+random.nextInt(4));
            ItemStack diamonds = new ItemStack(Material.DIAMOND);
            diamonds.setAmount(amount);

            event.getPlayer().getInventory().addItem(diamonds);
            event.getPlayer().sendMessage(BOS.getInstance().getGameManager().PREFIX + "§7Diamant: §e+" + amount);
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.NOTE_BASS, 100, 100);
        }
    }
}
