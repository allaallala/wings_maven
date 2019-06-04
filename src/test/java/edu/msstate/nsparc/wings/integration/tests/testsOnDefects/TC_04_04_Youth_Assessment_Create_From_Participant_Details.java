package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import org.testng.Assert;


@TestCase(id = "WINGS-10590")
public class TC_04_04_Youth_Assessment_Create_From_Participant_Details extends BaseWingsTest {

    //Bug WINGS-2631, sub-task WINGS-2641
    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, Boolean.TRUE, Boolean.FALSE);

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);

        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearch(participant);

        logStep("Open Participant participantSSDetails form");
        participantSearchForm.openFirstSearchResult();
        BaseParticipantDetailsForm participantDetailsForm = new BaseParticipantDetailsForm();

        logStep("Open WIA Enrollment participantSSDetails");
        participantDetailsForm.openWiaEnrollmentDetailsForm();
        WIAEnrollmentDetailsForm wiaEnrollmentDetailsForm = new WIAEnrollmentDetailsForm();

        logStep("Click on 'Create Assessment' button");
        wiaEnrollmentDetailsForm.clickCreateAssesm();

        logStep("Check, that 'Youth Assessment Creation' form is displayed");
        AssessmentCreationForm creationForm = new AssessmentCreationForm();
        Assert.assertTrue("Assessment Creation".equals(creationForm.getSpanPageHeader()));
    }
}
