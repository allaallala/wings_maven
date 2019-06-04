package framework.customassertions;

import framework.BaseEntity;
import framework.CommonFunctions;
import framework.Logger;

import java.io.File;

/**
 * Custom assertions for tests.
 * Created by a.vnuchko on 04.01.2016.
 */
public class CustomAssertion extends BaseEntity {

    private static String expectedText = "Expected: ";
    static String unexpectedText = "Unexpected: ";
    private static String actualText = "but actual: ";
    private static String errorText = "error";

    /**
     * Assert using 'contains' without failing the test
     * @param actual - actual value
     * @param expected - expected value
     * @param message - error message.
     */
    public static void softAssertContains(String actual, String expected, String message) {
        if (!actual.trim().contains(expected.trim())) {
            File screenshot = CommonFunctions.captureScreenshot("screenshot".concat(CommonFunctions.getTimestamp()));
            getErrorsList().add(message + "\n" + expectedText + expected + "\n" + actualText + actual+ "\n"+ String.format("Screenshot: %1$s", screenshot.getName()));
            listWithScreenshots.get().add(screenshot);
            Logger.getInstance().warn(message + "\n" + expectedText + expected + "\n" + actualText + actual);
        }
    }

    /**
     * Assert using 'equals' without failing the test
     * @param actual - actual value
     * @param expected - expected value
     * @param message - error message.
     */
    public static void softAssertEquals(String actual, String expected, String message) {
        if (!actual.trim().equals(expected.trim())) {
            File screenshot = CommonFunctions.captureScreenshot("screenshot".concat(CommonFunctions.getTimestamp()));
            getErrorsList().add(message + "\n" + expectedText + expected + "\n" + actualText + actual
                    + "\n"+ String.format("Screenshot: %1$s", screenshot.getName()));
            Logger.getInstance().warn(message + "\n" + expectedText + expected + "\n" + actualText + actual);
            listWithScreenshots.get().add(screenshot);
        }
    }

    /**
     * Assert, that actual is not the same as unexpected
     * @param actual - actual value
     * @param unexpected - unexpected value
     * @param message -error message
     */
    public static void softNotSame(Object actual, Object unexpected,String message) {
        if (actual == unexpected) {
            File screenshot = CommonFunctions.captureScreenshot("screenshot".concat(CommonFunctions.getTimestamp()));
            getErrorsList().add(message + "\n" + unexpectedText + unexpected + "\n" + actualText + actual + "\n"+ String.format("Screenshot: %1$s", screenshot.getName()));
            Logger.getInstance().warn(message + "\n" + unexpectedText + unexpected + "\n" + actualText + actual);
            listWithScreenshots.get().add(screenshot);
        }
    }

    /**
     * Replaces method assertTrue - makes it more soft.
     * @param message - error message
     * @param condititon - condition
     */
    public static void softTrue(String message, Boolean condititon) {
        if (!condititon) {
            File screenshot = CommonFunctions.captureScreenshot("screenshot".concat(CommonFunctions.getTimestamp()));
            getErrorsList().add(message + "\n"+ String.format("Screenshot: %1$s", screenshot.getName()));
            Logger.getInstance().warn(message);
            listWithScreenshots.get().add(screenshot);
        }
    }

    /**
     * Replaces method assertNotTrue - makes it more soft.
     * @param message - error message
     * @param condititon - condition
     */
    public static void softNotTrue(String message, Boolean condititon) {
        if (condititon) {
            File screenshot = CommonFunctions.captureScreenshot("screenshot".concat(CommonFunctions.getTimestamp()));
            getErrorsList().add(message + "\n"+ String.format("Screenshot: %1$s", screenshot.getName()));
            Logger.getInstance().warn(message);
            listWithScreenshots.get().add(screenshot);
        }
    }
}
