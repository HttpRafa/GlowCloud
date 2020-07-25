package de.rafadev.glowcloud.core.bridge.authentication;

//------------------------------
//
// This class was developed by Rafael K.
// On 23.07.2020 at 13:58
// In the project GlowCloud
//
//------------------------------

public class BridgeAuthentication {

    private String serviceID;
    private String authKey;

    public BridgeAuthentication(String serviceID, String authKey) {
        this.serviceID = serviceID;
        this.authKey = authKey;
    }

    public String getServiceID() {
        return serviceID;
    }

    public String getAuthKey() {
        return authKey;
    }

}
