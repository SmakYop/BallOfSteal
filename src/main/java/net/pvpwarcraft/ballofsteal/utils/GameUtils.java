package net.pvpwarcraft.ballofsteal.utils;

import net.pvpwarcraft.ballofsteal.BOS;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scoreboard.Scoreboard;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameUtils {

    public static Scoreboard scoreboard;

    public void spawnFirework(Location location) {
        List<Firework> fireworks = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Firework f = location.getWorld().spawn(location, Firework.class);
            FireworkMeta fm = f.getFireworkMeta();
            fm.addEffect(getColorFireworkEffect());
            f.setFireworkMeta(fm);
            fireworks.add(f);
        }
        Bukkit.getScheduler().runTaskLater(BOS.getInstance(), new Runnable() {
            public void run() {
                for (Firework f : fireworks)
                    f.detonate();
            }
        }, 2L);
    }

    private FireworkEffect getColorFireworkEffect() {
        FireworkEffect.Builder builder = FireworkEffect.builder();
        FireworkEffect effect = builder.flicker(false).trail(false).with(FireworkEffect.Type.BALL_LARGE).withColor(Color.RED).withFade(Color.RED).build();
        return effect;
    }

    public void sendToLobby(Player player){
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(BOS.getInstance(), "BungeeCord");
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF("lobby1");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        player.sendPluginMessage(BOS.getInstance(), "BungeeCord", b.toByteArray());
    }
}
