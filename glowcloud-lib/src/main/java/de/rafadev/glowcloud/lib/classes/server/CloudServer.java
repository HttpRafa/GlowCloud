package de.rafadev.glowcloud.lib.classes.server;

//------------------------------
//
// This class was developed by Rafael K.
// On 28.06.2020 at 19:38
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.classes.group.SimpleCloudServerGroup;
import de.rafadev.glowcloud.lib.interfaces.IGlowCloudObject;
import de.rafadev.glowcloud.lib.network.protocol.packet.PacketSender;
import de.rafadev.glowcloud.lib.template.SimpleCloudTemplate;

import java.util.UUID;

public class CloudServer extends PacketSender implements IGlowCloudObject {

    private String serviceID;
    private UUID uuid;
    private SimpleCloudServerGroup cloudServerGroup;
    private SimpleCloudTemplate template;

    public CloudServer(String serviceID, UUID uuid, SimpleCloudServerGroup cloudServerGroup, SimpleCloudTemplate cloudTemplate) {
        this.serviceID = serviceID;
        this.uuid = uuid;
        this.cloudServerGroup = cloudServerGroup;
        this.template = cloudTemplate;
    }

    public SimpleCloudServerGroup getCloudServerGroup() {
        return cloudServerGroup;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public void setCloudServerGroup(SimpleCloudServerGroup cloudServerGroup) {
        this.cloudServerGroup = cloudServerGroup;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public SimpleCloudTemplate getTemplate() {
        return template;
    }

    public void setTemplate(SimpleCloudTemplate template) {
        this.template = template;
    }
}
