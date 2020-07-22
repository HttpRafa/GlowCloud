package de.rafadev.glowcloud.master.event;

//------------------------------
//
// This class was developed by Rafael K.
// On 22.07.2020 at 01:33
// In the project GlowCloud
//
//------------------------------

import com.google.gson.internal.$Gson$Preconditions;
import de.rafadev.glowcloud.master.event.item.CloudEventListenerItem;
import de.rafadev.glowcloud.master.event.listener.CloudEventListener;
import de.rafadev.glowcloud.master.main.GlowCloud;
import de.rafadev.glowcloud.master.module.CloudModule;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class CloudEventManager {

    private List<CloudEventListenerItem> listeners = new LinkedList<>();

    public void callEvent(CloudEvent cloudEvent) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {

        for (CloudEventListenerItem listener : listeners) {
            Class<?> aClass = listener.getCloudEventListener().getClass();
            for (Method method : aClass.getDeclaredMethods()) {
                if(method.isAnnotationPresent(CloudEventHandler.class)) {
                    if(method.getParameters()[0].getType() == cloudEvent.getClass()) {
                        method.invoke(null, cloudEvent);
                    }
                }
            }
        }

    }

    public void register(CloudEventListener cloudEventListener, CloudModule cloudModule) {
        listeners.add(new CloudEventListenerItem(cloudEventListener, cloudModule));
    }

}
