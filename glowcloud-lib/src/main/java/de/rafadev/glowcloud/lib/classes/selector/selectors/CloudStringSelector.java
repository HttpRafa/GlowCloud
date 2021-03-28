package de.rafadev.glowcloud.lib.classes.selector.selectors;

//------------------------------
//
// This class was developed by Rafael K.
// On 06.07.2020 at 19:50
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.classes.selector.Selector;

public class CloudStringSelector extends Selector {

    private String selected;

    public CloudStringSelector(String selected) {
        this.selected = selected;
    }

    public String getSelected() {
        return selected;
    }

}
