package de.rafadev.glowcloud.master.main;

//------------------------------
//
// This class was developed by RafaDev
// On 17.05.2020 at 14:01
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.logging.CloudLogger;
import de.rafadev.glowcloud.lib.network.address.NetworkAddress;
import de.rafadev.glowcloud.lib.scheduler.GlowScheduler;
import de.rafadev.glowcloud.master.command.CommandManager;
import de.rafadev.glowcloud.master.command.commands.*;
import de.rafadev.glowcloud.master.file.FileManager;
import de.rafadev.glowcloud.master.group.GroupManager;
import de.rafadev.glowcloud.master.key.KeyManager;
import de.rafadev.glowcloud.master.module.manager.CloudModuleManager;
import de.rafadev.glowcloud.master.network.manager.NetworkManager;
import de.rafadev.glowcloud.master.wrapper.WrapperManager;

public class GlowCloud {

    private static GlowCloud glowCloud;
    private CloudLogger logger;

    private boolean started = false;

    private CommandManager commandManager;
    private FileManager fileManager;
    private GroupManager groupManager;
    private NetworkManager networkManager;
    private CloudModuleManager moduleManager;
    private KeyManager keyManager;
    private WrapperManager wrapperManager;

    private GlowScheduler scheduler;

    public GlowCloud() throws Exception {
        glowCloud = this;

        long startTime = System.currentTimeMillis();

        /*
        Init FileSystem
         */
        fileManager = new FileManager();
        if(fileManager.isFirstStart()) {
            System.out.println("The cloud is running for the first time.");
            System.out.println("Creating all files...");
            fileManager.checkAllFiles();
            System.out.println("The Cloud is now ready to work. Thanks for using GlowCloud.");
            Thread.sleep(1500);
            //shutdown();
            System.exit(0);
            return;
        }

        /*
        Starting LoggingService
         */
        logger = new CloudLogger();

        logger.info("Starting SchedulerService...");
        /*
        Loading SchedulerService
         */
        scheduler = new GlowScheduler();

        /*
        Loading all Managers
         */
        wrapperManager = new WrapperManager();
        groupManager = new GroupManager();
        commandManager = new CommandManager(logger);
        moduleManager = new CloudModuleManager();
        keyManager = new KeyManager();


        /*
        Register All Command
         */
        commandManager.registerCommand(new StopCommand("stop", null, "Stops the GlowCloud application"));
        commandManager.registerCommand(new StopCommand("exit", null, "Stops the GlowCloud application"));
        commandManager.registerCommand(new HelpCommand("help", null, "Displays all executable commands"));
        commandManager.registerCommand(new DebugCommand("debug", null, "Enable the DebugMode for the Cloud"));
        commandManager.registerCommand(new ClearCommand("clear", null, "Deletes the displayed console"));
        commandManager.registerCommand(new ReloadCommand("reload", "§8<§eall, configs§8>", "Reload the GlowCloud application"));
        commandManager.registerCommand(new CreateCommand("create", "§8<§eServerGroup§8>", "Creates a servergroup or a wrapper"));
        commandManager.registerCommand(new GroupCommand("group", "§8<§eset§8> <§emaintenance§8> §8<§evalue§8>", "Manage a servergroup"));
        commandManager.registerCommand(new StartCommand("start", "§8<§egroup§8> <§eamount§8>", "Starts server for a group"));
        commandManager.registerCommand(new ShutdownCommand("shutdown", "§8<§eServer§7/§eGroup§7/§eWrapper§8> §8<§eName§8>", "Shutdown a server or a group or a wrapper"));
        commandManager.registerCommand(new ScreenCommand("screen", "§8<§eServer§8>", "View the console messages of a server"));
        commandManager.registerCommand(new CopyCommand("copy", "§8<§eServer§8> §8<§eTemplate§8>", "Copy all data from a server into the template"));
        commandManager.registerCommand(new CommandCommand("command", "§8<§eServer§8> <§eCommand[]...§8>", "Executes a command on a server"));

        /*
         Start GlowCloud Services
         */

        /*
         Load all Modules
         */
        moduleManager.loadModules();

        /*
        Starting NetworkSystem
         */
        networkManager = new NetworkManager();
        networkManager.startNetworkServer(new NetworkAddress("127.0.0.1", fileManager.getConfig().get("cloud").getAsJsonObject().get("port").getAsInt()));

        /*
        Load all Wrappers
         */
        wrapperManager.loadWrappers();

        /*
         Load all ServerGroup
         */
        groupManager.loadGroups();

        /*
         Enable all Modules
         */
        moduleManager.enableModules();

        /*
         Start Cloud main actions
         */
        started = true;
        logger.info("The cloud was started in §e" + (System.currentTimeMillis() - startTime) + " §7ms§8.");
        logger.info("For help, write §8\"§ehelp§8\".");
    }

    public void reloadAll() {

        GlowCloud.getGlowCloud().getLogger().warning("There may be serious errors when reloading these are not supported§8.");
        GlowCloud.getGlowCloud().getLogger().info("Reloading the cloud§8...");

        /*
        Disable all Modules
         */
        moduleManager.disableModules();

        /*
         Load all Modules
         */
        moduleManager.loadModules();

        /*
         Reload all ServerGroup
         */
        groupManager.reload();

        /*
         Enable all Modules
         */
        moduleManager.enableModules();

    }

    public void shutdown() {
        System.exit(0);
    }

    public void exit() {
        if(!fileManager.isFirstStart()) {
            logger.info("Cloud is shutting down...");

            logger.overrideLine(1, "Closing the NetworkServer... §8[§cClosing§8]");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(networkManager != null && networkManager.getNetworkServer() != null && networkManager.getNetworkServer().shutdown()) {
                logger.overrideLine(1, "Closing the NetworkServer... §8[§aClosed§8] ");
            } else {
                logger.overrideLine(1, "Closing the NetworkServer... §8[§4Failed§8] ");
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            logger.info("§7Thanks for using §eGlowCloud§8.");
        }
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public GlowScheduler getScheduler() {
        return scheduler;
    }

    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public WrapperManager getWrapperManager() {
        return wrapperManager;
    }

    public CloudModuleManager getModuleManager() {
        return moduleManager;
    }

    public GroupManager getGroupManager() {
        return groupManager;
    }

    public CloudLogger getLogger() {
        return logger;
    }

    public boolean isStarted() {
        return started;
    }

    public static void run(String[] args) throws Exception {
        new GlowCloud();
    }

    public static GlowCloud getGlowCloud() {
        return glowCloud;
    }
}
