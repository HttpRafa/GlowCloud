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
import de.rafadev.glowcloud.lib.scheduler.GlowScheduler;
import de.rafadev.glowcloud.wrapper.command.CommandManager;
import de.rafadev.glowcloud.wrapper.command.commands.StopCommand;
import de.rafadev.glowcloud.wrapper.file.FileManager;
import de.rafadev.glowcloud.wrapper.key.KeyManager;
import de.rafadev.glowcloud.wrapper.network.manager.NetworkManager;
import de.rafadev.glowcloud.wrapper.version.VersionManager;

import java.io.IOException;

public class GlowCloudWrapper {

    private static GlowCloudWrapper glowCloudWrapper;

    private FileManager fileManager;
    private VersionManager versionManager;
    private NetworkManager networkManager;
    private KeyManager keyManager;
    private CommandManager commandManager;

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

        /*
        Loading all Managers
         */
        networkManager = new NetworkManager(new NetworkAddress("127.0.0.1", 6360));
        commandManager.startReader(logger);

        // Create a Auth
        NetworkAuthentication networkAuthentication = new NetworkAuthentication("Wrapper-1", AuthServiceType.WRAPPER, keyManager.getConnectionKey());

        // Start the Connection
        networkManager.start(networkAuthentication, scheduler);

    }

    public void shutdown() {
        System.exit(0);
    }

    public void exit() {
        if(!fileManager.isFirstStart()) {
            networkManager.shutdown();
            logger.info("Cloud Wrapper is shutting down...");

            logger.overrideLine(1, "Closing the connection to the master. §8[§cClosing§8]");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(networkManager != null && networkManager.getNetworkConnection() != null && networkManager.getNetworkConnection().shutdown()) {
                logger.overrideLine(1, "Closing the connection to the master. §8[§aClosed§8] ");
                logger.nextLine();
            } else {
                logger.overrideLine(1, "Closing the connection to the master. §8[§4Failed§8] ");
                logger.nextLine();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("Stopping all running server... §8[§e1/10§8]");

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
