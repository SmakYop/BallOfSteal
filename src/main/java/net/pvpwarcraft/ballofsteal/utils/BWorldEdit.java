package net.pvpwarcraft.ballofsteal.utils;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import com.sk89q.worldedit.world.DataException;
import net.pvpwarcraft.ballofsteal.BOS;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.io.IOException;

public class BWorldEdit {

    public static void pasteSchematic(String schematicName, Location centerLocation){
        File ballFile = new File(BOS.getInstance().getDataFolder()+"/ball/"+schematicName+".schematic");
        EditSession editSession = BOS.getInstance().getWorldEditPlugin().getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(Bukkit.getWorld("world")), 100000000);
        try{
            CuboidClipboard clipboard = MCEditSchematicFormat.getFormat(ballFile).load(ballFile);
            clipboard.paste(editSession, new Vector(centerLocation.getX(), centerLocation.getY(), centerLocation.getZ()), false);
        }catch (MaxChangedBlocksException |DataException |IOException e){
            BOS.getInstance().log("ERROR: can't paste ball schematic, contact an administrator");
        }
    }
}
