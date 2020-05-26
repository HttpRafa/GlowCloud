package de.rafadev.glowcloud.master.group;

//------------------------------
//
// This class was developed by RafaDev
// On 22.05.2020 at 12:23
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.master.group.classes.CloudServerGroup;
import de.rafadev.glowcloud.master.group.setup.GroupSetupResult;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class GroupManager {

    private File folder = new File("groups/");

    private List<CloudServerGroup> groups = new LinkedList<>();

    public void createGroup(GroupSetupResult result) {
        if(!existGroup(result.getName())) {
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File file = new File(folder.getPath() + "/" + result.getName() + ".json");
        }
    }

    public boolean existGroup(String name) {
        return search(name).size() > 0;
    }

    public CloudServerGroup getGroup(String name) {
        return search(name).get(0);
    }

    public List<CloudServerGroup> search(String name) {
        return groups.stream().filter(group -> group.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
    }


}
