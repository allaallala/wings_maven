package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderEditForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSearchForm;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10726")
public class TC_10_20_Job_Order_Edit extends BaseWingsTest {

    public void main() {
        String[] editedTitleAndNumberOfOpenings;

        TC_10_11_Job_Order_Search searchTest = new TC_10_11_Job_Order_Search();
        searchTest.main();

        logStep("Open job order edit form");
        JobOrderSearchForm jobOrderSearchForm = new JobOrderSearchForm();
        jobOrderSearchForm.clickJobTitle();
        jobOrderSearchForm.clickAndWait(BaseWingsForm.BaseButton.EDIT_BASIC_INFORMATION);

        logStep("Edit job order");
        JobOrderEditForm jobOrderEditForm = new JobOrderEditForm();
        editedTitleAndNumberOfOpenings = jobOrderEditForm.editJobTitleAndNumberOfOpenings();

        logStep("Open job order participantSSDetails form");
        JobOrderDetailsForm jobOrderDetailsForm = new JobOrderDetailsForm();
        CustomAssertion.softTrue("Number of openings is not present on the page", jobOrderDetailsForm.getJobTitleText().contains(editedTitleAndNumberOfOpenings[0]));
        CustomAssertion.softAssertEquals(jobOrderDetailsForm.getNumberOfOpenings(), editedTitleAndNumberOfOpenings[1],"Assert Number of Openings Failed");
        jobOrderDetailsForm.clickButton(Buttons.Done);

        logStep("Check, that edition is accepted");
        jobOrderSearchForm = new JobOrderSearchForm();
        jobOrderSearchForm.inputJobTitle(editedTitleAndNumberOfOpenings[0]);
        jobOrderSearchForm.clickButton(Buttons.Search);
        jobOrderSearchForm.checkInternalError();
        CustomAssertion.softTrue("Assert Job Title Failed", jobOrderSearchForm.getJobTitleText().contains(editedTitleAndNumberOfOpenings[0])); //TODO ISSUE https://jira.nsparc.msstate.edu/browse/WINGS-8902
    }
}