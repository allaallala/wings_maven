package edu.msstate.nsparc.wings.integration.base;

import aqa.xray.enums.XrayTestStatus;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.xray.utils.JiraHooks;
import edu.msstate.nsparc.xray.utils.LoggerWrapper;
import framework.*;
import org.openqa.selenium.io.TemporaryFilesystem;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import webdriver.Browser;

import java.util.TimeZone;


/**
 * Base class for any tests
 */
public abstract class BaseWingsTest extends BaseTest {

    protected abstract void main() throws Exception;

    @Test
    protected void xTest() throws Exception {
        try {
            main();
        } finally {
            checkCustomAssertion();
        }
    }

    @BeforeMethod
    public void doBeforeTest(ITestResult testResult) {
        LoggerWrapper.getInstance().setUpLogFile(getClass().getName());
        Browser.getInstance().windowMaximise();
        if (getErrorsList().size() != 0) {
            getErrorsList().clear();
        }

        if (listWithScreenshots.get().size() != 0) {
            listWithScreenshots.get().clear();
        }
        String testTitle = "<!------  Test name is : " + CommonFunctions.regexGetMatch(this.getClass().getName(),"TC_\\w*") + " ------!>";
        StringBuilder enhancement = new StringBuilder("");
        for (int i = 0; i < testTitle.length(); i++) {
            enhancement.append("=");
        }
        info(enhancement.toString());
        info(testTitle);
        info(enhancement.toString());
        resetStepNumberToDefault();
        TimeZone.setDefault(TimeZone.getTimeZone("CST"));
        initProperties();
        AccountUtils.init();
        new JiraHooks().addTestToXrayExecution(testResult);
        info("Test Started");
    }

    @AfterMethod
    public void checkErrors(ITestResult testResult) {
        if (!testResult.isSuccess()) {
            new JiraHooks().publishXrayResult(testResult, XrayTestStatus.FAIL, testResult.getThrowable(), listWithScreenshots.get());
        } else {
            new JiraHooks().publishXrayResult(testResult, XrayTestStatus.PASS, null, listWithScreenshots.get());
        }
        doAfterTest();
    }

    private void checkCustomAssertion() {
        if (!getErrorsList().isEmpty()) {
            StringBuilder message = new StringBuilder("Following errors were found during test launch:" + "\n");
            for (String aListError : getErrorsList()) {
                message.append(" * ").append(aListError).append("\n");
            }
            Assert.assertTrue(Constants.FALSE, message.toString());
        }
    }

    public void doAfterTest() {
        AccountUtils.destroy();
        try {
            TemporaryFilesystem tmpSys = TemporaryFilesystem.getDefaultTmpFS();
            tmpSys.deleteTemporaryFiles();
            Browser.getInstance().exit();
            Logger.getInstance().info("Browser exit");
        } catch (Exception e) {
            BaseEntity.getLogger().info(e);
        }
    }
}