package de.rafadev.glowcloud.lib.template;

//------------------------------
//
// This class was developed by Rafael K.
// On 10.07.2020 at 14:17
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.classes.group.SimpleCloudServerGroup;

public class SimpleCloudTemplate {

    private String name;
    private SimpleCloudServerGroup cloudServerGroup;
    private String version;

    public SimpleCloudTemplate(String name, SimpleCloudServerGroup cloudServerGroup, String version) {
        this.name = name;
        this.cloudServerGroup = cloudServerGroup;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SimpleCloudServerGroup getCloudServerGroup() {
        return cloudServerGroup;
    }

    public void setCloudServerGroup(SimpleCloudServerGroup cloudServerGroup) {
        this.cloudServerGroup = cloudServerGroup;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
