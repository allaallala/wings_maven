package edu.msstate.nsparc.wings.integration.xray.providers;

import edu.msstate.nsparc.xray.utils.LoggerWrapper;
import webdriver.Browser;
import framework.Logger;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class ScenarioDataGetter extends edu.msstate.nsparc.xray.providers.ScenarioDataGetter {

    private Logger getLoggerInstance() {
        return Logger.getInstance();
    }

    public final byte[] getScreenshot(File file) {
        byte[] fileContent = null;
        try {
            fileContent = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            Logger.getInstance().warn(e.getMessage());
        }
        return fileContent;
    }
    @Override
    public final byte[] getExecutionLog() {
        byte[] executionLog = new byte[0];

        try {
            executionLog = Files.readAllBytes(LoggerWrapper.getInstance().getLogFile().toPath());
        } catch (IOException var3) {
            getLoggerInstance().warn(String.format("IO Exception during attaching logs%n%s", var3.getMessage()));
        }

        return executionLog;
    }

    @Override
    public byte[] getBrowserLog() {
        StringBuilder sb = new StringBuilder();
        List<LogEntry> logs = Browser.getDriver().manage().logs().get(LogType.BROWSER).getAll();
        for (LogEntry entry : logs) {
            sb.append(String.format("\r\n%s", entry.getMessage()));
        }
        return sb.toString().getBytes();
    }
}
