package de.rafadev.glowcloud.lib.downloader;

//------------------------------
//
// This class was developed by Rafael K.
// On 20.06.2020 at 00:49
// In the project GlowCloud
//
//------------------------------

import de.rafadev.glowcloud.lib.logging.CloudLogger;
import de.rafadev.glowcloud.lib.logging.ProcessBar;
import de.rafadev.glowcloud.lib.scheduler.GlowScheduler;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class URLDownloader {

    public URLDownloader(GlowScheduler glowScheduler, CloudLogger logger, String urlstr, File output) throws IOException {
        logger.info("Starting the download§8..");
        final URL url = new URL(urlstr);
        final URLConnection conn = url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        conn.connect();
        final InputStream is = new BufferedInputStream(conn.getInputStream());

        long dateKB = conn.getHeaderFieldLong("Content-Length", is.available());
        long downloadedBytes = 0;
        final int[] downloadBytesOn1Sek = {0};
        final int[] downloadBytesPer1Sek = {0};

        final OutputStream os =
                new BufferedOutputStream(new FileOutputStream(output));

        int taskID = glowScheduler.runRepeatingTask(new Runnable() {
            @Override
            public void run() {
                downloadBytesPer1Sek[0] = downloadBytesOn1Sek[0];
                downloadBytesOn1Sek[0] = 0;
            }
        }, 0, 1000);

        byte[] chunk = new byte[1024];
        int chunkSize;
        while ((chunkSize = is.read(chunk)) != -1) {
            downloadedBytes += 1024;
            downloadBytesOn1Sek[0]++;
            os.write(chunk, 0, chunkSize);
            new ProcessBar(logger, downloadBytesPer1Sek[0], dateKB, downloadedBytes);
        }
        os.flush(); // Necessary for Java < 6
        os.close();
        is.close();

        glowScheduler.existTask(taskID);

        logger.nextLine();
        logger.info("The download was §asuccessfully§8.");
    }

    public URLDownloader(String urlstr, File output) throws IOException {
        final URL url = new URL(urlstr);
        final URLConnection conn = url.openConnection();
        final InputStream is = new BufferedInputStream(conn.getInputStream());

        final OutputStream os =
                new BufferedOutputStream(new FileOutputStream(output));
        byte[] chunk = new byte[1024];
        int chunkSize;
        while ((chunkSize = is.read(chunk)) != -1) {
            os.write(chunk, 0, chunkSize);
        }
        os.flush(); // Necessary for Java < 6
        os.close();
        is.close();
    }

}
