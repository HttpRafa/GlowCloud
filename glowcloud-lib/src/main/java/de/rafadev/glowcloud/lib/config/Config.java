package de.rafadev.glowcloud.lib.config;

//------------------------------
//
// This class was developed by Rafael K.
// On 08.06.2020 at 16:29
// In the project GlowCloud
//
//------------------------------

import com.google.gson.JsonParser;
import de.rafadev.glowcloud.lib.document.Document;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Config extends Document {

    private File file;

    public Config(File file) {
        this.file = file;

        try {
            if(!file.exists()) {
                file.createNewFile();

                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write("{}");
                fileWriter.flush();
                fileWriter.close();

            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));

            StringBuilder stringBuilder = new StringBuilder();
            String buffer;

            while ((buffer = bufferedReader.readLine()) != null) {
                stringBuilder.append(buffer);
            }

            setData(new JsonParser().parse(stringBuilder.toString()).getAsJsonObject());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
