package de.rafadev.glowcloud.master.server.seletor;

//------------------------------
//
// This class was developed by Rafael K.
// On 06.07.2020 at 11:19
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.classes.selector.Selector;
import de.rafadev.glowcloud.master.wrapper.classes.CloudWrapper;

public class CloudWrapperSelector extends Selector {
    
    private CloudWrapper selected;

    public CloudWrapperSelector(CloudWrapper selected) {
        this.selected = selected;
    }

    public CloudWrapper getSelected() {
        return selected;
    }
    
}
