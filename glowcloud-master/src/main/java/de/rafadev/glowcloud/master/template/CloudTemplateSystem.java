package de.rafadev.glowcloud.master.template;

//------------------------------
//
// This class was developed by Rafael K.
// On 10.07.2020 at 14:22
// In the project GlowCloud
//
//------------------------------

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import de.rafadev.glowcloud.lib.document.Document;
import de.rafadev.glowcloud.master.group.classes.CloudServerGroup;
import de.rafadev.glowcloud.master.main.GlowCloud;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CloudTemplateSystem {

    private List<CloudTemplate> templates = new LinkedList<>();
    private String selectedTemplate = null;
    private boolean useRandom = false;

    public CloudTemplateSystem(CloudServerGroup cloudServerGroup) {

        File groupFile = GlowCloud.getGlowCloud().getGroupManager().getFile(cloudServerGroup);

        try {
            Document document = Document.load(groupFile);

            selectedTemplate = document.get("template").getAsJsonObject().get("defaultTemplate").getAsString();
            useRandom = document.get("template").getAsJsonObject().get("useRandomTemplate").getAsBoolean();
            JsonArray jsonArray = document.get("template").getAsJsonObject().get("templates").getAsJsonArray();
            for (JsonElement element : jsonArray) {
                templates.add(new CloudTemplate(element.getAsJsonObject().get("name").getAsString(), cloudServerGroup.toSimple(), element.getAsJsonObject().get("version").getAsString()));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<CloudTemplate> getTemplates() {
        return templates;
    }

    public CloudTemplate getSelectedTemplate() {
        return useRandom ? (templates.size() > 1 ? templates.get(new Random().nextInt(templates.size() - 1) + 1) : templates.get(0)) : templates.stream().filter(item -> item.getName().equals(selectedTemplate)).collect(Collectors.toList()).get(0);
    }
}
