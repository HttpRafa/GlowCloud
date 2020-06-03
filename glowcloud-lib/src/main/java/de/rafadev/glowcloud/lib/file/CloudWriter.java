package de.rafadev.glowcloud.lib.file;

//------------------------------
//
// This class was developed by Rafael K.
// On 03.06.2020 at 09:59
// In the project GlowCloud
//
//------------------------------

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CloudWriter {

    private File file;

    public CloudWriter(File file) {
        this.file = file;
    }

    public CloudWriter write(String str) throws IOException {
        Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
        writer.write(str);
        writer.flush();
        writer.close();
        return this;
    }
}
