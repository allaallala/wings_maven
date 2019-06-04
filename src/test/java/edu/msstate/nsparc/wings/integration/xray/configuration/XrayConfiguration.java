package edu.msstate.nsparc.wings.integration.xray.configuration;

import edu.msstate.nsparc.wings.integration.functions.BambooHttpFunctions;
import edu.msstate.nsparc.wings.integration.xray.enums.TestType;
import edu.msstate.nsparc.xray.utils.DateFunctions;
import framework.PropertiesResourceManager;

public class XrayConfiguration extends edu.msstate.nsparc.xray.configuration.XrayConfiguration {

    private PropertiesResourceManager props = new PropertiesResourceManager("xray.properties");

    public String getSecurityLevel() {
        return props.getProperty("xray.execution.security");
    }

    @Override
    public String getPlanKey() {
        String planKey = System.getProperty("xrayPlanKey");
        return planKey != null ? planKey : props.getProperty("xray.plan.key");
    }

    @Override
    public String getExecutionSummary() {
        String xrayPlanKey = System.getProperty("xrayPlanKey");
        String testType;

        if(xrayPlanKey != null) {
            testType = TestType.getTestTypeByTestPlan(xrayPlanKey);
        } else testType = TestType.getTestTypeByTestPlan(props.getProperty("xray.plan.key"));

        return String.format("[%1$s][%2$s] Auto test execution", testType, BambooHttpFunctions.getLatestAppBuild());
    }

    @Override
    public String getExecutionDescription() {
        return String.format("* Test build completed: %1$s" + "\n"
                + "* Application build: %2$s" + "\n",
                DateFunctions.getCurrentDate("MM.dd.yyyy"), BambooHttpFunctions.getLatestAppBuild());
    }
}
