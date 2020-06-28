package de.rafadev.glowcloud.lib.classes.group;

//------------------------------
//
// This class was developed by RafaDev
// On 22.05.2020 at 11:21
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.enums.GroupMode;
import de.rafadev.glowcloud.lib.enums.ServerType;
import de.rafadev.glowcloud.lib.interfaces.IGlowCloudObject;

import java.util.List;

public class SimpleCloudServerGroup implements IGlowCloudObject {

    private String name;
    private String wrapperID;

    private boolean fallback;
    private boolean maintenance;

    private ServerType serverType;
    private GroupMode groupMode;

    private int memory;
    private int dynamicMemory;
    private int minServerCount;
    private int maxServerCount;
    private int newServerPercent;

    public SimpleCloudServerGroup(String name, String wrapperID, boolean fallback, boolean maintenance, ServerType serverType, GroupMode groupMode, int memory, int dynamicMemory, int minServerCount, int maxServerCount, int newServerPercent) {
        this.name = name;
        this.wrapperID = wrapperID;
        this.fallback = fallback;
        this.maintenance = maintenance;
        this.serverType = serverType;
        this.groupMode = groupMode;
        this.memory = memory;
        this.dynamicMemory = dynamicMemory;
        this.minServerCount = minServerCount;
        this.maxServerCount = maxServerCount;
        this.newServerPercent = newServerPercent;
    }

    public SimpleCloudServerGroup() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWrapperID() {
        return wrapperID;
    }

    public void setWrapperID(String wrapperID) {
        this.wrapperID = wrapperID;
    }

    public boolean isFallback() {
        return fallback;
    }

    public void setFallback(boolean fallback) {
        this.fallback = fallback;
    }

    public boolean isMaintenance() {
        return maintenance;
    }

    public void setMaintenance(boolean maintenance) {
        this.maintenance = maintenance;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public void setServerType(ServerType serverType) {
        this.serverType = serverType;
    }

    public GroupMode getGroupMode() {
        return groupMode;
    }

    public void setGroupMode(GroupMode groupMode) {
        this.groupMode = groupMode;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getDynamicMemory() {
        return dynamicMemory;
    }

    public void setDynamicMemory(int dynamicMemory) {
        this.dynamicMemory = dynamicMemory;
    }

    public int getMinServerCount() {
        return minServerCount;
    }

    public void setMinServerCount(int minServerCount) {
        this.minServerCount = minServerCount;
    }

    public int getMaxServerCount() {
        return maxServerCount;
    }

    public void setMaxServerCount(int maxServerCount) {
        this.maxServerCount = maxServerCount;
    }

    public int getNewServerPercent() {
        return newServerPercent;
    }

    public void setNewServerPercent(int newServerPercent) {
        this.newServerPercent = newServerPercent;
    }
}
