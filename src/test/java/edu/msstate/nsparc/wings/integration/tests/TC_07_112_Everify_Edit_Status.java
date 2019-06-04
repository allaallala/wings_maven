package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.everify.EverifyDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.everify.EverifyEditForm;
import edu.msstate.nsparc.wings.integration.forms.everify.EverifySearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.EverifySteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.Assert.assertEquals;

@TestCase(id = "WINGS-10680")
// Author: d.poznyak
public class TC_07_112_Everify_Edit_Status extends BaseWingsTest {

    private static final String CASE_NUMBER = "12345";

    public void main() {


        info("For E-Verify edit we need:");
        info("E-Verified Participant");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        EverifySearchForm everify = EverifySteps.openEverifySearchPage(participant, Roles.STAFF, CASE_NUMBER);

        logStep("Open one of the e-verify(Case number)->Edit");
        everify.openFirstSearchResult();
        EverifyDetailsForm everifyDetailsForm = new EverifyDetailsForm();
        everifyDetailsForm.clickButton(Buttons.Edit);

        logStep("Change E-Verify Status");
        EverifyEditForm everifyEditForm = new EverifyEditForm();
        everifyEditForm.selectStatus("Not Authorized");

        logStep("Save changes");
        everifyEditForm.clickButton(Buttons.Save);

        // validate
        logStep("Validate, that status is changed");
        everifyDetailsForm = new EverifyDetailsForm();
        assertEquals("Status assert fail", "Not Authorized", everifyDetailsForm.getStatusText());
        everifyDetailsForm.clickButton(Buttons.Done);

        logStep("Final View");
        BaseNavigationSteps.logout();
        logEnd();
    }
}
