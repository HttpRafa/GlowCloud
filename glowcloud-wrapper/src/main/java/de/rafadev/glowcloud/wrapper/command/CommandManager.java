package de.rafadev.glowcloud.wrapper.command;

//------------------------------
//
// This class was developed by Rafael K.
// On 27.06.2020 at 15:51
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.logging.CloudLogger;
import de.rafadev.glowcloud.lib.logging.color.ConsoleColor;
import de.rafadev.glowcloud.wrapper.command.executer.Command;
import de.rafadev.glowcloud.wrapper.main.GlowCloudWrapper;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandManager {

    private List<Command> commandList = new LinkedList<>();

    public void startReader(CloudLogger logger) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                String user = System.getProperty("user.name");

                String commandLine;
                try {
                    while (true) {
                        while ((commandLine = logger.readLine(ConsoleColor.toColouredString('§', "§8[ §6" + user + "§8@§eGlowCloud §8] §7- "))) != null) {
                            if (!(commandLine.equalsIgnoreCase(""))) {
                                String key = commandLine.split(" ")[0];
                                List<Command> selected = commandList.stream().filter(command -> command.getName().equalsIgnoreCase(key.trim())).collect(Collectors.toList());
                                if (selected.size() == 0) {
                                    logger.error("§cCommand not found§8. §cUse the command §8\"§4help§8\"§c for further information§8!");
                                } else {
                                    for (Command command : selected) {
                                        String[] args = commandLine.replaceAll(key, "").trim().split(" ");
                                        if (args.length == 1) {
                                            if (args[0].equalsIgnoreCase("") || args[0].equalsIgnoreCase(" ")) {
                                                args = new String[]{};
                                            }
                                        }
                                        command.execute(args);
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception ignored) {

                }
            }
        }, "Command Thread").start();
    }

    public String requestString() {
        String user = System.getProperty("user.name");

        String commandLine;
        try {
            while (true) {
                while ((commandLine = GlowCloudWrapper.getGlowCloud().getLogger().readLine(ConsoleColor.toColouredString('§', "§8[ §6" + user + "§8@§eGlowCloud §8] §7- "))) != null) {
                    if(!(commandLine.equalsIgnoreCase(""))) {
                        return commandLine;
                    }
                }
            }
        } catch (Exception ignored) {

        }
        return "";
    }


    public void registerCommand(Command command) {
        commandList.add(command);
    }

    public List<Command> getCommandList() {
        return commandList;
    }

}
