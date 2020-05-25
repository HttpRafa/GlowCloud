package de.rafadev.glowcloud.lib.document;

//------------------------------
//
// This class was developed by RafaDev
// On 19.05.2020 at 09:51
// In the project GlowCloud
//
//------------------------------

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import de.rafadev.glowcloud.lib.interfaces.IGlowCloudObject;

import java.io.File;
import java.io.StringReader;

public class Document implements IGlowCloudObject {

    private Gson gson = new GsonBuilder().create();
    private JsonObject data;

    public Document(JsonObject jsonObject) {
        this.data = jsonObject;
    }

    public Document(String src) {
        JsonReader jsonReader = new JsonReader(new StringReader(src));

        data = new JsonParser().parse(jsonReader).getAsJsonObject();
    }

    public static Document load(File file) {

        return new Document("");

    }

}
