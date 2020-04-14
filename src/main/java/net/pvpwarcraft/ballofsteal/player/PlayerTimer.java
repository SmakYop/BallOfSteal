package net.pvpwarcraft.ballofsteal.player;

import net.pvpwarcraft.ballofsteal.BOS;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerTimer {

    private GamePlayer gamePlayer;
    private int deathTimer = 5;

    PlayerTimer(GamePlayer gamePlayer){
        this.gamePlayer = gamePlayer;
    }

    public void startDeathTimer(){
        new BukkitRunnable() {
            @Override
            public void run() {
                deathTimer--;

                BOS.getInstance().getTitleUtils().sendTitle(gamePlayer.getPlayer(), "§cVous êtes mort !", "§cPatientez encore " + deathTimer + " secondes.", deathTimer);

                if(deathTimer == 0){
                    cancel();
                    gamePlayer.getManager().backToGame();
                    deathTimer = 5;
                }
            }
        }.runTaskTimer(BOS.getInstance(), 20, 20);
    }
}
