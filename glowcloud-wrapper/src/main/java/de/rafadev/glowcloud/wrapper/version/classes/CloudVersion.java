package de.rafadev.glowcloud.wrapper.version.classes;

//------------------------------
//
// This class was developed by Rafael K.
// On 20.06.2020 at 00:45
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.downloader.URLDownloader;
import de.rafadev.glowcloud.lib.file.FileUtils;
import de.rafadev.glowcloud.lib.interfaces.IGlowCloudObject;
import de.rafadev.glowcloud.wrapper.main.GlowCloudWrapper;

import java.io.File;
import java.io.IOException;

public class CloudVersion implements IGlowCloudObject {

    private String url;
    private String name;
    private File savedFile;

    public CloudVersion(String url, String name) {

        this.url = url;
        this.name = name;

        if(!new File("database/versions/files/" + name).exists()) {
            new File("database/versions/files/" + name).mkdirs();
        }

        savedFile = new File("database/versions/files/" + name + "/version.jar");

        if(!savedFile.exists()) {
            try {
                savedFile.createNewFile();
                new URLDownloader(GlowCloudWrapper.getGlowCloud().getScheduler(), GlowCloudWrapper.getGlowCloud().getLogger(), url, savedFile);
            } catch (IOException e) {
                GlowCloudWrapper.getGlowCloud().getLogger().error("Can`t install the version with the name " + name + "§8!");
                e.printStackTrace();
            }
        }

    }

    public void prepare(File folder) {

        if(!folder.exists()) {
            folder.mkdirs();
        }

        if(!new File(folder.getAbsolutePath() + "/" + getSavedFile().getName()).exists()) {

            try {
                FileUtils.copyFileToDirectory(getSavedFile(), folder);
            } catch (IOException e) {
                GlowCloudWrapper.getGlowCloud().getLogger().handleException(e);
            }

        }

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getSavedFile() {
        return savedFile;
    }

    public void setSavedFile(File savedFile) {
        this.savedFile = savedFile;
    }
}
