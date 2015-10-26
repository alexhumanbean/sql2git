package com.sql2git;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alex on 26.10.2015.
 */
public class Common {
    protected static int loggingEnabled = 0;
    private Logger log;

    private Logger initLogger() {
        if (log == null) {
            log = LoggerFactory.getLogger(this.getClass());
        }
        return log;
    }

    protected void logFatalError(String errMess, Component parentComponent) {
        if(Common.loggingEnabled == 1) {
            initLogger();
            JOptionPane.showMessageDialog(parentComponent, errMess);
            log.error(errMess);
            System.exit(0);
        }
    }
    protected void logFatalError(String errMess) {
        logFatalError(errMess, null);
    }
}
