package edu.msstate.nsparc.wings.integration.xray;

import aqa.xray.XrayApi;
import aqa.xray.enums.XrayProperty;
import aqa.xray.models.AddRemoveTestExecutionToTestPlanReq;
import edu.msstate.nsparc.wings.integration.xray.configuration.XrayConfiguration;
import edu.msstate.nsparc.wings.integration.xray.utils.JiraIssueCreator;
import edu.msstate.nsparc.xray.utils.Store;
import org.json.JSONObject;

import java.util.Arrays;

public class PreconditionCreator extends edu.msstate.nsparc.xray.PreconditionCreator {
    private final XrayConfiguration xrayConfiguration = new XrayConfiguration();
    private final XrayApi xrayApi = new XrayApi();

    @Override
    public void addExecutionToProject() throws Exception{
        if (!xrayConfiguration.isEnabled()) {
            return;
        }
        String executionKey = xrayConfiguration.getExecutionKey();

        if(executionKey == null || executionKey.isEmpty()) {
            JSONObject executionObject = new JiraIssueCreator().create(
                    xrayConfiguration.getProjectKey(),
                    xrayConfiguration.getExecutionSummary(),
                    xrayConfiguration.getExecutionDescription(),
                    "Test Execution",
                    xrayConfiguration.getExecutionAssignee(),
                    xrayConfiguration.getAffectsVersion(),
                    //net
                    xrayConfiguration.getPriority(),
                    xrayConfiguration.getSecurityLevel(),
                    xrayConfiguration.getPlanKey());


            executionKey = (String) executionObject.get("key");
        }

        Store.store(executionKey, XrayProperty.EXECUTION_KEY.getName());

        // add execution into test plan
        AddRemoveTestExecutionToTestPlanReq addRemoveTestExecutionToTestPlanReq = new AddRemoveTestExecutionToTestPlanReq();
        addRemoveTestExecutionToTestPlanReq.setAdd(Arrays.asList(new String[]{executionKey}));
        xrayApi.addExecutionToTestPlan(xrayConfiguration.getPlanKey(), addRemoveTestExecutionToTestPlanReq);
    }
}
