package de.rafadev.glowcloud.master.network.logging;

//------------------------------
//
// This class was developed by Rafael K.
// On 27.07.2020 at 15:34
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.master.main.GlowCloud;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NetworkLogger {

    private final File logFile = new File("database/logs/network/" + new SimpleDateFormat("dd.MM.yyyy").format(new Date()) + ".log");

    public NetworkLogger() {

        if(!new File("database/logs/network/").exists()) {
            new File("database/logs/network/").mkdirs();
        }

        if(!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void log(String val) {

        try {
            FileWriter fileWriter = new FileWriter(logFile);
            fileWriter.write(val + "\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
