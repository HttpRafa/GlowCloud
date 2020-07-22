package de.rafadev.glowcloud.master.event;

//------------------------------
//
// This class was developed by Rafael K.
// On 22.07.2020 at 10:43
// In the project GlowCloud
//
//------------------------------

public class CloudEvent {

    private boolean cancelled = false;

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
