/* ******************************************************************************
* Project    : Tools
* File       : DatedRollingFileAppender.java
* Author     : Patrick Wyss
* Creation   : 09.08.2006
* 
* Copyright (c) 2006 Patrick Wyss. All rights reserved.
******************************************************************************
* Version History:
*
* V 1.0    : Inital Version
*
*************************************************************************** */

package org.apache.log4j;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.helpers.LogLog;

public class DatedRollingFileAppender extends DailyRollingFileAppender {

    String baseFileName;
    String fileNameSuffix;

    public DatedRollingFileAppender() {
        super();
    }

    public void setAppend(boolean flag) {
        if (!flag) {
            LogLog.warn("append option can not be set to false for appender [" + name + "]");
        }
    }

    String calcFileName(String filename) {
        if (filename == null) {
            LogLog.error("FileName options are not set for appender [" + name + "].");
            return null;
        }

        int datePos = filename.indexOf("%d");
        if (datePos == -1) {
            // fileName has already been modified
            return filename;
        }

        baseFileName = filename.substring(0, datePos);
        fileNameSuffix = filename.substring(datePos + 2);

        //BUGFIX: the default dateformat starts with a dot, which looks ugly for us....
        if (getDatePattern().startsWith("'.'")) {
            //if it starts with the dot we delete that...
            setDatePattern(getDatePattern().substring(3));
        }

        // during startup the sdf is not yet initialised so we create a temporary one for ourselfs
        SimpleDateFormat localSdf = sdf;
        localSdf = new SimpleDateFormat(getDatePattern());

        //build new filename
        return baseFileName + localSdf.format(new Date()) + fileNameSuffix;
    }

    public void activateOptions() {
        setFile(calcFileName(getFile()));
        super.activateOptions();
    }

    /**
     Rollover the current file to a new file.
     */
    void rollOver() throws IOException {

        /* Compute filename, but only if datePattern is specified */
        if (getDatePattern() == null) {
            errorHandler.error("Missing DatePattern option in rollOver().");
            return;
        }

        //calculate the new filename
        String newFileName = baseFileName + sdf.format(new Date()) + fileNameSuffix;

        //check if we are too early to roll
        if (newFileName.equals(fileName)) {
            return;
        }

        // close current file
        this.closeFile();

        handleClosedFile();

        try {
            // This will also close the file. This is OK since multiple
            // close operations are safe.
            this.setFile(newFileName, true, this.bufferedIO, this.bufferSize);
        } catch (IOException e) {
            errorHandler.error("setFile(" + newFileName + ", false) call failed.");
        }
    }

    void handleClosedFile() {
    }

}
