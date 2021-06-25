package de.javagl.jgltf.logging;

import java.util.logging.Level;

/**
 * more general logger class
 */
public class Logger {

    public Class<?> clazz;
    public String clazzName;

    public static Logger getLogger(Class<?> clazz) {
        Logger logger = new Logger();
        logger.clazz = clazz;
        logger.clazzName = clazz.getSimpleName();
        return logger;
    }

    public static Logger getLogger(String clazz) {
        Logger logger = new Logger();
        logger.clazzName = clazz;
        return logger;
    }

    /**
     * can be overwritten to use your own logger,
     * e.g. for Rem's Studios engine
     */
    public static Logger global = new Logger();

    public boolean isLoggable(Level level) {
        return global.isLoggable(this, level);
    }

    public boolean isLoggable(Logger logger, Level level) {
        return false;
    }

    public void log(Level lvl, String msg, Exception e) {
        global.log(this, lvl, msg, e);
    }

    public void log(Logger logger, Level lvl, String msg, Exception e) {
        System.out.println("[LOG :" + logger.clazzName + "] " + msg + ", " + e);
    }

    public void log(Level lvl, String msg) {
        global.log(this, lvl, msg);
    }

    public void log(Logger logger, Level lvl, String msg) {
        System.out.println("[LOG :" + logger.clazzName + "] " + msg);
    }

    public void info(String msg) {
        global.info(this, msg);
    }

    public void info(Logger logger, String msg) {
        System.out.println("[INFO:" + logger.clazzName + "] " + msg);
    }

    public void warning(String msg) {
        global.warning(this, msg);
    }

    public void warning(Logger logger, String msg) {
        System.out.println("[WARN:" + logger.clazzName + "] " + msg);
    }

    public void severe(String msg) {
        global.severe(this, msg);
    }

    public void severe(Logger logger, String msg) {
        System.out.println("[ERR!:" + logger.clazzName + "] " + msg);
    }

    public void fine(String msg) {
        global.finest(this, msg);
    }

    public void fine(Logger logger, String msg) {
        System.out.println("[FINE:" + logger.clazzName + "] " + msg);
    }

    public void finest(String msg) {
        global.finest(this, msg);
    }

    public void finest(Logger logger, String msg) {
        System.out.println("[FINE:" + logger.clazzName + "] " + msg);
    }

}
