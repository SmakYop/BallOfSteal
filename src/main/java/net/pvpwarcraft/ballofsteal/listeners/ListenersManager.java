package net.pvpwarcraft.ballofsteal.listeners;

import net.pvpwarcraft.ballofsteal.listeners.block.BlockListener;
import net.pvpwarcraft.ballofsteal.listeners.inventory.InventoryListener;
import net.pvpwarcraft.ballofsteal.listeners.player.PlayerConnectionListener;
import net.pvpwarcraft.ballofsteal.listeners.player.PlayerDamageListener;
import net.pvpwarcraft.ballofsteal.listeners.player.PlayerInteractListener;
import net.pvpwarcraft.ballofsteal.listeners.player.PlayerListeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class ListenersManager {

    private Plugin plugin;
    private PluginManager pluginManager;

    public ListenersManager(Plugin plugin){
        this.plugin = plugin;
        this.pluginManager = Bukkit.getPluginManager();
    }

    public void registerEvents(){
        pluginManager.registerEvents(new PlayerConnectionListener(), plugin);
        pluginManager.registerEvents(new PlayerDamageListener(), plugin);
        pluginManager.registerEvents(new PlayerInteractListener(), plugin);
        pluginManager.registerEvents(new PlayerListeners(), plugin);
        pluginManager.registerEvents(new BlockListener(), plugin);
        pluginManager.registerEvents(new InventoryListener(), plugin);
    }
}
