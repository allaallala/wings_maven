package edu.msstate.nsparc.wings.integration.tests.employer;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.jobCenter.JobCenterSearchForm;
import edu.msstate.nsparc.xray.info.TestCase;


// Author: d.poznyak

@TestCase(id = "WINGS-10537")
public class TC_02_02_Dashboard_Locate_Job_Center_Search extends TC_02_01_Dashboard_Locate_Job_Center {


    public void main() {
        logStep("Find a WIN Job Center");
        JobCenterSearchForm jobCenterSearchForm = loginJobCenterSearch();

        logStep("Search");
        jobCenterSearchForm.clickButton(Buttons.Search);

        logStep("Validate search results");
        String center = jobCenterSearchForm.getJobCenterName();
        jobCenterSearchForm.validateSearchResults(center);
    }
}
