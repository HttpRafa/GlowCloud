package de.rafadev.glowcloud.master.module.description;

//------------------------------
//
// This class was developed by Rafael K.
// On 18.06.2020 at 15:00
// In the project GlowCloud
//
//------------------------------

import java.io.File;

public class CloudModuleDescription {

    private String name;
    private String mainclass;
    private String author;
    private String version;
    private File file;

    public CloudModuleDescription(String name, String mainclass, String author, String version, File file) {
        this.name = name;
        this.mainclass = mainclass;
        this.author = author;
        this.version = version;
        this.file = file;
    }

    public String getMainclass() {
        return mainclass;
    }

    public String getAuthor() {
        return author;
    }

    public String getVersion() {
        return version;
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

}
