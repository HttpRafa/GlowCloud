package de.rafadev.glowcloud.master.file;

//------------------------------
//
// This class was developed by Rafael K.
// On 26.05.2020 at 13:37
// In the project GlowCloud
//
//------------------------------

import com.google.gson.JsonArray;
import de.rafadev.glowcloud.lib.document.Document;

import java.io.File;

public class FileManager {

    private File settingsFile = new File("settings.json");

    public FileManager() {

        if(!settingsFile.exists()) {
            Document document = new Document();

            // Server Settings
            Document serverSettings = new Document();
            serverSettings.append("name-splitter", "-");

            document.append("server", serverSettings);
            document.append("wrappers", new JsonArray());

            document.save(settingsFile);
        }

    }

}
