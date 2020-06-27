package de.rafadev.glowcloud.wrapper.key;

//------------------------------
//
// This class was developed by Rafael K.
// On 26.06.2020 at 17:22
// In the project GlowCloud
//
//------------------------------

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.rafadev.glowcloud.lib.file.CloudReader;
import de.rafadev.glowcloud.wrapper.main.GlowCloudWrapper;

import java.io.File;
import java.io.IOException;

public class KeyManager {

    private String connectionKey;

    public KeyManager() {

        File keyFile = new File("connection/key.json");

        if(keyFile.exists()) {

            CloudReader reader = new CloudReader(keyFile);

            try {
                JsonObject jsonObject = new JsonParser().parse(reader.read()).getAsJsonObject();

                JsonArray jsonArray = jsonObject.get("key").getAsJsonArray();

                StringBuilder stringBuilder = new StringBuilder();
                for (JsonElement element : jsonArray) {
                    stringBuilder.append(element.getAsString());
                }

                connectionKey = stringBuilder.toString();

            } catch (IOException e) {
                GlowCloudWrapper.getGlowCloud().getLogger().error("Can`t load the connectionKey§8, §cplease copy the key.json for the master to the wrapper§8!");
                GlowCloudWrapper.getGlowCloud().getLogger().error("The Wrapper stops in 5 seconds§8.");

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    e.printStackTrace();
                }

                System.exit(0);
            }

        } else {
            GlowCloudWrapper.getGlowCloud().getLogger().error("Can`t find the connectionKey§8, §cplease copy the key.json for the master to the wrapper§8!");
            GlowCloudWrapper.getGlowCloud().getLogger().error("The Wrapper stops in 5 seconds§8.");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.exit(0);
        }


    }

    public String getConnectionKey() {
        return connectionKey;
    }
}
