package de.rafadev.glowcloud.master.group.proxy;

//------------------------------
//
// This class was developed by Rafael K.
// On 01.06.2020 at 18:19
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.classes.group.SimpleCloudServerGroup;
import de.rafadev.glowcloud.master.group.classes.CloudServerGroup;
import de.rafadev.glowcloud.master.template.CloudTemplateSystem;

public class CloudProxyGroup extends CloudServerGroup {

    private CloudTemplateSystem cloudTemplateSystem;

    public CloudProxyGroup(SimpleCloudServerGroup simpleCloudServerGroup) {
        super(simpleCloudServerGroup);
        cloudTemplateSystem = new CloudTemplateSystem(this);
    }

    public CloudTemplateSystem getCloudTemplateSystem() {
        return cloudTemplateSystem;
    }
}
