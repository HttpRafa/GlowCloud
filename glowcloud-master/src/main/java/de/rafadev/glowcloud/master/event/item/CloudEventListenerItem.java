package de.rafadev.glowcloud.master.event.item;

//------------------------------
//
// This class was developed by Rafael K.
// On 22.07.2020 at 10:39
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.master.event.listener.CloudEventListener;
import de.rafadev.glowcloud.master.module.CloudModule;

public class CloudEventListenerItem {

    private CloudEventListener cloudEventListener;
    private CloudModule cloudModule;

    public CloudEventListenerItem(CloudEventListener cloudEventListener, CloudModule cloudModule) {
        this.cloudEventListener = cloudEventListener;
        this.cloudModule = cloudModule;
    }

    public CloudEventListener getCloudEventListener() {
        return cloudEventListener;
    }

    public void setCloudEventListener(CloudEventListener cloudEventListener) {
        this.cloudEventListener = cloudEventListener;
    }

    public CloudModule getCloudModule() {
        return cloudModule;
    }

    public void setCloudModule(CloudModule cloudModule) {
        this.cloudModule = cloudModule;
    }
}
