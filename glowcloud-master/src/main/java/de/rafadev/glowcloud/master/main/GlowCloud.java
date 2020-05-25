package de.rafadev.glowcloud.master.main;

//------------------------------
//
// This class was developed by RafaDev
// On 17.05.2020 at 14:01
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.logging.CloudLogger;
import de.rafadev.glowcloud.lib.scheduler.GlowScheduler;
import de.rafadev.glowcloud.master.command.CommandManager;
import de.rafadev.glowcloud.master.command.commands.*;

public class GlowCloud {

    private static GlowCloud glowCloud;
    private CloudLogger logger;

    private CommandManager commandManager;

    private GlowScheduler scheduler;

    public GlowCloud() throws Exception {
        glowCloud = this;

        /*
        Starting LoggingService
         */
        logger = new CloudLogger();

        /*
        Loading SchedulerService
         */
        scheduler = new GlowScheduler();

        /*
        Loading all Managers
         */
        commandManager = new CommandManager(logger);


        logger.handleException(new NullPointerException());

        /*
        Register All Command
         */
        commandManager.registerCommand(new StopCommand("stop", null, "Stops the GlowCloud application"));
        commandManager.registerCommand(new StopCommand("exit", null, "Stops the GlowCloud application"));
        commandManager.registerCommand(new HelpCommand("help", null, "Displays all executable commands"));
        commandManager.registerCommand(new CreateCommand("create", "§8<§eServerGroup§7/§eWrapper§8>", "Creates a servergroup or a wrapper"));
        commandManager.registerCommand(new GroupCommand("group", "§8<§eset§8> <§emaintenance§8> §8<§evalue§8>", "Manage a servergroup"));
        commandManager.registerCommand(new StartCommand("start", "§8<§egroup§8> <§eamount§8>", "Starts server for a group"));
        commandManager.registerCommand(new ShutdownCommand("shutdown", "§8<§eServer§7/§eGroup§7/§eWrapper§8> §8<§eName§8>", "Shutdown a server or a group or a wrapper"));
        commandManager.registerCommand(new ScreenCommand("screen", "§8<§eServer§8>", "View the console messages of a server"));
        commandManager.registerCommand(new CopyCommand("copy", "§8<§eServer§8> §8<§eTemplate§8>", "Copy all data from a server into the template"));
        commandManager.registerCommand(new CommandCommand("command", "§8<§eServer§8> <§eCommand[]...§8>", "Executes a command on a server"));
    }

    public void shutdown() {
        System.exit(0);
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public GlowScheduler getScheduler() {
        return scheduler;
    }

    public CloudLogger getLogger() {
        return logger;
    }

    public static void run(String[] args) throws Exception {
        new GlowCloud();
    }

    public static GlowCloud getGlowCloud() {
        return glowCloud;
    }
}
