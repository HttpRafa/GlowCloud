package de.rafadev.glowcloud.master.module.classes;

//------------------------------
//
// This class was developed by Rafael K.
// On 18.06.2020 at 15:02
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.master.module.CloudModule;
import de.rafadev.glowcloud.master.module.description.CloudModuleDescription;

public class LoadedCloudModule {

    private CloudModule cloudModule;
    private CloudModuleDescription description;

    public LoadedCloudModule(CloudModule cloudModule, CloudModuleDescription description) {
        this.cloudModule = cloudModule;
        this.description = description;
    }

    public LoadedCloudModule() {}

    public LoadedCloudModule setModule(CloudModule cloudModule) {
        this.cloudModule = cloudModule;
        return this;
    }

    public LoadedCloudModule setDescription(CloudModuleDescription description) {
        this.description = description;
        return this;
    }

    public CloudModule getCloudModule() {
        return cloudModule;
    }

    public CloudModuleDescription getDescription() {
        return description;
    }
}
