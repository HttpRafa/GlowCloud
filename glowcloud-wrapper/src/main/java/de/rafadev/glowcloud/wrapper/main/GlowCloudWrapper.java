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
import de.rafadev.glowcloud.wrapper.file.FileManager;
import de.rafadev.glowcloud.wrapper.network.manager.NetworkManager;
import de.rafadev.glowcloud.wrapper.network.packet.handler.DefaultHandler;
import de.rafadev.glowcloud.wrapper.version.VersionManager;

import java.io.IOException;

public class GlowCloudWrapper {

    private static GlowCloudWrapper glowCloudWrapper;

    private FileManager fileManager;
    private VersionManager versionManager;
    private NetworkManager networkManager;

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

        /*
        Loading SchedulerService
         */
        scheduler = new GlowScheduler();

        /*
        Load datas
         */
        versionManager = new VersionManager();

        /*
        Check ID and Versions
         */
        versionManager.checkVersions();

        /*
        Loading all Managers
         */
        networkManager = new NetworkManager(new NetworkAddress("127.0.0.1", 6360));

        // Create a Auth
        NetworkAuthentication networkAuthentication = new NetworkAuthentication("Wrapper-1", AuthServiceType.WRAPPER, "Test");

        // Start the Connection
        networkManager.getNetworkConnection().tryConnect(networkAuthentication, new DefaultHandler(), logger);

    }

    public void shutdown() {
        System.exit(0);
    }

    public void exit() {
        if(!fileManager.isFirstStart()) {
            logger.info("Cloud Wrapper is shutting down...");
        }
    }

    public CloudLogger getLogger() {
        return logger;
    }

    public GlowScheduler getScheduler() {
        return scheduler;
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
