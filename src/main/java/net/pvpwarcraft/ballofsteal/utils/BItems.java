package net.pvpwarcraft.ballofsteal.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BItems {

    private Material material;
    private String name;
    private int amount = 1;
    private String[] lore;

    public BItems(Material material, String name){
        this.material = material;
        this.name = name;
    }

    public BItems(Material material, String name, String[] lore){
        this.material = material;
        this.name = name;
        this.lore = lore;

    }

    public BItems(Material material, String name, int amout){
        this.material = material;
        this.name = name;
        this.amount = amout;
    }

    public BItems(ItemStack item){
        this.material = item.getType();
        this.name = item.getItemMeta().getDisplayName();
    }

    public ItemStack toItemStack(){
        ItemStack item = new ItemStack(this.material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(this.name);
        item.setAmount(this.amount);

        if (this.lore != null) {
            List finalLore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<String>();
            String[] arrayOfString;
            int j = (arrayOfString = this.lore).length;
            for (int i = 0; i < j; i++) {
                String s = arrayOfString[i];
                if (s != null) {
                    finalLore.add(s.replace("&", "§"));}
            }
            itemMeta.setLore(finalLore);
        }

        item.setItemMeta(itemMeta);
        return item;
    }

    public Material getMaterial(){
        return material;
    }

    public void setMaterial(Material material){
        this.material = material;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
