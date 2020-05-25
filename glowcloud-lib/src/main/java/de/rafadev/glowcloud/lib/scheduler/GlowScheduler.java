package de.rafadev.glowcloud.lib.scheduler;

//------------------------------
//
// This class was developed by RafaDev
// On 20.05.2020 at 11:01
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.interfaces.IGlowCloudObject;
import de.rafadev.glowcloud.lib.scheduler.tasks.GlowSchedulerTask;

import java.util.*;

public class GlowScheduler implements IGlowCloudObject {

    private HashMap<Integer, GlowSchedulerTask> tasks = new HashMap<>();

    public boolean isRunning(int id) {
        if(tasks.containsKey(id)) {
            return tasks.get(id).isRunning();
        } else {
            return false;
        }
    }

    public boolean existTask(int id) {
        return tasks.containsKey(id);
    }

    public void cancel(int id) {
        if(existTask(id)) {
            tasks.get(id).kill();
            tasks.remove(id);
        }
    }

    public int runRepeatingTask(Runnable runnable, int delay, int repeat) {
        int id = new Random().nextInt(Integer.MAX_VALUE - 999);
        GlowSchedulerTask schedulerTask = new GlowSchedulerTask(runnable, delay, repeat);
        schedulerTask.start();
        tasks.put(id, schedulerTask);
        return id;
    }

    public void runWaitTask(Runnable runnable, int delay) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        }, delay);
    }

}
