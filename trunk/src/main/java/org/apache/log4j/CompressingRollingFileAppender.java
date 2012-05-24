
package org.apache.log4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author LiWei：<a href="mailto:liwei2672@gmail.com">liwei2672@gmail.com</a>
 * @version CompressingRollingFileAppender.java, v 0.1 May 24, 2012 3:37:19 PM
 */
public class CompressingRollingFileAppender extends DatedRollingFileAppender {

    public CompressingRollingFileAppender() {
        super();
    }

    void handleClosedFile() {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            FileOutputStream fos = new FileOutputStream(fileName + ".zip");
            ZipOutputStream zos = new ZipOutputStream(fos);

            ZipEntry zipEntry = new ZipEntry(getFilename(fileName));
            zos.putNextEntry(zipEntry);

            byte[] inbuf = new byte[8102];
            int n;

            while ((n = fis.read(inbuf)) != -1) {
                zos.write(inbuf, 0, n);
            }

            zos.close();
            fis.close();

            new File(fileName).delete();
        } catch (FileNotFoundException e) {
            errorHandler.error("file not found", e, -1);
        } catch (IOException e) {
            errorHandler.error("IOExceptrion", e, -2);
        }
    }

    static String getFilename(String filePath) {
        int pos = filePath.lastIndexOf('/');
        if (pos == -1) {
            pos = filePath.lastIndexOf(File.separatorChar);
        }

        if (pos == -1) {
            return filePath;
        } else {
            return filePath.substring(pos + 1);
        }

    }
}
