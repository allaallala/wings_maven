package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10668")
public class TC_07_02_WIA_Enrollment_Ended extends BaseWingsTest {
   private static final Integer TEST_DATA = 199;

    public void main() {

        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createAndPrepareParticipantForProgramOutcomes(participant, false, TEST_DATA);

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WIA_ENROLLMENT, Popup.Search);

        logStep("Search for Exited enrollment");
        WIAEnrollmentSearchForm searchForm = new WIAEnrollmentSearchForm();
        searchForm.performSearch(participant);

        logStep("Open Participant profile");
        searchForm.clickParticipant();

        logStep("Expand the WIA Enrollments section");
        BaseParticipantDetailsForm detailsForm = new BaseParticipantDetailsForm();
        detailsForm.expandWIAEnrollments();

        logStep("Make sure that WIA Enrollment has Ended status");
        String checkString = String.format("Ended: %1$s", CommonFunctions.getDaysAgoDate(TEST_DATA));
        assertTrue("WIA Enrollment doesn't have Ended status", detailsForm.getWiaEnrrlDetailsText().contains(checkString));
    }
}
