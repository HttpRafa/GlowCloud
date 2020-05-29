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
import de.rafadev.glowcloud.master.main.GlowCloud;

import java.io.File;

public class FileManager {

    private File settingsFile = new File("settings.json");

    private boolean fiststart = false;

    public FileManager() {
        if(!settingsFile.exists()) {
            fiststart = true;
        }
    }

    public void checkAllFiles() {
        if(!settingsFile.exists()) {
            Document document = new Document();

            // Server Settings
            Document serverSettings = new Document();
            serverSettings.append("name-splitter", "-");

            document.append("server", serverSettings);
            document.append("wrappers", new JsonArray());

            document.save(settingsFile);
            System.out.println("\"" + settingsFile.getName() + "\" was created.");
        }
    }

    public boolean isFistStart() {
        return fiststart;
    }
}
