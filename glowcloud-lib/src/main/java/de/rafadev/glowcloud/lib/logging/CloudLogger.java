package de.rafadev.glowcloud.lib.logging;

//------------------------------
//
// This class was developed by RafaDev
// On 17.05.2020 at 14:23
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.logging.color.ConsoleColor;
import de.rafadev.glowcloud.lib.logging.exeption.ExceptionHandler;
import jline.console.ConsoleReader;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CloudLogger {

    private ConsoleReader consoleReader;
    private ExceptionHandler exeptionHandler;

    private boolean debugging = false;

    public CloudLogger() throws IOException {

        consoleReader = new ConsoleReader(System.in, System.out);
        consoleReader.setExpandEvents(false);

        consoleReader.setPrompt("");
        consoleReader.resetPromptLine("", "", 0);

        exeptionHandler = new ExceptionHandler(this);

        info("Logger was loaded.");

    }

    public void handleException(Exception exception) {
        exeptionHandler.handle(exception);
    }

    public void overrideLine(int id, Object object) {
        try {
            if(id == 1) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                Date currentTime = new Date();
                String formatstr = ConsoleColor.toColouredString('§', "§8") + "[ " + ConsoleColor.toColouredString('§', "§e") + formatter.format(currentTime) + ConsoleColor.toColouredString('§', "§8") +" ]";
                consoleReader.print(ConsoleReader.RESET_LINE + formatstr + ConsoleColor.toColouredString('§', "§7") + " INFO " + ConsoleColor.toColouredString('§', "§8") + "» " + ConsoleColor.toColouredString('§', "§7") + ConsoleColor.toColouredString('§', object.toString() + "§r"));
            }
            if(id == 2) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                Date currentTime = new Date();
                String formatstr = ConsoleColor.toColouredString('§', "§8") + "[ " + ConsoleColor.toColouredString('§', "§e") + formatter.format(currentTime) + ConsoleColor.toColouredString('§', "§8") +" ]";
                consoleReader.print(ConsoleReader.RESET_LINE + formatstr + ConsoleColor.toColouredString('§', "§4") + " ERROR " + ConsoleColor.toColouredString('§', "§8") + "» " + ConsoleColor.toColouredString('§', "§c") + ConsoleColor.toColouredString('§', object.toString() + "§r"));
            }
            //consoleReader.drawLine();
            consoleReader.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearConsole() {
        try {
            consoleReader.clearScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void nextLine() {
        try {
            consoleReader.print("\n");
            consoleReader.drawLine();
            consoleReader.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void info(Object obj) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date currentTime = new Date();
        String formatstr = ConsoleColor.toColouredString('§', "§8") + "[ " + ConsoleColor.toColouredString('§', "§e") + formatter.format(currentTime) + ConsoleColor.toColouredString('§', "§8") +" ]";
        try {
            consoleReader.print(ConsoleReader.RESET_LINE + formatstr + ConsoleColor.toColouredString('§', "§7") + " INFO " + ConsoleColor.toColouredString('§', "§8") + "» " + ConsoleColor.toColouredString('§', "§7") + ConsoleColor.toColouredString('§', obj.toString()) + ConsoleColor.toColouredString('§', "§r") + "\n");
            consoleReader.drawLine();
            consoleReader.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void error(Object obj) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date currentTime = new Date();
        String formatstr = ConsoleColor.toColouredString('§', "§8") + "[ " + ConsoleColor.toColouredString('§', "§e") + formatter.format(currentTime) + ConsoleColor.toColouredString('§', "§8") +" ]";
        try {
            consoleReader.print(ConsoleReader.RESET_LINE + formatstr + ConsoleColor.toColouredString('§', "§4") + " ERROR " + ConsoleColor.toColouredString('§', "§8") + "» " + ConsoleColor.toColouredString('§', "§c") + ConsoleColor.toColouredString('§', obj.toString()) + ConsoleColor.toColouredString('§', "§r") + "\n");
            consoleReader.drawLine();
            consoleReader.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void warning(Object obj) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date currentTime = new Date();
        String formatstr = ConsoleColor.toColouredString('§', "§8") + "[ " + ConsoleColor.toColouredString('§', "§e") + formatter.format(currentTime) + ConsoleColor.toColouredString('§', "§8") +" ]";
        try {
            consoleReader.print(ConsoleReader.RESET_LINE + formatstr + ConsoleColor.toColouredString('§', "§c") + " WARNING " + ConsoleColor.toColouredString('§', "§8") + "» " + ConsoleColor.toColouredString('§', "§c") + ConsoleColor.toColouredString('§', obj.toString()) + ConsoleColor.toColouredString('§', "§r") + "\n");
            consoleReader.drawLine();
            consoleReader.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void debug(Object obj) {
        if(debugging) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            Date currentTime = new Date();
            String formatstr = ConsoleColor.toColouredString('§', "§8") + "[ " + ConsoleColor.toColouredString('§', "§e") + formatter.format(currentTime) + ConsoleColor.toColouredString('§', "§8") +" ]";
            try {
                consoleReader.print(ConsoleReader.RESET_LINE + formatstr + ConsoleColor.toColouredString('§', "§b") + " DEBUG " + ConsoleColor.toColouredString('§', "§8") + "» " + ConsoleColor.toColouredString('§', "§7") + ConsoleColor.toColouredString('§', obj.toString()) + ConsoleColor.toColouredString('§', "§r") + "\n");
                consoleReader.drawLine();
                consoleReader.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String readLine(String prompt) {
        try {
            String line = this.consoleReader.readLine(prompt);
            this.consoleReader.setPrompt("");
            return line;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isDebugging() {
        return debugging;
    }

    public void setDebugging(boolean debugging) {
        this.debugging = debugging;
    }


}
