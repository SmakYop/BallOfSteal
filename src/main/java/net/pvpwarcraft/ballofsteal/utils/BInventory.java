package net.pvpwarcraft.ballofsteal.utils;

import net.pvpwarcraft.ballofsteal.BOS;
import net.pvpwarcraft.ballofsteal.player.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class BInventory implements Listener{

    protected GamePlayer player;
    protected String nameMenu;
    protected int rowMenu;
    protected org.bukkit.inventory.Inventory menuInventory;

    public BInventory(GamePlayer player, String nameMenu, int rowMenu){
        this.player = player;
        this.nameMenu = nameMenu;
        this.rowMenu = rowMenu;
        this.menuInventory = Bukkit.createInventory(null, this.rowMenu * 9, this.nameMenu);
    }

    public void openInventory(){
        this.player.getPlayer().openInventory(this.menuInventory);
        Bukkit.getPluginManager().registerEvents(this, BOS.getInstance());
    }

    public void addItem(BItems itemsUtils, int slot){
        this.menuInventory.setItem(slot, itemsUtils.toItemStack());
    }

    public void addItem(BItems itemsUtils){
        this.menuInventory.addItem(itemsUtils.toItemStack());
    }

    public void addItem(ItemStack itemStack){
        this.menuInventory.addItem(itemStack);
    }
}
