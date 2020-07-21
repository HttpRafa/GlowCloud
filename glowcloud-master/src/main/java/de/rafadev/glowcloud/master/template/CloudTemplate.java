package de.rafadev.glowcloud.master.template;

//------------------------------
//
// This class was developed by Rafael K.
// On 10.07.2020 at 14:19
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.classes.group.SimpleCloudServerGroup;
import de.rafadev.glowcloud.lib.template.SimpleCloudTemplate;

public class CloudTemplate extends SimpleCloudTemplate {

    public CloudTemplate(String name, SimpleCloudServerGroup cloudServerGroup, String version) {
        super(name, cloudServerGroup, version);
    }
}
