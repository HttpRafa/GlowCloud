package de.rafadev.glowcloud.wrapper.bootstrap;

//------------------------------
//
// This class was developed by Rafael K.
// On 28.05.2020 at 11:36
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.logging.color.ConsoleColor;
import de.rafadev.glowcloud.wrapper.main.GlowCloudWrapper;
import org.fusesource.jansi.AnsiConsole;

public class GlowCloudWrapperBootstrap {

    public static void main(String[] args) {

        if (Float.parseFloat(System.getProperty("java.class.version")) < 52D) {
            System.out.println("This application needs Java 8 or 10.0.1");
            return;
        }

        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("client.encoding.override", "UTF-8");
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");

        AnsiConsole.systemInstall();
        showLogo();

        /*
        Starting the GlowCloud-Wrapper
         */

        try {
            GlowCloudWrapper.run(args);
        } catch (Exception exception) {
            System.out.println("Error by starting GlowCloud-Wrapper.");
            exception.printStackTrace();
        }

    }

    private static void showLogo() {
        System.out.println(ConsoleColor.toColouredString('§', "§e\n" +
                "§e  _____  _                 §6  _____  _                    _ \n" +
                "§e / ____|| |                §6 / ____|| |                  | |\n" +
                "§e| |  __ | |  ___ __      __§6| |     | |  ___   _   _   __| |\n" +
                "§e| | |_ || | / _ \\\\ \\ /\\ / /§6| |     | | / _ \\ | | | | / _` |\n" +
                "§e| |__| || || (_) |\\ V  V / §6| |____ | || (_) || |_| || (_| |\n" +
                "§e \\_____||_| \\___/  \\_/\\_/  §6 \\_____||_| \\___/  \\__,_| \\__,_|§r"));
        System.out.println(ConsoleColor.toColouredString('§', "§8«§e*§8» §eGlowCloud §7Technology §62 §7by §eRafaDev§r"));
        System.out.println(" ");
    }

}
