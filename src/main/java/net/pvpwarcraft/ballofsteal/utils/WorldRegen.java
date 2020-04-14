package net.pvpwarcraft.ballofsteal.utils;

import net.pvpwarcraft.ballofsteal.BOS;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.*;

public class WorldRegen {

    public WorldRegen() {
        World world = Bukkit.getWorlds().get(0);
        Bukkit.unloadWorld(world, false);

        File worldfile = new File(world.getName());
        deleteWorld(worldfile);

        File worldcopyfile = new File(BOS.getInstance().getDataFolder() + "/map");
        try {
            copyFolder(worldcopyfile, worldfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyFolder(File src, File dest) throws IOException {
        if (src.isDirectory()) {
            if (!dest.exists()) {
                dest.mkdir();
                System.out.println("[SHARING] Copie de " + src + " vers " + dest + " en cours...");
            }

            String[] files = src.list();

            for (String file : files) {
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                copyFolder(srcFile, destFile);
            }
        } else {
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);

            byte[] buffer = new byte[1024];
            int lenght;
            while ((lenght = in.read(buffer)) > 0) {
                out.write(buffer, 0, lenght);
            }
            in.close();
            out.close();
            System.out.println("[SHARING] Copie de " + src + " vers " + dest + " en cours...");
        }
    }

    private void deleteWorld(File fichier) {
        if (fichier.exists()) {
            File[] files = fichier.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++)
                    if (files[i].isDirectory())
                        deleteWorld(files[i]);
                    else
                        files[i].delete();
            }
        }
    }
}
