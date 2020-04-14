package net.pvpwarcraft.ballofsteal.team;

import net.pvpwarcraft.ballofsteal.player.GamePlayer;
import net.pvpwarcraft.ballofsteal.utils.BInventory;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class TeamInventory extends BInventory implements Listener{

    public TeamInventory(GamePlayer gamePlayer){
        super(gamePlayer, "Choisissez votre équipe", 1);

        for(Team team : Team.getTeams().values()){
            ItemStack item = new ItemStack(Material.BANNER);
            BannerMeta meta = (BannerMeta)item.getItemMeta();
            meta.setBaseColor(team.getBannerColor());
            meta.setDisplayName(team.getColor() + team.getName());
            item.setItemMeta(meta);
            this.addItem(item);
        }
        this.openInventory();
    }
}
