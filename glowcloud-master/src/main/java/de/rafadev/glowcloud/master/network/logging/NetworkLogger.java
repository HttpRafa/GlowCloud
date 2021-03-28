package de.rafadev.glowcloud.master.network.logging;

//------------------------------
//
// This class was developed by Rafael K.
// On 27.07.2020 at 15:34
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.master.main.GlowCloud;

import java.io.*;
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

            BufferedReader bufferedReader = new BufferedReader(new FileReader(logFile));
            StringBuilder builder = new StringBuilder();

            String buffer;
            while ((buffer = bufferedReader.readLine()) != null) {
                builder.append(buffer + "\n");
            }

            FileWriter fileWriter = new FileWriter(logFile);
            fileWriter.write(builder.toString() + "[ " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss@SS").format(new Date()) + " ] " + val + "\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
