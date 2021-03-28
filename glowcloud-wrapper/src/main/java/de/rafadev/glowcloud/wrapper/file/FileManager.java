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
import de.rafadev.glowcloud.lib.config.Config;
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

    public Config getConfig() {
        return new Config(settingsFile);
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
                settingsObject.append("id", id);
                GlowCloudWrapper.getGlowCloud().getLogger().info("The §eServiceID §7was set to §8\"§e" + id + "§8\"!");

                GlowCloudWrapper.getGlowCloud().getLogger().info("Please write the §eMaster Address §7in the console§8.");

                String address = GlowCloudWrapper.getGlowCloud().getCommandManager().requestString();
                settingsObject.append("masterAddress", address);
                GlowCloudWrapper.getGlowCloud().getLogger().info("The §eMaster Address §7was set to §8\"§e" + address + "§8\"!");

                GlowCloudWrapper.getGlowCloud().getLogger().info("Please write the §eMaster Port §7in the console§8.");

                String port = GlowCloudWrapper.getGlowCloud().getCommandManager().requestString();
                settingsObject.append("masterPort", Integer.parseInt(port));
                GlowCloudWrapper.getGlowCloud().getLogger().info("The §eMaster Port §7was set to §8\"§e" + port + "§8\"!");

                settingsObject.append("totalMemory", GlowCloudWrapper.getGlowCloud().getRuntime().totalMemory() / 1000);

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

            requestBukkitVersion(jsonObject);

            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();
            try {
                new CloudWriter(bukkitVersion).write(gson.toJson(jsonObject));
            } catch (IOException e) {
                e.printStackTrace();
            }
            GlowCloudWrapper.getGlowCloud().getLogger().info(" ");
        }
        File bungeeCordVersion = new File("database/versions/configs/bungeecord.json");
        if(!bungeeCordVersion.exists()) {

            if(!new File("database/versions/configs/").exists()) {
                new File("database/versions/configs/").mkdirs();
            }

            JsonObject jsonObject = new JsonObject();

            requestBungeeCordVersion(jsonObject);

            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();
            try {
                new CloudWriter(bungeeCordVersion).write(gson.toJson(jsonObject));
            } catch (IOException e) {
                e.printStackTrace();
            }
            GlowCloudWrapper.getGlowCloud().getLogger().info(" ");
        }
    }

    public void requestBukkitVersion(JsonObject jsonObject) {
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
            requestBukkitVersion(jsonObject);
        }
    }

    public void requestBungeeCordVersion(JsonObject jsonObject) {
        GlowCloudWrapper.getGlowCloud().getLogger().error("§cNo default BungeeCord version found§8!");
        GlowCloudWrapper.getGlowCloud().getLogger().info("§8«§e*§8» §edefault§8, §ewaterfall");

        String buffer = GlowCloudWrapper.getGlowCloud().getCommandManager().requestString();
        if (buffer.equalsIgnoreCase("default")) {
            jsonObject.addProperty("url", "https://ci.md-5.net/job/BungeeCord/lastSuccessfulBuild/artifact/bootstrap/target/BungeeCord.jar");
        } else if (buffer.equalsIgnoreCase("waterfall")) {
            jsonObject.addProperty("url", "https://papermc.io/ci/job/Waterfall/lastSuccessfulBuild/artifact/Waterfall-Proxy/bootstrap/target/Waterfall.jar");
        } else {
            GlowCloudWrapper.getGlowCloud().getLogger().info("The version §c" + buffer + " §7is not supported§8.");
            requestBungeeCordVersion(jsonObject);
        }

    }

    public boolean isFirstStart() {
        return firststart;
    }

}
