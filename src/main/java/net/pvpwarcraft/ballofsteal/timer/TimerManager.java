package net.pvpwarcraft.ballofsteal.timer;

import net.pvpwarcraft.ballofsteal.BOS;
import net.pvpwarcraft.ballofsteal.game.GameState;
import net.pvpwarcraft.ballofsteal.player.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerManager {

    public int countdownGameTimer = 60;
    public int countdownServerEndTimer = 20;
    public int gameTimer = 30;
    public boolean countdownGameStarted = false;

    public void startCountdownGameTimer(){
        this.countdownGameStarted = true;
        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player players : Bukkit.getOnlinePlayers()){

                    GamePlayer.get(players).getManager().getScoreboard().updateLobbyTimer();

                    if(countdownGameTimer == 60 || countdownGameTimer == 30)
                        players.sendMessage(BOS.getInstance().getGameManager().PREFIX + "§7Lancement de la partie dans §c" + countdownGameTimer + " secondes.");


                    if(countdownGameTimer <= 5 && countdownGameTimer > 0){
                        BOS.getInstance().getTitleUtils().sendActionBar(players, "§eDébut de la partie dans §6" + countdownGameTimer + " §esecondes.");
                        players.playSound(players.getLocation(), Sound.NOTE_BASS, 100, 100);
                    }
                }
                if(countdownGameTimer == 0){
                    cancel();
                    BOS.getInstance().getGameManager().startGame();
                    for(Player players : Bukkit.getOnlinePlayers())
                        BOS.getInstance().getTitleUtils().sendTitle(players, "§eLa partie commence !", "", 60);
                }
                countdownGameTimer--;
            }
        }.runTaskTimer(BOS.getInstance(), 20, 20);
    }

    public void startGameTimer(){
        new BukkitRunnable() {
            @Override
            public void run() {
                if(GameState.isState(GameState.IN_GAME)) {
                    gameTimer--;

                    for(GamePlayer gamePlayer : GamePlayer.getPlayers().values())
                        gamePlayer.getManager().getScoreboard().gameUpdate();

                    if(gameTimer == 0){
                        cancel();
                        BOS.getInstance().getGameManager().endGame();
                    }

                }
            }
        }.runTaskTimer(BOS.getInstance(), 20, 20);
    }

    public void startCountdownEndServerTimer(){
        new BukkitRunnable() {
            @Override
            public void run() {
                countdownServerEndTimer--;
                if(countdownServerEndTimer == 0){
                    Bukkit.shutdown();
                }
            }
        }.runTaskTimer(BOS.getInstance(), 20, 20);
    }
}
