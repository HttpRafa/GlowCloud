package de.rafadev.glowcloud.wrapper.main;

//------------------------------
//
// This class was developed by Rafael K.
// On 28.05.2020 at 11:37
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.logging.CloudLogger;
import de.rafadev.glowcloud.lib.scheduler.GlowScheduler;

import java.io.IOException;

public class GlowCloudWrapper {

    private static GlowCloudWrapper glowCloudWrapper;

    private CloudLogger logger;
    private GlowScheduler scheduler;

    public GlowCloudWrapper() throws IOException {
        glowCloudWrapper = this;

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

    }

    public CloudLogger getLogger() {
        return logger;
    }

    public GlowScheduler getScheduler() {
        return scheduler;
    }

    public static void run(String[] args) throws Exception {
        new GlowCloudWrapper();
    }

    public static GlowCloudWrapper getGlowCloud() {
        return glowCloudWrapper;
    }

}
