package de.rafadev.glowcloud.wrapper.main;

//------------------------------
//
// This class was developed by Rafael K.
// On 28.05.2020 at 11:37
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.logging.CloudLogger;
import de.rafadev.glowcloud.lib.network.address.NetworkAddress;
import de.rafadev.glowcloud.lib.network.auth.NetworkAuthentication;
import de.rafadev.glowcloud.lib.network.auth.types.AuthServiceType;
import de.rafadev.glowcloud.lib.network.utils.CloudUtils;
import de.rafadev.glowcloud.lib.scheduler.GlowScheduler;
import de.rafadev.glowcloud.wrapper.command.CommandManager;
import de.rafadev.glowcloud.wrapper.command.commands.DebugCommand;
import de.rafadev.glowcloud.wrapper.command.commands.HelpCommand;
import de.rafadev.glowcloud.wrapper.command.commands.StopCommand;
import de.rafadev.glowcloud.wrapper.file.FileManager;
import de.rafadev.glowcloud.wrapper.key.KeyManager;
import de.rafadev.glowcloud.wrapper.network.manager.NetworkManager;
import de.rafadev.glowcloud.wrapper.server.ServerManager;
import de.rafadev.glowcloud.wrapper.version.VersionManager;

import java.io.IOException;

public class GlowCloudWrapper {

    private static GlowCloudWrapper glowCloudWrapper;

    private FileManager fileManager;
    private VersionManager versionManager;
    private NetworkManager networkManager;
    private KeyManager keyManager;
    private CommandManager commandManager;
    private ServerManager serverManager;

    private CloudLogger logger;
    private GlowScheduler scheduler;

    public GlowCloudWrapper() throws IOException, InterruptedException {
        glowCloudWrapper = this;

        /*
        Init FileSystem
         */
        fileManager = new FileManager();
        if(fileManager.isFirstStart()) {
            System.out.println("The cloud wrapper is running for the first time.");
            System.out.println("Creating all files...");
            fileManager.checkAllFiles();
            System.out.println("The Cloud Wrapper is now ready to work. Thanks for using GlowCloud.");
            Thread.sleep(1500);
            //shutdown(0);
            System.exit(0);
            return;
        }

        /*
        Starting LoggingService
         */
        logger = new CloudLogger();
        commandManager = new CommandManager();

        /*
        Loading SchedulerService
         */
        scheduler = new GlowScheduler();

        /*
        Load datas
         */
        fileManager.preCheckFiles();
        versionManager = new VersionManager();
        keyManager = new KeyManager();


        /*
        Check ID and Versions
         */
        versionManager.checkVersions();

        /*
        Register all Commands
         */
        commandManager.registerCommand(new StopCommand("stop", null, "Stops the GlowCloud Wrapper application"));
        commandManager.registerCommand(new StopCommand("exit", null, "Stops the GlowCloud Wrapper application"));
        commandManager.registerCommand(new HelpCommand("help", null, "Displays all executable commands"));
        commandManager.registerCommand(new DebugCommand("debug", null, "Enable the DebugMode for the Cloud"));

        /*
        Loading all Managers
         */
        networkManager = new NetworkManager(new NetworkAddress(fileManager.getConfig().getAsString("masterAddress"), fileManager.getConfig().get("masterPort").getAsInt()));
        commandManager.startReader(logger);
        serverManager = new ServerManager();
        serverManager.startQueue();

        // Create a Auth
        NetworkAuthentication networkAuthentication = new NetworkAuthentication(fileManager.getConfig().getAsString("id"), AuthServiceType.WRAPPER, keyManager.getConnectionKey());

        // Start the Connection
        networkManager.start(networkAuthentication, scheduler);

    }

    public void reset() {
        GlowCloudWrapper.getGlowCloud().getLogger().warning("§cThe wrapper is now reset§8.");
        networkManager.setCheckConnection(false);

        serverManager.resetAllServers();

        CloudUtils.sleep(1000);

        networkManager.setCheckConnection(true);

    }

    public void shutdown() {
        System.exit(0);
    }

    public void exit() {
        if(!fileManager.isFirstStart()) {
            networkManager.shutdown();
            logger.info("Cloud Wrapper is shutting down...");

            logger.overrideLine(1, "Closing the connection to the master. §8[§6Closing§8]");

            CloudUtils.sleep(500);

            if(networkManager != null && networkManager.getNetworkConnection() != null && networkManager.getNetworkConnection().shutdown()) {
                logger.overrideLine(1, "Closing the connection to the master. §8[§cClosed§8] ");
                logger.nextLine();
            } else {
                logger.overrideLine(1, "Closing the connection to the master. §8[§4Failed§8] ");
                logger.nextLine();
            }

            CloudUtils.sleep(500);

            logger.info("§7Thanks for using §eGlowCloud§8.");

        }
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public CloudLogger getLogger() {
        return logger;
    }

    public GlowScheduler getScheduler() {
        return scheduler;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public VersionManager getVersionManager() {
        return versionManager;
    }

    public ServerManager getServerManager() {
        return serverManager;
    }

    public Runtime getRuntime() {
        return Runtime.getRuntime();
    }

    public static void run(String[] args) throws Exception {
        new GlowCloudWrapper();
    }

    public static GlowCloudWrapper getGlowCloud() {
        return glowCloudWrapper;
    }

}
