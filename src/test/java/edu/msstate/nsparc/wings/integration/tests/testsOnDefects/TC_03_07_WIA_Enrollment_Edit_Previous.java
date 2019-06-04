package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentEditForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10567")
public class TC_03_07_WIA_Enrollment_Edit_Previous extends BaseWingsTest {
    String emptyData = "";

    //Bug WINGS-2573 , sub-task WINGS-2594
    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, Boolean.TRUE, Boolean.FALSE);

        logStep("Log in to the system and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WIA_ENROLLMENT, Popup.Search);
        WIAEnrollmentSearchForm wiaEnrollmentSearchForm = new WIAEnrollmentSearchForm();

        logStep("Search for enrollment");
        wiaEnrollmentSearchForm.selectParticipant(participant);
        wiaEnrollmentSearchForm.clickButton(Buttons.Search);

        logStep("Open Enrollment");
        wiaEnrollmentSearchForm.openFirstSearchResult();
        WIAEnrollmentDetailsForm wiaEnrollmentDetailsForm = new WIAEnrollmentDetailsForm();

        logStep("Click on Edit Program Information");
        wiaEnrollmentDetailsForm.clickEditProgramInfo();
        WIAEnrollmentEditForm wiaEnrollmentEditForm = new WIAEnrollmentEditForm();

        logStep("Clear 'Number in Family', Preprogram Wages' and 'Annual family income'");
        wiaEnrollmentEditForm.inputNumberFamily(emptyData);
        wiaEnrollmentEditForm.inputProgramWages(emptyData);
        wiaEnrollmentEditForm.inputAnnualFamilyIncome(emptyData);

        logStep("Click on Previous button");
        wiaEnrollmentEditForm.clickPrevious();

        logStep("Check, that error message is displayed");
        assertTrue("Error message wasn't displayed", wiaEnrollmentEditForm.checkErrorsPresent());

        BaseNavigationSteps.logout();
        logEnd();
    }
}
