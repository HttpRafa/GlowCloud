package de.rafadev.glowcloud.lib;

//------------------------------
//
// This class was developed by Rafael K.
// On 24.07.2020 at 11:37
// In the project GlowCloud
//
//------------------------------

public class CloudValue<T> {

    private T val;

    public CloudValue(T val) {
        this.val = val;
    }

    public T get() {
        return val;
    }

    public void override(T val) {
        this.val = val;
    }
}
