package de.rafadev.glowcloud.core.bukkit;

//------------------------------
//
// This class was developed by Rafael K.
// On 23.07.2020 at 13:25
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.core.api.GlowCloudAPI;
import de.rafadev.glowcloud.core.bridge.GlowCloudBridge;
import de.rafadev.glowcloud.core.bridge.authentication.BridgeAuthentication;
import de.rafadev.glowcloud.core.bridge.network.result.ConnectTryResult;
import de.rafadev.glowcloud.lib.document.Document;
import de.rafadev.glowcloud.lib.network.address.NetworkAddress;
import de.rafadev.glowcloud.lib.network.utils.CloudUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class BukkitBootstrap extends JavaPlugin {

    @Override
    public void onEnable() {

    }

    @Override
    public void onLoad() {

        /*
        Show the GlowCloud logo
         */
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage("§e  _____  _                 §6  _____  _                    _ ");
        Bukkit.getConsoleSender().sendMessage("§e / ____|| |                §6 / ____|| |                  | |");
        Bukkit.getConsoleSender().sendMessage("§e| |  __ | |  ___ __      __§6| |     | |  ___   _   _   __| |");
        Bukkit.getConsoleSender().sendMessage("§e| | |_ || | / _ \\\\ \\ /\\ / /§6| |     | | / _ \\ | | | | / _` |");
        Bukkit.getConsoleSender().sendMessage("§e| |__| || || (_) |\\ V  V / §6| |____ | || (_) || |_| || (_| |");
        Bukkit.getConsoleSender().sendMessage("§e \\_____||_| \\___/  \\_/\\_/  §6 \\_____||_| \\___/  \\__,_| \\__,_|");
        Bukkit.getConsoleSender().sendMessage("§8«§e*§8» §eGlowCloud §7Technology §62 §7by §eRafaDev");
        Bukkit.getConsoleSender().sendMessage(" ");

        /*
        Init the CloudBridge
         */
        
        // Create a network auth

        BridgeAuthentication authentication = null;
        NetworkAddress networkAddress = null;
        
        try {
            Document document = Document.load(new File(".cloud/service.json"));
            
            authentication = new BridgeAuthentication(document.getAsString("serviceID"), document.getAsString("authKey"));
            networkAddress = new NetworkAddress(document.getAsString("masterHost"), document.getAsNumber("masterPort").intValue());
        } catch (Exception e) {
            shutdown("Can`t load the service.json file!");
            return;
        }
        
        new GlowCloudBridge(authentication);

        /*
        Init the GlowCloudAPI
         */

        new GlowCloudAPI();

        /*
           Connecting to the master
         */

        Bukkit.getConsoleSender().sendMessage("§aTry to connect to the master...");
        ConnectTryResult result = GlowCloudBridge.getBridge().getNetworkBridge().connectToMaster(networkAddress);
        if(result.getState() == 0) {
            shutdown("Can`t connect to the master!");
            return;
        }

    }
    
    public void shutdown(String reason) {
        
        Bukkit.getConsoleSender().sendMessage("§4" + reason);
        Bukkit.getConsoleSender().sendMessage("§cStopping the cloud server....");

        CloudUtils.sleep(1500);
        Bukkit.shutdown();
        
    }

    @Override
    public void onDisable() {

    }
}
