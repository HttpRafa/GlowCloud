package de.rafadev.glowcloud.master.key.classes;

//------------------------------
//
// This class was developed by Rafael K.
// On 26.06.2020 at 16:45
// In the project GlowCloud
//
//------------------------------

public class SystemKey {

    private String key;
    private int id;

    public SystemKey(String key, int id) {
        this.key = key;
        this.id = id;
    }

    public boolean is(String val) {
        return key.equals(val);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
