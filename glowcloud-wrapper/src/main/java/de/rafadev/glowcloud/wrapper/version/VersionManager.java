package de.rafadev.glowcloud.wrapper.version;

//------------------------------
//
// This class was developed by Rafael K.
// On 20.06.2020 at 00:45
// In the project GlowCloud
//
//------------------------------

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.rafadev.glowcloud.lib.file.CloudReader;
import de.rafadev.glowcloud.wrapper.main.GlowCloudWrapper;
import de.rafadev.glowcloud.wrapper.version.classes.CloudVersion;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class VersionManager {

    private List<CloudVersion> versions = new LinkedList<>();

    private File folder = new File("database/versions/configs/");

    public VersionManager() {
        if(!folder.exists()) {
            folder.mkdirs();
        }
    }

    public void checkVersions() {

        File[] files = folder.listFiles();

        for (File file : files) {
            if(file.getName().endsWith(".json")) {
                GlowCloudWrapper.getGlowCloud().getLogger().info("Loading the version with the name §8\"§e" + file.getName().replaceAll(".json", "") + "§8\"§7...");

                CloudReader cloudReader = new CloudReader(file);
                try {
                    JsonObject jsonObject = new JsonParser().parse(cloudReader.read()).getAsJsonObject();

                    if(jsonObject.has("url")) {

                        CloudVersion cloudVersion = new CloudVersion(jsonObject.get("url").getAsString(), file.getName().replaceAll(".json", ""));
                        versions.add(cloudVersion);

                        GlowCloudWrapper.getGlowCloud().getLogger().info("The version with the name §8\"§e" + file.getName().replaceAll(".json", "") + "§8\" §7was§a loaded§8.");

                    } else {
                        GlowCloudWrapper.getGlowCloud().getLogger().error("Can´t load the version with the name §8\"§4" + file.getName().replaceAll(".json", "") + "§8\"§8!");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public CloudVersion get(String version) {
        return versions.stream().filter(item -> item.getName().equals(version)).collect(Collectors.toList()).get(0);
    }

}
