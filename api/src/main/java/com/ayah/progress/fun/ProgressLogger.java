package com.ayah.progress.fun;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * special logger for progressoft project
 * <p>
 * write logs message in log file,
 * <p>
 * the path of log path should added it on {@link #Progressoft.json src\main\Progressoft.json}
 * <p>
 * default Path is src/main/ApplicationLogs
 */
public class ProgressLogger extends Logger {
    private static FileHandler fh;

    /**
     * constructor of ProgressLogger
     * @param className 
     */
    public ProgressLogger(String className) {
        super(className, null);
        try {
            this.addHandler(fh);
            this.info("start log "+className);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * initialize logger
     * @param path  path of log file
     */

    public static void initProgressLogger(String path) {
        try {
            fh = new FileHandler(path != null && !path.isEmpty() ? path : "src/main/ApplicationLogs/MyLogFile.log");
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void finalize() {
        fh.close();
    }
}
