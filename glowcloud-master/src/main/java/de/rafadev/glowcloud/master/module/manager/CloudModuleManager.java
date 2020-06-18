package de.rafadev.glowcloud.master.module.manager;

//------------------------------
//
// This class was developed by Rafael K.
// On 18.06.2020 at 15:02
// In the project GlowCloud
//
//------------------------------

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import de.rafadev.glowcloud.master.main.GlowCloud;
import de.rafadev.glowcloud.master.module.CloudModule;
import de.rafadev.glowcloud.master.module.classes.LoadedCloudModule;
import de.rafadev.glowcloud.master.module.description.CloudModuleDescription;
import de.rafadev.glowcloud.master.module.loader.CloudModuleLoader;

import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class CloudModuleManager {

    private List<LoadedCloudModule> modules = new LinkedList<>();
    private HashMap<String, CloudModuleDescription> toLoad = new HashMap<>();

    private File folder = new File("modules//");

    public void disableModules() {
        for (LoadedCloudModule module : modules) {
            GlowCloud.getGlowCloud().getLogger().info("Disabling the module §8\"§e" + module.getDescription().getName() + "§8\" §7by §e" + module.getDescription().getAuthor() + "§8...");
            module.getCloudModule().onDisable();
            modules.remove(module);
        }
    }

    public void enableModules() {

        GlowCloud.getGlowCloud().getLogger().info("§7Enabling all modules§8...");

        for (String name : toLoad.keySet()) {
            CloudModuleDescription description = toLoad.get(name);

            try {

                URL url = description.getFile().toURI().toURL();
                URL[] urls = new URL[]{url};

                CloudModuleLoader moduleLoader = new CloudModuleLoader(urls);

                CloudModule serviceModule = (CloudModule) moduleLoader.getClass(description.getMainclass()).newInstance();
                GlowCloud.getGlowCloud().getLogger().info("Try to enable module §8\"§e" + description.getName() + "§8\" §7...");
                serviceModule.onEnable();

                LoadedCloudModule loadedCloudModule = new LoadedCloudModule().setModule(serviceModule).setDescription(description);
                modules.add(loadedCloudModule);

            } catch (Exception ex) {
                GlowCloud.getGlowCloud().getLogger().error("Can`t load the main class of the module §8\"§e" + description.getName() + "§8\" | §c" + ex.getMessage());
                ex.printStackTrace();
            }

        }

        toLoad.clear();
        toLoad = null;

    }

    public void loadModules() {

        toLoad = new HashMap<>();

        GlowCloud.getGlowCloud().getLogger().info("§7Loading all modules§8...");

        if (!folder.exists()) {
            folder.mkdirs();
        }

        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.isFile() && file.getName().endsWith(".jar")) {
                try {

                    JarFile jarFile = new JarFile(file);

                    JarEntry pdf = jarFile.getJarEntry("module.json");

                    if (pdf == null) {
                        pdf = jarFile.getJarEntry("glowcloud.json");
                    }

                    if (pdf == null) {
                        GlowCloud.getGlowCloud().getLogger().error("Can´t found a module.json or glowcloud.json on the file \"" + file.getName() + "\"");
                        return;
                    }

                    JsonReader jsonReader = new JsonReader(new InputStreamReader(jarFile.getInputStream(pdf)));
                    jsonReader.setLenient(true);

                    JsonParser parser = new JsonParser();

                    JsonElement element = parser.parse(jsonReader);

                    if (element.isJsonObject()) {

                        JsonObject data = element.getAsJsonObject();

                        CloudModuleDescription description = new CloudModuleDescription(data.get("name").getAsString(), data.get("main").getAsString(), data.get("author").getAsString(), data.get("version").getAsString(), file);

                        GlowCloud.getGlowCloud().getLogger().info("Loading module §e" + description.getName() + " §7by §e" + description.getAuthor() + " §7...");

                        try {

                            URL url = description.getFile().toURI().toURL();
                            URL[] urls = new URL[]{url};

                            CloudModuleLoader moduleLoader = new CloudModuleLoader(urls);

                            CloudModule serviceModule = (CloudModule) moduleLoader.getClass(description.getMainclass()).newInstance();
                            GlowCloud.getGlowCloud().getLogger().info("Try to run the module §8\"§e" + description.getName() + "§8\" §7...");
                            serviceModule.onLoad();

                            toLoad.put(description.getName(), description);
                        } catch (Exception ex) {
                            GlowCloud.getGlowCloud().getLogger().error("Can`t load the main class of the module §8\"§e" + description.getName() + "§8\" | §c" + ex.getMessage());
                            ex.printStackTrace();
                        }

                    } else {
                        GlowCloud.getGlowCloud().getLogger().error("The format is wrong in the module.json file.");
                    }

                } catch (Exception ex) {
                    GlowCloud.getGlowCloud().getLogger().error("Can´t load modules | " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }

    }

}
