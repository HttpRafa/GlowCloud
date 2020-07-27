package de.rafadev.glowcloud.lib.file;

//------------------------------
//
// This class was developed by Rafael K.
// On 03.06.2020 at 18:07
// In the project GlowCloud
//
//------------------------------

import java.io.*;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;

public class CloudReader {

    private File file;

    public CloudReader(File file) {
        this.file = file;
    }

    public String read() throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));

        StringBuilder stringBuilder = new StringBuilder();

        String buffer;
        while((buffer = bufferedReader.readLine()) != null) {
            stringBuilder.append(buffer);
        }

        return stringBuilder.toString();

    }
}
