package framework;

import edu.msstate.nsparc.xray.utils.DateFunctions;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;

import java.io.File;
import java.nio.file.Paths;

public final class Logger {

    private static ThreadLocal<org.apache.log4j.Logger> LOG = ThreadLocal.withInitial(() ->
        org.apache.log4j.Logger.getLogger(String.valueOf(Thread.currentThread().getId())));
    private static ThreadLocal<Logger> instance = ThreadLocal.withInitial(Logger::new);
    public static final String LOG_DELIMITER = ":  :";
    private File logFile;
    private FileAppender fa;

    private Logger() {
        initialize();
    }

    public void initialize() {
        /*if (fa != null) {
            LOG.get().removeAppender(fa);
        }
        fa = new FileAppender();
        fa.setFile(logFile.getAbsolutePath());
        fa.setLayout(new PatternLayout("%d %-5p - %m%n"));
        fa.setThreshold(Level.INFO);
        fa.activateOptions();
        LOG.get().addAppender(fa);*/
    }

    public void info(Throwable e) {
        info(e.getMessage(), e);
    }

    public void warn(Throwable e) {
        warn(e.getMessage(), e);
    }

    public void error(Throwable e) {
        error(e.getMessage(), e);
    }

    public void debug(Throwable e) {
        debug(e.getMessage(), e);
    }


    public void info(Object message, Throwable e) {
        LOG.get().info(message, e);
    }

    public void warn(Object message, Throwable e) {
        LOG.get().warn(message, e);
    }

    public void error(Object message, Throwable e) {
        LOG.get().error(message, e);
    }

    public void debug(Object message, Throwable e) {
        LOG.get().debug(message, e);
    }

    public void fatal(Object message, Throwable e) {
        LOG.get().fatal(message, e);
    }


    public static Logger getInstance() {
        return instance.get();
    }


    public void debug(String message) {
        LOG.get().debug(message);
    }

    public void info(String message) {
        LOG.get().info(message);
    }

    public void warn(String message) {
        LOG.get().warn(message);
    }

    public void error(String message) {
        LOG.get().error(message);
    }

    public void fatal(String message) {
        LOG.get().fatal(message);
    }
}
