package de.rafadev.glowcloud.master.server.seletor;

//------------------------------
//
// This class was developed by Rafael K.
// On 06.07.2020 at 19:52
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.classes.selector.Selector;
import de.rafadev.glowcloud.lib.classes.server.CloudServer;

public class CloudServerSelector extends Selector {

    private CloudServer selected;

    public CloudServerSelector(CloudServer selected) {
        this.selected = selected;
    }

    public CloudServer getSelected() {
        return selected;
    }

}
