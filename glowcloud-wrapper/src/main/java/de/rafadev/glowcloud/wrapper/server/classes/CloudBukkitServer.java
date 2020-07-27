package de.rafadev.glowcloud.wrapper.server.classes;

//------------------------------
//
// This class was developed by Rafael K.
// On 29.06.2020 at 11:47
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.classes.group.SimpleCloudServerGroup;
import de.rafadev.glowcloud.lib.classes.server.CloudServer;
import de.rafadev.glowcloud.lib.config.Config;
import de.rafadev.glowcloud.lib.enums.GroupMode;
import de.rafadev.glowcloud.lib.enums.ServerType;
import de.rafadev.glowcloud.lib.file.FileUtils;
import de.rafadev.glowcloud.lib.template.SimpleCloudTemplate;
import de.rafadev.glowcloud.wrapper.main.GlowCloudWrapper;
import de.rafadev.glowcloud.wrapper.template.CloudTemplate;
import de.rafadev.glowcloud.wrapper.version.classes.CloudVersion;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.UUID;

public class CloudBukkitServer extends CloudServer {

    private Process process;

    private File folder = null;
    private File runFolder = null;

    public CloudBukkitServer(String serviceID, UUID uuid, SimpleCloudServerGroup cloudServerGroup, SimpleCloudTemplate cloudTemplate) {
        super(serviceID, uuid, cloudServerGroup, cloudTemplate);
    }

    public void kill() {

        if(process != null) {
            process.destroy();
            process = null;
            runFolder = null;
            folder = null;
        }

    }

    public boolean prepare() {

        GlowCloudWrapper.getGlowCloud().getLogger().info("Starting the preparation for the server " + getServiceID() + "...");

        if(getCloudServerGroup().getGroupMode() == GroupMode.DYNAMIC) {
            folder = new File("database/templates/" + getCloudServerGroup().getName() + "/" + getTemplate().getName() + "/");
            runFolder = new File("temp/servers/" + getServiceID() + "#" + getUUID().toString() + "/");
        } else if(getCloudServerGroup().getGroupMode() == GroupMode.STATIC) {
            folder = new File("database/servers/" + getServiceID() + "/");
            runFolder = folder;
        }
        if(!folder.exists()) {
            folder.mkdirs();
        }

        /*
        Server files
         */
        File bukkitFile = new File(folder.getPath() + "/bukkit.yml");
        File propertiesFile = new File(folder.getPath() + "/server.properties");
        File spigotFile = new File(folder.getPath() + "/spigot.yml");

        CloudVersion cloudVersion = GlowCloudWrapper.getGlowCloud().getVersionManager().get(getTemplate().getVersion());
        cloudVersion.prepare(folder);

        try {

            if (!bukkitFile.exists()) {
                bukkitFile.createNewFile();
                FileUtils.insertData("files/bukkit.yml", bukkitFile.getPath());
            }
            if (!propertiesFile.exists()) {
                bukkitFile.createNewFile();
                FileUtils.insertData("files/server.properties", propertiesFile.getPath());
            }
            if (!spigotFile.exists()) {
                bukkitFile.createNewFile();
                FileUtils.insertData("files/spigot.yml", spigotFile.getPath());
            }

        } catch (Exception exception) {
            GlowCloudWrapper.getGlowCloud().getLogger().handleException(exception);
        }

        if(getCloudServerGroup().getGroupMode() == GroupMode.DYNAMIC) {
            /*
                Copy the template folder in the temp folder
             */

            if(!runFolder.exists()) {
                runFolder.mkdirs();
            }

            try {
                FileUtils.copyFilesInDirectory(folder, runFolder);
            } catch (IOException e) {
                GlowCloudWrapper.getGlowCloud().getLogger().handleException(e);
            }
        }

        /*
        Search for a port
         */

        setPort(GlowCloudWrapper.getGlowCloud().getNetworkManager().findFreePort(5000, 9200));

        /*
        Edit the server.properties file...
         */

        Properties properties = new Properties();
        try (InputStreamReader inputStreamReader = new InputStreamReader(Files.newInputStream(Paths.get(runFolder.getPath() + "/server.properties")))) {
            properties.load(inputStreamReader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        properties.setProperty("server-ip", "127.0.0.1");
        properties.setProperty("server-port", getPort() + "");
        properties.setProperty("online-mode", "false");
        properties.setProperty("server-name", getServiceID());

        try (OutputStream outputStream = Files.newOutputStream(Paths.get(runFolder.getPath() + "/server.properties"))) {
            properties.store(outputStream, "GlowCloud-Wrapper EDIT");
        } catch (IOException e) {
            e.printStackTrace();
        }

        File cloudSettingsFolder = new File(runFolder.getPath() + "/.cloud/");
        if(!cloudSettingsFolder.exists()) {
            cloudSettingsFolder.mkdirs();

            Config config = new Config(new File(cloudSettingsFolder.getPath() + "/service.json"));
            config.append("serviceID", getServiceID());
            config.append("servicePort", getPort());
            config.append("authKey", GlowCloudWrapper.getGlowCloud().getKeyManager().getConnectionKey());
            config.append("masterHost", GlowCloudWrapper.getGlowCloud().getFileManager().getConfig().getAsString("masterAddress"));
            config.append("masterPort", GlowCloudWrapper.getGlowCloud().getFileManager().getConfig().getAsNumber("masterPort").intValue());
            config.save(config.getFile());
        }

        return true;
    }

    public void startProcessing() {

        GlowCloudWrapper.getGlowCloud().getLogger().info("Starting the §eCloudServer §7with the serviceID §e" + getServiceID() + "§8@§e" + getPort() + " §7...");

        StringBuilder commandBuilder = new StringBuilder();
        commandBuilder.append("java ");
        commandBuilder.append("-XX:+UseG1GC -XX:MaxGCPauseMillis=50 -XX:MaxPermSize=256M -XX:-UseAdaptiveSizePolicy -XX:CompileThreshold=100 -Dcom.mojang.eula.agree=true -Dio.netty.recycler.maxCapacity=0 -Dio.netty.recycler.maxCapacity.default=0 -Djline.terminal=jline.UnsupportedTerminal -Xms" + getCloudServerGroup().getMemory() + "M -Xmx" + getCloudServerGroup().getDynamicMemory() + "M -jar ");
        commandBuilder.append("version.jar nogui");

        try {
            process = Runtime.getRuntime().exec(commandBuilder.toString().split(" "), null, new File(runFolder.getPath() + "/"));

            GlowCloudWrapper.getGlowCloud().getLogger().info("The §eCloudServer §7with the serviceID §e" + getServiceID() + "§8@§e" + getPort() + " §7is started§8.");
        } catch (IOException IOException) {
            GlowCloudWrapper.getGlowCloud().getLogger().handleException(IOException);
        }
    }

    public boolean stopService() {
        GlowCloudWrapper.getGlowCloud().getLogger().info("The §eCloudServer §7with the serviceID §e" + getServiceID() + "§8@§e" + getPort() + " §7is stopping§8..");
        kill();

        FileUtils.deleteDirectory(runFolder);
        GlowCloudWrapper.getGlowCloud().getLogger().info("The §eCloudServer §7with the serviceID §e" + getServiceID() + " §7was §cdeleted§8.");

        return true;
    }

}
