package de.rafadev.glowcloud.lib.classes.tasks;

//------------------------------
//
// This class was developed by RafaDev
// On 20.05.2020 at 11:01
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.interfaces.IGlowCloudObject;

public class GlowTask implements IGlowCloudObject {

    private Runnable runnable;

    public GlowTask(Runnable runnable) {
        this.runnable = runnable;
    }

    public void run() {
        runnable.run();
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }
}
