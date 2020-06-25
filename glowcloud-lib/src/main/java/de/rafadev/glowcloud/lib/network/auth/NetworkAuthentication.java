package de.rafadev.glowcloud.lib.network.auth;

//------------------------------
//
// This class was developed by Rafael K.
// On 24.06.2020 at 18:49
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.network.auth.types.AuthServiceType;

public class NetworkAuthentication {

    private String serviceID;
    private AuthServiceType authServiceType;
    private String authKey;

    public NetworkAuthentication(String serviceID, AuthServiceType authServiceType, String authKey) {
        this.serviceID = serviceID;
        this.authServiceType = authServiceType;
        this.authKey = authKey;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public AuthServiceType getAuthServiceType() {
        return authServiceType;
    }

    public void setAuthServiceType(AuthServiceType authServiceType) {
        this.authServiceType = authServiceType;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }
}
