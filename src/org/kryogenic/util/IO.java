package org.kryogenic.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Class for IO operations fileDownload method stolen from roseindia.net
 * @author kryo
 * @version 1.0
 */
public class IO {

    /**
     * Downloads a file to a directory
     *
     * @param fAddress       the web URL to download from
     * @param destinationDir the directory to ultimately put the file in
     * @author roseindia.net
     */
    public static void fileDownload(String fAddress, String destinationDir) {
        int slashIndex = fAddress.lastIndexOf('/');
        int periodIndex = fAddress.lastIndexOf('.');

        String fileName = fAddress.substring(slashIndex + 1);

        if (periodIndex >= 1 && slashIndex >= 0 && slashIndex < fAddress.length() - 1) {
            OutputStream outStream = null;
            URLConnection uCon;

            InputStream is = null;
            try {
                URL Url;
                byte[] buf;
                int ByteRead;
                Url = new URL(fAddress);
                outStream = new BufferedOutputStream(new FileOutputStream(destinationDir + System.getProperty("file.separator") + fileName));

                uCon = Url.openConnection();
                uCon.setRequestProperty("User-Agent", "Mozilla/5.0 Windows NT 6.2 WOW64 AppleWebKit/537.4 KHTML, like Gecko Chrome/22.0.1229.8 Safari/537.4");
                is = uCon.getInputStream();
                buf = new byte[1024];
                while ((ByteRead = is.read(buf)) != -1) {
                    outStream.write(buf, 0, ByteRead);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}