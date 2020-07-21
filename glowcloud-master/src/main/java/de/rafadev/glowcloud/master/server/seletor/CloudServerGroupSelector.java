package de.rafadev.glowcloud.master.server.seletor;

//------------------------------
//
// This class was developed by Rafael K.
// On 06.07.2020 at 11:22
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.classes.selector.Selector;
import de.rafadev.glowcloud.master.group.classes.CloudServerGroup;

public class CloudServerGroupSelector extends Selector {

    private CloudServerGroup selected;

    public CloudServerGroupSelector(CloudServerGroup selected) {
        this.selected = selected;
    }

    public CloudServerGroup getSelected() {
        return selected;
    }

}
