package de.rafadev.glowcloud.master.group.setup;

//------------------------------
//
// This class was developed by RafaDev
// On 23.05.2020 at 21:09
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.enums.GroupMode;
import de.rafadev.glowcloud.lib.enums.ServerType;

public class GroupSetupResult {

    private String name;
    private String wrapperID;

    private int minServerCount;
    private int memory;

    private GroupMode groupMode;
    private ServerType serverType;

    public GroupSetupResult() {

    }

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

    public int getMinServerCount() {
        return minServerCount;
    }

    public void setMinServerCount(int minServerCount) {
        this.minServerCount = minServerCount;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public GroupMode getGroupMode() {
        return groupMode;
    }

    public void setGroupMode(GroupMode groupMode) {
        this.groupMode = groupMode;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public void setServerType(ServerType serverType) {
        this.serverType = serverType;
    }
}
