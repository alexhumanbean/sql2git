package com.sql2git;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alex on 26.10.2015.
 */
public abstract class Common {
    protected static int loggingEnabled = 1;
    private Logger log;

    private Logger initLogger() {
        if (log == null) {
            log = Logger.getLogger(this.getClass());
        }
        return log;
    }

    protected void logError(String errMess, Component parentComponent, boolean isFatal) {
        if(Common.loggingEnabled == 1) {
            initLogger();
            JOptionPane.showMessageDialog(parentComponent, errMess);
            log.error(errMess);
            if(isFatal)
                System.exit(0);
        }
    }
    protected void logError(String errMess) {
        logError(errMess, null, false);
    }
    protected void logFatalError(String errMess, Component parentComponent) {
        logError(errMess, parentComponent, true);
    }
    protected void logFatalError(String errMess) {
        logError(errMess, null, true);
    }

    protected void logInfo(String infoMess) {
        if(Common.loggingEnabled == 1) {
            initLogger();
            log.info(infoMess);
        }
    }
}
