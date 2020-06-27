package de.rafadev.glowcloud.master.wrapper;

//------------------------------
//
// This class was developed by Rafael K.
// On 27.06.2020 at 22:11
// In the project GlowCloud
//
//------------------------------

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.rafadev.glowcloud.lib.file.CloudReader;
import de.rafadev.glowcloud.master.main.GlowCloud;
import de.rafadev.glowcloud.master.wrapper.classes.CloudWrapper;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class WrapperManager {

    private List<CloudWrapper> wrappers = new LinkedList<>();

    private File settingsFile = new File("settings.json");

    public void loadWrappers() {

        GlowCloud.getGlowCloud().getLogger().info("Loading all Wrappers...");

        CloudReader cloudReader = new CloudReader(settingsFile);

        try {
            JsonObject jsonObject = new JsonParser().parse(cloudReader.read()).getAsJsonObject();

            JsonArray wrappersArray = jsonObject.get("wrappers").getAsJsonArray();

            if(wrappersArray.size() > 0) {

                for (JsonElement element : wrappersArray) {
                    JsonObject wrapped = element.getAsJsonObject();

                    OfflineCloudWrapper cloudWrapper = new OfflineCloudWrapper(wrapped.get("id").getAsString(), wrapped.get("host").getAsString(), wrapped.get("heap").getAsInt());
                    wrappers.add(cloudWrapper);
                    GlowCloud.getGlowCloud().getLogger().info("Loading Wrapper §e" + cloudWrapper.getId() + "§8@§6" + cloudWrapper.getHost() + " §7with §e" + cloudWrapper.getHeap() + " §7MB");
                }

            } else {
                GlowCloud.getGlowCloud().getLogger().warning("The cloud could not load wrappers§8.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<CloudWrapper> getWrappers() {
        return wrappers;
    }
}
