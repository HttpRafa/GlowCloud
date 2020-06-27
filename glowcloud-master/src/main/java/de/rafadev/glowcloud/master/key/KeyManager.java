package de.rafadev.glowcloud.master.key;

//------------------------------
//
// This class was developed by Rafael K.
// On 26.06.2020 at 16:44
// In the project GlowCloud
//
//------------------------------

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import de.rafadev.glowcloud.lib.document.Document;
import de.rafadev.glowcloud.master.key.classes.SystemKey;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class KeyManager {

    private SystemKey connectionKey;

    public KeyManager() {

        File keyFile = new File("connection/key.json");

        if(!new File("connection/").exists()) {
            new File("connection/").mkdirs();
        }

        if(!keyFile.exists()) {
            Document document = new Document();

            for(int i = 0; i < 100; i++) {
                JsonArray jsonArray = new JsonArray();

                if(document.has("key")) {
                    jsonArray = document.get("key").getAsJsonArray();
                    document.delete("key");
                }

                jsonArray.add(new Random().nextInt(40000));

                document.append("key", jsonArray);

            }

            document.save(keyFile);

        }

        try {
            Document document = Document.load(keyFile);

            JsonArray jsonArray = document.get("key").getAsJsonArray();

            StringBuilder keyString = new StringBuilder();

            for (JsonElement element : jsonArray) {
                keyString.append(element.getAsString());
            }

            connectionKey = new SystemKey(keyString.toString(), 1);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public SystemKey getConnectionKey() {
        return connectionKey;
    }
}
