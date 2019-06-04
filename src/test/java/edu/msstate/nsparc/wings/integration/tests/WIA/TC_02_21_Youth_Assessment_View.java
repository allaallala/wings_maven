package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


@TestCase(id = "WINGS-10551")
public class TC_02_21_Youth_Assessment_View extends BaseWingsTest {
    private String assessmentType = "WIA";
    private Assessment assessment;


    public void main() {
        baseSteps();
    }

    /**
     * Gets an assessment
     *
     * @return - assessment
     */
    protected Assessment baseSteps() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, Boolean.TRUE, Boolean.FALSE);
        assessment = new Assessment(participant, assessmentType);
        User staff = new User(Roles.STAFF);
        AssessmentSteps.createAssessment(staff, assessment);
        return assessment;
    }

    /**
     * Open assessment participantSSDetails form
     *
     * @param assessment - assessment
     * @return assessment participantSSDetails form.
     */
    protected AssessmentDetailsForm openAssessmentDetails(Assessment assessment) {
        logStep("Log in WINGS and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS, Popup.Search);

        logStep("Perform search for chosen assessment");
        AssessmentSearchForm assessmentSearchForm = new AssessmentSearchForm();
        assessmentSearchForm.performSearch(assessment);

        logStep("Open participant participantSSDetails form");
        assessmentSearchForm.openFirstSearchResult();

        logStep("Validate assessment data");
        AssessmentDetailsForm assessmentDetailsForm = new AssessmentDetailsForm();
        assessmentDetailsForm.validateAssessmentInformation(assessment);
        return assessmentDetailsForm;
    }
}
