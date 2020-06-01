package de.rafadev.glowcloud.master.group;

//------------------------------
//
// This class was developed by RafaDev
// On 22.05.2020 at 12:23
// In the project GlowCloud
//
//------------------------------

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.rafadev.glowcloud.lib.classes.group.SimpleCloudServerGroup;
import de.rafadev.glowcloud.lib.enums.GroupMode;
import de.rafadev.glowcloud.lib.enums.ServerType;
import de.rafadev.glowcloud.master.group.bukkit.CloudBukkitGroup;
import de.rafadev.glowcloud.master.group.classes.CloudServerGroup;
import de.rafadev.glowcloud.master.group.proxy.CloudProxyGroup;
import de.rafadev.glowcloud.master.group.setup.GroupSetup;
import de.rafadev.glowcloud.master.group.setup.GroupSetupResult;
import de.rafadev.glowcloud.master.main.GlowCloud;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class GroupManager {

    private File folder = new File("groups/");

    private GroupSetup groupSetup;

    private List<CloudServerGroup> groups = new LinkedList<>();

    public void createGroup(GroupSetupResult result) {
        if(!existGroup(result.getName())) {
            if (!folder.exists()) {
                folder.mkdirs();
            }

            SimpleCloudServerGroup simpleCloudServerGroup = new SimpleCloudServerGroup();
            simpleCloudServerGroup.setNewServerPercent(100);
            simpleCloudServerGroup.setMaxServerCount(-1);
            simpleCloudServerGroup.setMinServerCount(result.getMinServerCount());
            simpleCloudServerGroup.setDynamicMemory(result.getMemory());
            simpleCloudServerGroup.setMemory(result.getMemory());
            simpleCloudServerGroup.setGroupMode(result.getGroupMode());
            simpleCloudServerGroup.setServerType(result.getServerType());
            simpleCloudServerGroup.setMaintenance(false);
            simpleCloudServerGroup.setFallback(false);
            simpleCloudServerGroup.setWrapperID(result.getWrapperID());
            simpleCloudServerGroup.setName(result.getName());

            CloudServerGroup cloudServerGroup = null;
            
            if(result.getServerType() == ServerType.BUKKIT) {
                cloudServerGroup = new CloudBukkitGroup(simpleCloudServerGroup);
            } else if(result.getServerType() == ServerType.BUNGEECORD) {
                cloudServerGroup = new CloudProxyGroup(simpleCloudServerGroup);
            }

            inject(cloudServerGroup);

            File file = new File(folder.getPath() + "/" + result.getName() + ".json");
            try {
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file);
                Gson gson = new GsonBuilder().disableHtmlEscaping().serializeNulls().setPrettyPrinting().create();

                JsonObject mainObject = new JsonObject();

                JsonObject groupObject = new JsonObject();
                groupObject.addProperty("name", cloudServerGroup.getName());
                JsonArray wrapperList = new JsonArray();
                wrapperList.add(cloudServerGroup.getWrapperID());
                groupObject.add("wrappers", wrapperList);
                mainObject.add("group", groupObject);

                JsonObject serverObject = new JsonObject();
                serverObject.addProperty("memory", cloudServerGroup.getMemory());
                serverObject.addProperty("dynamicMemory", cloudServerGroup.getDynamicMemory());
                serverObject.addProperty("serverType", cloudServerGroup.getServerType().toString());
                mainObject.add("server", serverObject);

                JsonObject autoObject = new JsonObject();
                autoObject.addProperty("maxServerCount", cloudServerGroup.getMaxServerCount());
                autoObject.addProperty("minServerCount", cloudServerGroup.getMinServerCount());
                autoObject.addProperty("newServerPercent", cloudServerGroup.getNewServerPercent());
                mainObject.add("cloud", autoObject);

                JsonObject stateObject = new JsonObject();
                stateObject.addProperty("maintenance", cloudServerGroup.isMaintenance());
                stateObject.addProperty("fallback", cloudServerGroup.isFallback());
                mainObject.add("state", stateObject);

                JsonObject templateObject = new JsonObject();
                templateObject.addProperty("groupMode", cloudServerGroup.getGroupMode().toString());
                templateObject.addProperty("defaultTemplate", "default");

                JsonArray templateListArray = new JsonArray();

                JsonObject defaultTemplateObject = new JsonObject();
                defaultTemplateObject.addProperty("name", "default");
                defaultTemplateObject.addProperty("version", "main");
                templateListArray.add(defaultTemplateObject);
                templateObject.add("templates", templateListArray);
                mainObject.add("template", templateObject);

                JsonObject settingsObject = new JsonObject();
                mainObject.add("settings", settingsObject);

                fileWriter.write(gson.toJson(mainObject));
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void inject(CloudServerGroup serverGroup) {

        groups.add(serverGroup);
        GlowCloud.getGlowCloud().getLogger().info("Loading ServerGroup \"" + serverGroup.getName() + "\" with " + serverGroup.getDynamicMemory() + "MB");

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

    public void setGroupSetup(GroupSetup groupSetup) {
        this.groupSetup = groupSetup;
    }

    public GroupSetup getGroupSetup() {
        return groupSetup;
    }
}
