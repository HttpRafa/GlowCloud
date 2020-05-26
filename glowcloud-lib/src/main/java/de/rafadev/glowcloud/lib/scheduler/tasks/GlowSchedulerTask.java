package de.rafadev.glowcloud.lib.scheduler.tasks;

//------------------------------
//
// This class was developed by RafaDev
// On 20.05.2020 at 11:03
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.classes.tasks.GlowTask;
import de.rafadev.glowcloud.lib.interfaces.IGlowCloudObject;

import java.util.Timer;
import java.util.TimerTask;

public class GlowSchedulerTask extends GlowTask implements IGlowCloudObject {

    private Timer timer = new Timer();
    private boolean running = false;
    private int delay = 0;
    private int repeat = 0;

    public GlowSchedulerTask(Runnable runnable, int delay, int repeat) {
        super(runnable);
        this.delay = delay;
        this.repeat = repeat;
    }

    public void start() {
        if(delay == 0) {
            running = true;
            execute();
        } else {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    running = true;
                    execute();
                }
            }, delay);
        }
    }

    public void kill() {
        running = false;
    }

    private void execute() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(running) {
                    getRunnable().run();
                    execute();
                }
            }
        }, repeat);
    }

    public boolean isRunning() {
        return running;
    }
}
