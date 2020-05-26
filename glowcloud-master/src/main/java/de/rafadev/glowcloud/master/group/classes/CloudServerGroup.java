package de.rafadev.glowcloud.master.group.classes;

//------------------------------
//
// This class was developed by RafaDev
// On 22.05.2020 at 12:45
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.classes.group.SimpleCloudServerGroup;
import de.rafadev.glowcloud.lib.enums.GroupMode;
import de.rafadev.glowcloud.lib.enums.ServerType;

public class CloudServerGroup {

    private final SimpleCloudServerGroup simpleCloudServerGroup;

    public CloudServerGroup(SimpleCloudServerGroup simpleCloudServerGroup) {
        this.simpleCloudServerGroup = simpleCloudServerGroup;
    }

    public String getName() {
        return simpleCloudServerGroup.getName();
    }

    public void setName(String name) {
        simpleCloudServerGroup.setName(name);
    }

    public String getWrapperID() {
        return simpleCloudServerGroup.getWrapperID();
    }

    public void setWrapperID(String wrapperID) {
        simpleCloudServerGroup.setWrapperID(wrapperID);
    }

    public boolean isFallback() {
        return simpleCloudServerGroup.isFallback();
    }

    public void setFallback(boolean fallback) {
        simpleCloudServerGroup.setFallback(fallback);
    }

    public boolean isMaintenance() {
        return simpleCloudServerGroup.isMaintenance();
    }

    public void setMaintenance(boolean maintenance) {
        simpleCloudServerGroup.setMaintenance(maintenance);
    }

    public ServerType getServerType() {
        return simpleCloudServerGroup.getServerType();
    }

    public void setServerType(ServerType serverType) {
        simpleCloudServerGroup.setServerType(serverType);
    }

    public GroupMode getGroupMode() {
        return simpleCloudServerGroup.getGroupMode();
    }

    public void setGroupMode(GroupMode groupMode) {
        simpleCloudServerGroup.setGroupMode(groupMode);
    }

    public int getMemory() {
        return simpleCloudServerGroup.getMemory();
    }

    public void setMemory(int memory) {
        simpleCloudServerGroup.setMemory(memory);
    }

    public int getDynamicMemory() {
        return simpleCloudServerGroup.getDynamicMemory();
    }

    public void setDynamicMemory(int dynamicMemory) {
        simpleCloudServerGroup.setDynamicMemory(dynamicMemory);
    }

    public int getMinServerCount() {
        return simpleCloudServerGroup.getMinServerCount();
    }

    public void setMinServerCount(int minServerCount) {
        simpleCloudServerGroup.setMinServerCount(minServerCount);
    }

    public int getMaxServerCount() {
        return simpleCloudServerGroup.getMaxServerCount();
    }

    public void setMaxServerCount(int maxServerCount) {
        simpleCloudServerGroup.setMaxServerCount(maxServerCount);
    }

    public int getNewServerPercent() {
        return simpleCloudServerGroup.getNewServerPercent();
    }

    public void setNewServerPercent(int newServerPercent) {
        simpleCloudServerGroup.setNewServerPercent(newServerPercent);
    }

}
