package net.pvpwarcraft.ballofsteal.kits;

import net.pvpwarcraft.ballofsteal.player.GamePlayer;
import net.pvpwarcraft.ballofsteal.utils.BInventory;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class KitInventory extends BInventory implements Listener{

    public KitInventory(GamePlayer gamePlayer){
        super(gamePlayer, "Choisissez votre kit", 1);

        for(BKits kit : BKits.values()){
            ItemStack item = new ItemStack(kit.getInventoryItem());
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName("§6" + kit.getName());
            ArrayList<String> lore = new ArrayList<>(Arrays.asList(kit.getLore()));
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);

            this.addItem(item);
        }
        this.openInventory();
    }
}
