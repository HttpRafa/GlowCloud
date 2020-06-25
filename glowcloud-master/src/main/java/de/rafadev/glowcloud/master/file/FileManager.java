package de.rafadev.glowcloud.master.file;

//------------------------------
//
// This class was developed by Rafael K.
// On 26.05.2020 at 13:37
// In the project GlowCloud
//
//------------------------------

import com.google.gson.JsonArray;
import de.rafadev.glowcloud.lib.config.Config;
import de.rafadev.glowcloud.lib.document.Document;
import de.rafadev.glowcloud.master.main.GlowCloud;

import java.io.File;

public class FileManager {


    private File groupFolder = new File("groups/");
    private File modulesFolder = new File("modules/");
    private File configsFolder = new File("configs/");

    private File settingsFile = new File("settings.json");
    private File messagesFile = new File(configsFolder.getPath() + "/ingame_message.json");

    private boolean firststart = false;

    public FileManager() {
        if(!settingsFile.exists()) {
            firststart = true;
        }
    }

    public Config getConfig() {
        return new Config(settingsFile);
    }

    public void checkAllFiles() {
        if(!groupFolder.exists()) {
            groupFolder.mkdirs();
            System.out.println("The folder \"" + groupFolder.getPath() + "\" was created.");
        }
        if(!configsFolder.exists()) {
            configsFolder.mkdirs();
            System.out.println("The folder \"" + configsFolder.getPath() + "\" was created.");
        }
        if(!modulesFolder.exists()) {
            modulesFolder.mkdirs();
            System.out.println("The folder \"" + modulesFolder.getPath() + "\" was created.");
        }
        if(!settingsFile.exists()) {
            Document document = new Document();

            // Cloud Settings
            Document cloudSettings = new Document();
            cloudSettings.append("port", 6360);

            // Server Settings
            Document serverSettings = new Document();
            serverSettings.append("name-splitter", "-");

            document.append("cloud", cloudSettings);
            document.append("server", serverSettings);
            document.append("wrappers", new JsonArray());

            document.save(settingsFile);
            System.out.println("The file \"" + settingsFile.getPath() + "\" was created.");
        }
        if(!messagesFile.exists()) {
            Document document = new Document();

            document.append("prefix", "§e○ Cloud §8» §7");
            document.append("color", "§e");
            document.append("kick-maintenance", "§cThe Network is currently in maintenance mode§8.");
            document.append("servergroup-maintenance", "§cThe group is in maintenance§8.");
            document.append("full-join", "§cThe Network is full§8!");
            document.append("no-original-proxy", "§cPlease connect using the original proxy from GlowCloud§8!");
            document.append("no-hub", "§cNo hub server was found, please wait§8.");
            document.append("notify-server-add", "§7The server §8(§e%server%§8) §7is §a§lstarting §7now§8...");
            document.append("notify-server-remove", "§7The server §8(§e%server%§8) §7is now §c§lstopping§8!");
            document.append("already-hub", "§cYou are already connected to a hub server§8.");

            document.save(messagesFile);
            System.out.println("The file \"" + messagesFile.getPath() + "\" was created.");
        }
    }

    public boolean isFirstStart() {
        return firststart;
    }
}
