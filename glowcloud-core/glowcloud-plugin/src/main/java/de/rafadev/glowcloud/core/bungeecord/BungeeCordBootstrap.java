package de.rafadev.glowcloud.core.bungeecord;

//------------------------------
//
// This class was developed by Rafael K.
// On 23.07.2020 at 13:25
// In the project GlowCloud
//
//------------------------------

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeCordBootstrap extends Plugin {

    @Override
    public void onEnable() {

    }

    @Override
    public void onLoad() {
        
        /*
        Show the GlowCloud logo
         */
        ProxyServer.getInstance().getConsole().sendMessage(" ");
        ProxyServer.getInstance().getConsole().sendMessage("§e  _____  _                 §6  _____  _                    _ ");
        ProxyServer.getInstance().getConsole().sendMessage("§e / ____|| |                §6 / ____|| |                  | |");
        ProxyServer.getInstance().getConsole().sendMessage("§e| |  __ | |  ___ __      __§6| |     | |  ___   _   _   __| |");
        ProxyServer.getInstance().getConsole().sendMessage("§e| | |_ || | / _ \\\\ \\ /\\ / /§6| |     | | / _ \\ | | | | / _` |");
        ProxyServer.getInstance().getConsole().sendMessage("§e| |__| || || (_) |\\ V  V / §6| |____ | || (_) || |_| || (_| |");
        ProxyServer.getInstance().getConsole().sendMessage("§e \\_____||_| \\___/  \\_/\\_/  §6 \\_____||_| \\___/  \\__,_| \\__,_|");
        ProxyServer.getInstance().getConsole().sendMessage("§8«§e*§8» §eGlowCloud §7Technology §62 §7by §eRafaDev");
        ProxyServer.getInstance().getConsole().sendMessage(" ");
        
    }

    @Override
    public void onDisable() {

    }
}
