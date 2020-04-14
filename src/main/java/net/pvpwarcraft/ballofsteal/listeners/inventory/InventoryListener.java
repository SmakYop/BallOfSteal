package net.pvpwarcraft.ballofsteal.listeners.inventory;

import net.pvpwarcraft.ballofsteal.BOS;
import net.pvpwarcraft.ballofsteal.game.GameState;
import net.pvpwarcraft.ballofsteal.kits.BKits;
import net.pvpwarcraft.ballofsteal.player.GamePlayer;
import net.pvpwarcraft.ballofsteal.team.Team;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener{

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        GamePlayer gamePlayer = GamePlayer.get(player);
        ItemStack clickItem = event.getCurrentItem();
        if(GameState.isState(GameState.LOBBY)) {
            event.setCancelled(true);
            if ((clickItem == null) || (clickItem.getType() == Material.AIR) || (event.getClickedInventory() == null)) {
                return;
            }

            if (event.getClickedInventory().getName().equalsIgnoreCase("Choisissez votre équipe")) {
                if (clickItem.getType().equals(Material.BANNER)) {

                    String teamName = clickItem.getItemMeta().getDisplayName();
                    for (Team team : Team.getTeams().values()) {
                        if (teamName.equalsIgnoreCase(team.getColor() + team.getName()))
                            BOS.getInstance().getTeamManager().playerJoinTeam(team, gamePlayer);
                    }
                    player.closeInventory();
                    return;
                }
            }

            if (event.getClickedInventory().getName().equalsIgnoreCase("Choisissez votre kit")) {
                if (clickItem.getType().equals(Material.DIAMOND_SWORD) || clickItem.getType().equals(Material.DIAMOND_PICKAXE)) {

                    String kitName = clickItem.getItemMeta().getDisplayName();
                    for (BKits kit : BKits.values()) {
                        if (kitName.equalsIgnoreCase("§6" + kit.getName())) {
                            gamePlayer.setKit(kit);
                            gamePlayer.getPlayer().sendMessage(BOS.getInstance().getGameManager().PREFIX + "§7Vous venez de choisir le kit: §6" + kit.getName());
                            gamePlayer.getManager().getScoreboard().kitUpdate(kit);
                        }
                    }
                    player.closeInventory();
                }
            }

        }

        if (GameState.isState(GameState.IN_GAME) || GameState.isState(GameState.DEATHMATCH)) {
            if ((clickItem.getType() == Material.AIR) || (event.getClickedInventory() == null)) {
                return;
            }
            if(event.getClickedInventory().getName().equalsIgnoreCase("Choisissez votre kit")) {
                event.setCancelled(true);
                if (clickItem.getType().equals(Material.DIAMOND_SWORD) || clickItem.getType().equals(Material.DIAMOND_PICKAXE)) {

                    String kitName = clickItem.getItemMeta().getDisplayName();
                    for (BKits kit : BKits.values()) {
                        if (kitName.equalsIgnoreCase("§6" + kit.getName())) {
                            gamePlayer.setKit(kit);
                            gamePlayer.getPlayer().sendMessage(BOS.getInstance().getGameManager().PREFIX + "§7Vous venez de choisir le kit: §6" + kit.getName());
                            gamePlayer.getPlayer().sendMessage(BOS.getInstance().getGameManager().PREFIX +  "§7§oLe changement prendra effet après votre prochaine mort.");
                        }
                    }
                    player.closeInventory();
                }
            }

            Inventory top = event.getView().getTopInventory();
            Inventory bottom = event.getView().getBottomInventory();

            if (top.getType() == InventoryType.CHEST && bottom.getType() == InventoryType.PLAYER) {
                for (Team team : Team.getTeams().values()) {
                    InventoryHolder inventoryHolder = event.getInventory().getHolder();
                    if (!(inventoryHolder instanceof Chest))
                        return;

                    if (team.getChestLocation().equals(((Chest) inventoryHolder).getLocation())) {
                        if (GamePlayer.get((Player) event.getWhoClicked()).getTeam() == team) {
                            if (event.getCurrentItem().getType() == Material.DIAMOND) {
                                if (event.getClick().isShiftClick()) {
                                    if (event.getClickedInventory().equals(event.getWhoClicked().getInventory())) {

                                        if (GameState.isState(GameState.IN_GAME)) {
                                            int amount = event.getCurrentItem().getAmount();
                                            team.addDiamonds(amount);
                                            for (GamePlayer gamePlayers : GamePlayer.getPlayers().values())
                                                gamePlayers.getManager().getScoreboard().gameUpdate();
                                            event.getWhoClicked().sendMessage(BOS.getInstance().getGameManager().PREFIX + "§7Vous venez de déposer §e" + amount + " §7diamants dans votre coffre! (Total: " + team.getDiamonds() + ")");
                                            return;

                                        } else if (GameState.isState(GameState.DEATHMATCH)) {
                                            int amount = event.getCurrentItem().getAmount();
                                            team.addDiamonds(amount);
                                            for (GamePlayer gamePlayers : GamePlayer.getPlayers().values())
                                                gamePlayers.getManager().getScoreboard().gameUpdate();
                                            event.getWhoClicked().sendMessage(BOS.getInstance().getGameManager().PREFIX + "§7Vous venez de déposer §e" + amount + " §7diamants dans votre coffre! (Total: " + team.getDiamonds() + ")");
                                            BOS.getInstance().getGameManager().endGame();
                                            return;
                                        }
                                    } else event.setCancelled(true);
                                } else event.setCancelled(true);
                            } else event.setCancelled(true);
                        } else event.setCancelled(true);
                    }
                }
            }
        }
    }
}
