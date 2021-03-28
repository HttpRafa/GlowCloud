package de.rafadev.glowcloud.master.group.bukkit;

//------------------------------
//
// This class was developed by Rafael K.
// On 01.06.2020 at 18:23
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.classes.group.SimpleCloudServerGroup;
import de.rafadev.glowcloud.master.group.classes.CloudServerGroup;
import de.rafadev.glowcloud.master.template.CloudTemplateSystem;

public class CloudBukkitGroup extends CloudServerGroup {

    private CloudTemplateSystem cloudTemplateSystem;

    public CloudBukkitGroup(SimpleCloudServerGroup simpleCloudServerGroup) {
        super(simpleCloudServerGroup);
        cloudTemplateSystem = new CloudTemplateSystem(this);
    }

    public CloudTemplateSystem getCloudTemplateSystem() {
        return cloudTemplateSystem;
    }
}
