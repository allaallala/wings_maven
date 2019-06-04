package framework;

/**
 * Baste test for any test classes.
 */
public abstract class BaseTest extends BaseEntity {

    private int stepNumber = 1;
    boolean logSteps = true;

    /**
     * Logs message in specified format
     * @param msg - message to log
     */
    private void logDelimMsg(String msg) {
        if (logSteps) {
            info(String.format("--------==[ %1$s ]==--------", msg));
        }
    }

    /**
     * Logs message in specified format (Step for the test with setting number of stepNumber).
     * @param step - stepNumber
     */
    public void logStep(int step) {
        logStep(step, null);
    }

    /**
     * Logs message in specified format (Step for the test without setting number of stepNumber)
     * @param msg - message
     */
    public void logStep(String msg) {
        logStep(stepNumber++, msg);
    }

    /**
     * Logs stepNumber and capture screenshot
     * @param step - stepNumber number
     * @param msg - message
     */
    public void logStep(int step, String msg) {


        info("---------------------------------------------------------");
        logDelimMsg("Step " + step);
        if (msg != null) {
            info("[ "+msg+" ]");
        }
        info("---------------------------------------------------------");

        String filename = step + ((msg != null) ? " " + msg : "");
        filename = filename.replace("->", " - ");
        filename = filename.replace("/", " - ");
    }

    /**
     * Logs some steps in one
     * @param fromStep - step number from
     * @param toStep - step number to.
     */
    public void logStep(int fromStep, int toStep) {
        logStep(fromStep, toStep, null);
    }

    /**
     * Logs some steps in one
     * @param fromStep - step number from
     * @param toStep - step number to
     * @param msg - message
     */
    public void logStep(int fromStep, int toStep, String msg) {
        if (msg != null) {
            info(msg);
        }
        logDelimMsg("Steps " + fromStep + "-" + toStep);
    }

    /**
     * Logs message in specified format (end of the test)
     */
    public void logEnd() {
        info("====================================================================================================");
    }

    /**
     * Logs message in specified format (result of the test)
     * @param msg - message to log
     */
    public void logResult(String msg){
        info("----------------------------------------------------------------------------------------------------");
        info("===============================================RESULT===============================================");
        info(msg);
    }

    /**
     *A beautiful precondition message.
     * @param msg - message to show.
     */
    public void preconditions(String msg){
        info("--------------------------------------------PRECONDITIONS-------------------------------------------");
        info("====================================================================================================");
        info(msg);
    }
    /**
     * Resets steps number.
     */
    protected void resetStepNumberToDefault() {
        stepNumber = 1;
    }

}
