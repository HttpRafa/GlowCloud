package de.rafadev.glowcloud.lib.document;

//------------------------------
//
// This class was developed by RafaDev
// On 19.05.2020 at 09:51
// In the project GlowCloud
//
//------------------------------

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import de.rafadev.glowcloud.lib.interfaces.IGlowCloudObject;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Document implements IGlowCloudObject {

    private Gson gson = new GsonBuilder().create();
    private JsonParser jsonParser = new JsonParser();

    private JsonObject data;

    public Document() {
        data = new JsonObject();
    }

    public Document(JsonObject jsonObject) {
        this.data = jsonObject;
    }

    public Document(String src) {
        JsonReader jsonReader = new JsonReader(new StringReader(src));
        data = jsonParser.parse(jsonReader).getAsJsonObject();
    }

    public static Document load(File file) throws IOException {
        JsonReader jsonReader = new JsonReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        return new Document(new JsonParser().parse(jsonReader).getAsJsonObject());
    }

    public Document append(String key, String value) {
        data.addProperty(key, value);
        return this;
    }

    public Document append(String key, Number value) {
        data.addProperty(key, value);
        return this;
    }

    public Document append(String key, boolean value) {
        data.addProperty(key, value);
        return this;
    }

    public Document append(String key, JsonElement value) {
        data.add(key, value);
        return this;
    }

    public Document append(String key, Document value) {
        data.add(key, value.data);
        return this;
    }

    public String getAsString(String key) {
        return data.get(key).getAsString();
    }

    public boolean getAsBoolean(String key) {
        return data.get(key).getAsBoolean();
    }

    public Number getAsNumber(String key) {
        return data.get(key).getAsNumber();
    }

    public Document getAsDocument(String key) {
        return new Document(data.get(key).getAsJsonObject());
    }

    public JsonElement get(String key) {
        return data.get(key);
    }

    public void delete(String key) {
        data.remove(key);
    }

    public boolean has(String key) {
        return data.has(key);
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

    public void save(File file) {
        try(Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            if(!file.exists()) {
                file.createNewFile();
            }

            writer.write((new GsonBuilder().disableHtmlEscaping().serializeNulls().setPrettyPrinting().create()).toJson(data));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonObject getData() {
        return data;
    }

    @Override
    public String toString() {
        return gson.toJson(data);
    }
}
