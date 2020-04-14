package net.pvpwarcraft.ballofsteal.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum BKits {

    MINER("Mineur", new String[]{"§7- Pioche en diamant §eE8U5", "§7- Épée en bois", "§7- §e10 §7pommes d'or"}, Material.DIAMOND_PICKAXE),
    PVP("PVP", new String[]{"§7- Épée en diamant §eS1", "§7- Pioche en bois", "§7- §e10 §7pommes d'or"}, Material.DIAMOND_SWORD);

    private String name;
    private String[] lore;
    private Material inventoryItem;

    BKits(String name, String[] lore, Material inventoryItem){
        this.name = name;
        this.lore = lore;
        this.inventoryItem = inventoryItem;
    }

    public String getName() {
        return name;
    }

    public String[] getLore() {
        return lore;
    }

    public Material getInventoryItem() {
        return inventoryItem;
    }

    public void give(Player player){
        player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
        player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));

        if(this == MINER){
            ItemStack pioche = new ItemStack(Material.DIAMOND_PICKAXE);
            ItemMeta piocheMeta = pioche.getItemMeta();
            piocheMeta.addEnchant(Enchantment.DURABILITY, 5, true);
            piocheMeta.addEnchant(Enchantment.DIG_SPEED, 8, true);
            pioche.setItemMeta(piocheMeta);
            player.getInventory().addItem(new ItemStack(Material.WOOD_SWORD));
            player.getInventory().addItem(pioche);
            ItemStack apple = new ItemStack(Material.GOLDEN_APPLE);
            apple.setAmount(10);
            player.getInventory().addItem(apple);
            return;
        }

        if(this == PVP){
            ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
            ItemMeta swordMeta = sword.getItemMeta();
            swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
            sword.setItemMeta(swordMeta);
            player.getInventory().addItem(sword);
            player.getInventory().addItem(new ItemStack(Material.WOOD_PICKAXE));
            ItemStack apple = new ItemStack(Material.GOLDEN_APPLE);
            apple.setAmount(10);
            player.getInventory().addItem(apple);
        }
    }
}
