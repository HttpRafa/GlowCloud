package de.rafadev.glowcloud.modules.sign;

//------------------------------
//
// This class was developed by Rafael K.
// On 18.06.2020 at 15:40
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.master.main.GlowCloud;
import de.rafadev.glowcloud.master.module.CloudModule;

public class SignModule extends CloudModule {

    @Override
    public void onLoad() {
        GlowCloud.getGlowCloud().getLogger().info("SignModule was loaded");
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
