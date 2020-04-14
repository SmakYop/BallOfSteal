package net.pvpwarcraft.ballofsteal.player;

import net.pvpwarcraft.ballofsteal.kits.BKits;
import net.pvpwarcraft.ballofsteal.utils.BItems;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerManager {

    private GamePlayer gamePlayer;
    private PlayerScoreboard playerScoreboard;
    private PlayerTimer playerTimer;

    PlayerManager(GamePlayer gamePlayer){
        this.gamePlayer = gamePlayer;
        this.playerScoreboard = new PlayerScoreboard(this.gamePlayer);
        this.playerTimer = new PlayerTimer(this.gamePlayer);
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public PlayerScoreboard getScoreboard() {
        return playerScoreboard;
    }

    public PlayerTimer getPlayerTimer() {
        return playerTimer;
    }

    public void sendLobbyItems(){
        Player player = gamePlayer.getPlayer();
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        BItems team = new BItems(Material.NETHER_STAR, "§eChoix d'équipe §7(Clic-droit)");
        BItems kit = new BItems(Material.NAME_TAG, "§eKits §7(Clic-droit)");
        BItems lobby = new BItems(Material.BED, "§cRetour au Hub §7(Clic-droit)");
        player.getInventory().setItem(0, team.toItemStack());
        player.getInventory().setItem(1, kit.toItemStack());
        player.getInventory().setItem(8, lobby.toItemStack());
    }

    public void sendGameItems(){
        Player player = gamePlayer.getPlayer();
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        BItems kit = new BItems(Material.NAME_TAG, "§eKits §7(Clic-droit)");
        this.gamePlayer.getPlayer().getInventory().setItem(7, new ItemStack(Material.STAINED_CLAY));
        this.gamePlayer.getPlayer().getInventory().setItem(8, kit.toItemStack());
        if(this.gamePlayer.getKit() == null)
            this.gamePlayer.setKit(BKits.PVP);
        this.gamePlayer.getKit().give(this.gamePlayer.getPlayer());
    }

    public void backToGame(){
        this.gamePlayer.getPlayer().setGameMode(GameMode.SURVIVAL);
        this.gamePlayer.getPlayer().teleport(this.gamePlayer.getTeam().getSpawnLocation());
        sendGameItems();
    }
}
