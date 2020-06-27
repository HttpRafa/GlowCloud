package de.rafadev.glowcloud.wrapper.file;

//------------------------------
//
// This class was developed by Rafael K.
// On 20.06.2020 at 00:14
// In the project GlowCloud
//
//------------------------------

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.rafadev.glowcloud.lib.document.Document;
import de.rafadev.glowcloud.lib.file.CloudWriter;
import de.rafadev.glowcloud.wrapper.main.GlowCloudWrapper;

import java.io.*;

public class FileManager {

    private File settingsFile = new File("settings.json");

    private boolean firststart = false;

    public FileManager() {
        firststart = !new File("database/").exists();
    }

    public void checkAllFiles() {

        new File("database/").mkdirs();

    }

    public void preCheckFiles() {
        if(!settingsFile.exists()) {
            try {
                settingsFile.createNewFile();

                /*
                Fill the settings file
                 */
                Document settingsObject = new Document();

                GlowCloudWrapper.getGlowCloud().getLogger().info("Please write the §eServiceID §7in the console§8.");

                String id = GlowCloudWrapper.getGlowCloud().getCommandManager().requestString();
                settingsObject.append("ID", id);
                GlowCloudWrapper.getGlowCloud().getLogger().info("The ServiceID was set to §8\"§e" + id + "§8\"!");

                settingsObject.append("TotalMemory", GlowCloudWrapper.getGlowCloud().getRuntime().totalMemory());

                settingsObject.save(settingsFile);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File bukkitVersion = new File("database/versions/configs/bukkit.json");
        if(!bukkitVersion.exists()) {

            if(!new File("database/versions/configs/").exists()) {
                new File("database/versions/configs/").mkdirs();
            }

            JsonObject jsonObject = new JsonObject();

            requestVersion(jsonObject);

            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();
            try {
                new CloudWriter(bukkitVersion).write(gson.toJson(jsonObject));
            } catch (IOException e) {
                e.printStackTrace();
            }
            GlowCloudWrapper.getGlowCloud().getLogger().info(" ");
        }
    }

    public void requestVersion(JsonObject jsonObject) {
        GlowCloudWrapper.getGlowCloud().getLogger().error("§cNo default version found§8!");
        GlowCloudWrapper.getGlowCloud().getLogger().info("§8«§e*§8» §e1.8.8§8, §e1.9§8, §e1.10§8, §e1.11§8, §e1.12§8, §e1.13§8, §e1.14§8, §e1.15§8, §e1.16");

        String buffer = GlowCloudWrapper.getGlowCloud().getCommandManager().requestString();
        if (buffer.equalsIgnoreCase("1.8.8")) {
            jsonObject.addProperty("url", "https://cdn.getbukkit.org/spigot/spigot-1.8.8-R0.1-SNAPSHOT-latest.jar");
        } else if (buffer.equalsIgnoreCase("1.9")) {
            jsonObject.addProperty("url", "https://cdn.getbukkit.org/spigot/spigot-1.9-R0.1-SNAPSHOT-latest.jar");
        } else if (buffer.equalsIgnoreCase("1.10")) {
            jsonObject.addProperty("url", "https://cdn.getbukkit.org/spigot/spigot-1.10-R0.1-SNAPSHOT-latest.jar");
        } else if (buffer.equalsIgnoreCase("1.11")) {
            jsonObject.addProperty("url", "https://cdn.getbukkit.org/spigot/spigot-1.11.jar");
        } else if (buffer.equalsIgnoreCase("1.12")) {
            jsonObject.addProperty("url", "https://cdn.getbukkit.org/spigot/spigot-1.12.2.jar");
        } else if (buffer.equalsIgnoreCase("1.13")) {
            jsonObject.addProperty("url", "https://cdn.getbukkit.org/spigot/spigot-1.13.jar");
        } else if (buffer.equalsIgnoreCase("1.14")) {
            jsonObject.addProperty("url", "https://cdn.getbukkit.org/spigot/spigot-1.14.jar");
        } else if (buffer.equalsIgnoreCase("1.15")) {
            jsonObject.addProperty("url", "https://cdn.getbukkit.org/spigot/spigot-1.15.jar");
        } else if (buffer.equalsIgnoreCase("1.16")) {
            jsonObject.addProperty("url", "https://cdn.getbukkit.org/spigot/spigot-1.16.1.jar");
        } else {
            GlowCloudWrapper.getGlowCloud().getLogger().info("The version §c" + buffer + " §7is not supported§8.");
            requestVersion(jsonObject);
        }
    }

    public boolean isFirstStart() {
        return firststart;
    }

}
