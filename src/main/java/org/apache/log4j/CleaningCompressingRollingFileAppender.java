/* ******************************************************************************
* Project    : Tools
* File       : CleaningCompressingRollingFileAppender.java
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

import java.io.File;
import java.io.FilenameFilter;
import java.util.Calendar;

import org.apache.log4j.helpers.LogLog;

public class CleaningCompressingRollingFileAppender extends CompressingRollingFileAppender {

    int                     maxAge          = -1;
    public static final int AMX_AGE_DEFAULT = 30;

    int                     maxAgeType      = Calendar.DATE; // DEFAULT: max age in days

    public CleaningCompressingRollingFileAppender() {
        super();
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        if (maxAge > 1) {
            this.maxAge = maxAge;
        } else {
            LogLog.warn("MaxAge option must be >1 for appender [" + name + "], taking default:"
                        + AMX_AGE_DEFAULT);
            maxAge = AMX_AGE_DEFAULT;
        }
    }

    public int getMaxAgeType() {
        return maxAgeType;
    }

    public void setMaxAgeType(int maxAgeType) {
        if (maxAgeType == Calendar.MINUTE || maxAgeType == Calendar.HOUR
            || maxAgeType == Calendar.DATE || maxAgeType == Calendar.WEEK_OF_YEAR
            || maxAgeType == Calendar.MONTH) {
            this.maxAgeType = maxAgeType;
        } else {
            LogLog
                .warn("MaxAge option must be MINUTE, HOUR, DATE, WEEK_OF_YEAR or MONTH for appender ["
                      + name + "], taking default: days");
            maxAgeType = Calendar.DATE;
        }
    }

    public void activateOptions() {
        super.activateOptions();

        if (maxAge == -1) {
            LogLog.warn("MaxAge option is not set for appender [" + name + "], taking default:"
                        + AMX_AGE_DEFAULT);
            maxAge = AMX_AGE_DEFAULT;
        }
    }

    private File getDirectory() {
        File file = new File(fileName);

        if (file.isDirectory()) {
            return file;
        } else {
            return file.getParentFile();
        }
    }

    void handleClosedFile() {
        super.handleClosedFile();
        Calendar cal = Calendar.getInstance();
        cal.add(maxAgeType, -maxAge);
        long maxAge = cal.getTimeInMillis();

        String fileNamePrefix = getFilename(baseFileName);
        File[] files = listFiles(fileNamePrefix, fileNameSuffix + ".zip");

        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile() && files[i].lastModified() < maxAge) {
                if (!files[i].delete()) {
                    LogLog.warn("appender [" + name + "], could not delete file "
                                + files[i].getName());
                }
            }
        }
    }

    private String pref;
    private String suf;

    /**
     * @return
     */
    private File[] listFiles(String prefix, String suffix) {
        pref = prefix;
        suf = suffix;
        File[] files = getDirectory().listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                boolean b1 = name.startsWith(pref);
                boolean b2 = name.endsWith(suf);

                return b1 && b2;
            }
        });
        return files;
    }

}
