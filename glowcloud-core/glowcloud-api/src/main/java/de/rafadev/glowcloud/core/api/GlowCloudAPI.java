package de.rafadev.glowcloud.core.api;

//------------------------------
//
// This class was developed by Rafael K.
// On 23.07.2020 at 13:26
// In the project GlowCloud
//
//------------------------------

public class GlowCloudAPI {

    private static GlowCloudAPI api;

    public GlowCloudAPI() {
        api = this;
    }

    public static GlowCloudAPI getAPI() {
        return api;
    }

}
