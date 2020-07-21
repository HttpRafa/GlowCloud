package de.rafadev.glowcloud.wrapper.template;

//------------------------------
//
// This class was developed by Rafael K.
// On 11.07.2020 at 14:08
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.classes.group.SimpleCloudServerGroup;
import de.rafadev.glowcloud.lib.template.SimpleCloudTemplate;

import java.nio.file.Path;

public class CloudTemplate extends SimpleCloudTemplate {

    private Path path;

    public CloudTemplate(String name, SimpleCloudServerGroup cloudServerGroup, String version, Path path) {
        super(name, cloudServerGroup, version);
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public static CloudTemplate fromSimple(SimpleCloudTemplate cloudTemplate, Path folderPath) {

        return new CloudTemplate(cloudTemplate.getName(), cloudTemplate.getCloudServerGroup(), cloudTemplate.getVersion(), folderPath);

    }

}
