package de.rafadev.glowcloud.master.group;

//------------------------------
//
// This class was developed by RafaDev
// On 22.05.2020 at 12:23
// In the project GlowCloud
//
//------------------------------

import com.google.gson.*;
import de.rafadev.glowcloud.lib.classes.group.SimpleCloudServerGroup;
import de.rafadev.glowcloud.lib.enums.GroupMode;
import de.rafadev.glowcloud.lib.enums.ServerType;
import de.rafadev.glowcloud.lib.file.CloudReader;
import de.rafadev.glowcloud.lib.file.CloudWriter;
import de.rafadev.glowcloud.master.group.bukkit.CloudBukkitGroup;
import de.rafadev.glowcloud.master.group.classes.CloudServerGroup;
import de.rafadev.glowcloud.master.group.proxy.CloudProxyGroup;
import de.rafadev.glowcloud.master.group.setup.GroupSetup;
import de.rafadev.glowcloud.master.group.setup.GroupSetupResult;
import de.rafadev.glowcloud.master.main.GlowCloud;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class GroupManager {

    private File folder = new File("groups/");

    private GroupSetup groupSetup;

    private List<CloudServerGroup> groups = new LinkedList<>();

    public void reload() {

    }

    public void loadGroups() {

        long startTime = System.currentTimeMillis();

        int loadedGroups = 0;

        GlowCloud.getGlowCloud().getLogger().info("Loading all ServerGroups...");

        File[] files = folder.listFiles();
        for (File file : files) {

            if(file.getName().endsWith(".json")) {

                CloudReader cloudReader = new CloudReader(file);

                try {
                    JsonObject jsonObject = new JsonParser().parse(cloudReader.read()).getAsJsonObject();
                    JsonObject groupObject = jsonObject.get("group").getAsJsonObject();
                    JsonObject serverObject = jsonObject.get("server").getAsJsonObject();
                    JsonObject settingsObject = jsonObject.get("settings").getAsJsonObject();

                    SimpleCloudServerGroup cloudServerGroup = new SimpleCloudServerGroup();
                    cloudServerGroup.setName(groupObject.get("name").getAsString());
                    cloudServerGroup.setFallback(!serverObject.get("serverType").getAsString().equalsIgnoreCase("BUNGEECORD") && settingsObject.get("network").getAsJsonObject().get("fallback").getAsBoolean());
                    cloudServerGroup.setMaintenance(jsonObject.get("state").getAsJsonObject().get("maintenance").getAsBoolean());
                    cloudServerGroup.setServerType(ServerType.valueOf(serverObject.get("serverType").getAsString()));
                    cloudServerGroup.setGroupMode(GroupMode.valueOf(jsonObject.get("template").getAsJsonObject().get("groupMode").getAsString()));
                    cloudServerGroup.setMemory(serverObject.get("memory").getAsInt());
                    cloudServerGroup.setDynamicMemory(serverObject.get("dynamicMemory").getAsInt());
                    cloudServerGroup.setMinServerCount(jsonObject.get("cloud").getAsJsonObject().get("minServerCount").getAsInt());
                    cloudServerGroup.setMaxServerCount(jsonObject.get("cloud").getAsJsonObject().get("maxServerCount").getAsInt());
                    cloudServerGroup.setNewServerPercent(jsonObject.get("cloud").getAsJsonObject().get("newServerPercent").getAsInt());

                    for (JsonElement jsonElement : groupObject.get("wrappers").getAsJsonArray()) {
                        cloudServerGroup.setWrapperID(jsonElement.getAsString());
                    }

                    if (cloudServerGroup.getServerType() == ServerType.BUKKIT) {
                        CloudBukkitGroup cloudBukkitGroup = new CloudBukkitGroup(cloudServerGroup);
                        inject(cloudBukkitGroup);
                        loadedGroups++;
                    } else if (cloudServerGroup.getServerType() == ServerType.BUNGEECORD) {
                        CloudProxyGroup cloudProxyGroup = new CloudProxyGroup(cloudServerGroup);
                        inject(cloudProxyGroup);
                        loadedGroups++;
                    }

                } catch (Exception e) {
                    GlowCloud.getGlowCloud().getLogger().error("Can`t load group \"" + file.getName().replaceAll(".json", "") + "\"!");
                    GlowCloud.getGlowCloud().getLogger().handleException(e);
                }
            } else {
                GlowCloud.getGlowCloud().getLogger().error("Invalid format for " + file.getName() + " file§8!");
            }
        }

        long time = System.currentTimeMillis() - startTime;
        if(time > 2000) {
            GlowCloud.getGlowCloud().getLogger().error("The loading of the groups took more than 2 second exactly " + time + " ms");
        } else {
            GlowCloud.getGlowCloud().getLogger().info(loadedGroups + " groups were loaded in " + time + " ms");
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

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
            simpleCloudServerGroup.setFallback(result.isFallback());
            simpleCloudServerGroup.setWrapperID(result.getWrapperID());
            simpleCloudServerGroup.setName(result.getName());


            File file = new File(folder.getPath() + "/" + result.getName() + ".json");
            try {
                file.createNewFile();
                Gson gson = new GsonBuilder().disableHtmlEscaping().serializeNulls().setPrettyPrinting().create();

                JsonObject mainObject = new JsonObject();

                /*
                Group information
                 */

                JsonObject groupObject = new JsonObject();
                groupObject.addProperty("name", simpleCloudServerGroup.getName());
                JsonArray wrapperList = new JsonArray();
                wrapperList.add(simpleCloudServerGroup.getWrapperID());
                groupObject.add("wrappers", wrapperList);
                mainObject.add("group", groupObject);

                /*
                Server Settings
                 */

                JsonObject serverObject = new JsonObject();
                serverObject.addProperty("memory", simpleCloudServerGroup.getMemory());
                serverObject.addProperty("dynamicMemory", simpleCloudServerGroup.getDynamicMemory());
                serverObject.addProperty("serverType", simpleCloudServerGroup.getServerType().toString());
                mainObject.add("server", serverObject);

                /*
                Cloud Settings
                 */

                JsonObject autoObject = new JsonObject();
                autoObject.addProperty("maxServerCount", simpleCloudServerGroup.getMaxServerCount());
                autoObject.addProperty("minServerCount", simpleCloudServerGroup.getMinServerCount());
                autoObject.addProperty("newServerPercent", simpleCloudServerGroup.getNewServerPercent());
                mainObject.add("cloud", autoObject);

                /*
                State
                 */

                JsonObject stateObject = new JsonObject();
                stateObject.addProperty("maintenance", simpleCloudServerGroup.isMaintenance());
                //stateObject.addProperty("fallback", cloudServerGroup.isFallback());
                mainObject.add("state", stateObject);

                /*
                Template
                 */

                JsonObject templateObject = new JsonObject();
                templateObject.addProperty("groupMode", simpleCloudServerGroup.getGroupMode().toString());
                templateObject.addProperty("defaultTemplate", "default");
                templateObject.addProperty("useRandomTemplate", false);

                JsonArray templateListArray = new JsonArray();

                JsonObject defaultTemplateObject = new JsonObject();
                defaultTemplateObject.addProperty("name", "default");
                defaultTemplateObject.addProperty("version", simpleCloudServerGroup.getServerType().toString().toLowerCase());
                templateListArray.add(defaultTemplateObject);
                templateObject.add("templates", templateListArray);
                mainObject.add("template", templateObject);

                JsonObject settingsObject = new JsonObject();

                /*
                Settings
                 */

                if(simpleCloudServerGroup.getServerType() == ServerType.BUKKIT) {
                    JsonObject proxySettingsObject = new JsonObject();
                    proxySettingsObject.addProperty("fallback", simpleCloudServerGroup.isFallback());
                    proxySettingsObject.addProperty("fallback-permission", "");
                    settingsObject.add("network", proxySettingsObject);
                } else if(simpleCloudServerGroup.getServerType() == ServerType.BUNGEECORD) {

                    JsonObject proxyObject = new JsonObject();
                    proxyObject.addProperty("maxPlayers", 250);

                    settingsObject.add("proxy", proxyObject);

                    JsonObject motdObject = new JsonObject();
                    motdObject.addProperty("enabled", true);

                    JsonObject maintenanceObject = new JsonObject();
                    maintenanceObject.addProperty("protocol", "§8» §cMaintenance");
                    maintenanceObject.addProperty("firstLine", " §eGlowCloud §8■ §7your Cloud§8, §7my §ework §8● §8[§e%version%§8]");
                    maintenanceObject.addProperty("secondLine", "      §8▎ §4Maintenance §8● §cI am in maintenance§8. §8▎");
                    motdObject.add("maintenance", maintenanceObject);

                    JsonObject normalObject = new JsonObject();
                    normalObject.addProperty("firstLine", " §eGlowCloud §8■ §7your Cloud§8, §7my §ework §8● §8[§e%version%§8]");
                    normalObject.addProperty("secondLine", "      §8▎ §eOnline §8● §7Please configure me.§8. §8▎");
                    motdObject.add("normal", normalObject);

                    settingsObject.add("motd", motdObject);

                    JsonObject tabListObject = new JsonObject();
                    tabListObject.addProperty("enabled", true);
                    tabListObject.addProperty("header", " \n§8▎ §6● §8▎ §eGlowCloud §8■ §e%online_players% §8/ §e%max_players% §8▎ §6● §8▎\n §8► §7Current server §8● §e%server% §8◄ \n ");
                    tabListObject.addProperty("footer", " \n §7Developer §8● §eRafaDev §8▎ §7Info §8● §eWith <3 \n ");

                    settingsObject.add("tabList", tabListObject);

                    JsonObject playerListObject = new JsonObject();
                    playerListObject.addProperty("enabled", true);
                    JsonArray jsonArray = new JsonArray();
                    jsonArray.add(" ");
                    jsonArray.add("§e§lGlowCloud");
                    jsonArray.add("§7Your Minecraft cloud.");
                    jsonArray.add("§7developed by RafaDev with §c<3");
                    jsonArray.add(" ");
                    playerListObject.add("list", jsonArray);

                    settingsObject.add("playerList", playerListObject);

                }

                mainObject.add("settings", settingsObject);

                new CloudWriter(file).write(gson.toJson(mainObject));
            } catch (IOException e) {
                e.printStackTrace();
            }

            CloudServerGroup cloudServerGroup = null;
            
            if(result.getServerType() == ServerType.BUKKIT) {
                cloudServerGroup = new CloudBukkitGroup(simpleCloudServerGroup);
            } else if(result.getServerType() == ServerType.BUNGEECORD) {
                cloudServerGroup = new CloudProxyGroup(simpleCloudServerGroup);
            }

            inject(cloudServerGroup);

            GlowCloud.getGlowCloud().getServerManager().checkGroup(cloudServerGroup);

        }

    }

    public void inject(CloudServerGroup serverGroup) {

        groups.add(serverGroup);
        GlowCloud.getGlowCloud().getLogger().info("Loading ServerGroup §e" + serverGroup.getName() + "§8@§6" + serverGroup.getWrapperID() + " §7with §e" + serverGroup.getDynamicMemory() + " §7MB");

    }

    public File getFile(CloudServerGroup cloudServerGroup) {

        return new File(folder.getPath() + "/" + cloudServerGroup.getName() + ".json");

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

    public CloudServerGroup get(String name) {

        return search(name).size() > 0 ? search(name).get(0) : null;

    }

    public List<CloudServerGroup> getGroups() {
        return groups;
    }

    public void setGroupSetup(GroupSetup groupSetup) {
        this.groupSetup = groupSetup;
    }

    public GroupSetup getGroupSetup() {
        return groupSetup;
    }
}
