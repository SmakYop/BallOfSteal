package net.pvpwarcraft.ballofsteal.command;

import net.pvpwarcraft.ballofsteal.BOS;
import net.pvpwarcraft.ballofsteal.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player))
            return false;

        Player player = (Player) commandSender;
        if(!player.hasPermission("game.use"))
            return true;

        if(args.length == 1 && args[0].equalsIgnoreCase("start")){
            if(BOS.getInstance().getTimerManager().countdownGameStarted || !GameState.isState(GameState.LOBBY)){
                player.sendMessage("§cLa partie est déjà lancée!");
                return true;
            }
            BOS.getInstance().getTimerManager().startCountdownGameTimer();
            BOS.getInstance().getTimerManager().countdownGameTimer = 10;
            Bukkit.broadcastMessage(BOS.getInstance().getGameManager().PREFIX + "§7La partie vient d'être lancée par: §c" + player.getName());
            return true;
        }
        return false;
    }
}
