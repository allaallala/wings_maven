package edu.msstate.nsparc.wings.integration.xray.utils;

import aqa.xray.enums.XrayTestStatus;
import aqa.xray.models.Evidence;
import edu.msstate.nsparc.wings.integration.xray.providers.ScenarioDataGetter;
import edu.msstate.nsparc.xray.info.ContentType;
import edu.msstate.nsparc.xray.providers.ScenarioProcessor;
import org.testng.ITestResult;

import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class JiraHooks extends edu.msstate.nsparc.xray.utils.JiraHooks {
    private ScenarioDataGetter scenarioDataGetter = new ScenarioDataGetter();

    public void publishXrayResult(ITestResult testResult, XrayTestStatus resultStatus, Throwable error, ArrayList<File> listOfScreenshots) {
        if (doWorkWithXray(testResult)) {
            String testId = ScenarioProcessor.getTestId(testResult);
            List<Evidence> evidences = getEvidencesOfRun(resultStatus, listOfScreenshots);
            List<String> defectIds = new ArrayList<>();
            if(ScenarioProcessor.hasDefectId(testResult)){
                defectIds = ScenarioProcessor.getDefectIds(testResult);
            }
            addEvidencesToTestRun(testId, defectIds, evidences, resultStatus, error);
            addTestExecutionTestResult(testId, resultStatus);
        }
    }

    private List<Evidence> getEvidencesOfRun(XrayTestStatus xrayTestStatus, List<File> listOfScreenshots) {
        List<Evidence> evidences = getEvidencesOfRun(xrayTestStatus);
        if (xrayTestStatus != XrayTestStatus.PASS) {
            for (File screenshot: listOfScreenshots) {
                evidences.add(createEvidence(Base64.getEncoder().encodeToString(scenarioDataGetter.getScreenshot(screenshot)),
                        screenshot.getName(),
                        ContentType.IMAGE_PNG_TYPE));
            }
        }
        return evidences;
    }
}


