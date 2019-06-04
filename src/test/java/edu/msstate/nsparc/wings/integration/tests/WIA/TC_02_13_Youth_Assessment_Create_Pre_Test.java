package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentEditForm;
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
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10459")
public class TC_02_13_Youth_Assessment_Create_Pre_Test extends BaseWingsTest {


    public void main() {
        logStep("Creating Pre-Test Assessment");
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, Boolean.TRUE, Boolean.FALSE);
        Assessment assessment = new Assessment(participant, "WIOA");
        User staff = new User(Roles.STAFF);
        AssessmentSteps.createAssessment(staff, assessment);

        logStep("Log in WINGS and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS, Popup.Search);

        logStep("Open assessment search form");
        AssessmentSearchForm assessmentSearchForm = new AssessmentSearchForm();
        assessmentSearchForm.performSearchAndOpen(assessment);

        //sub-task WINGS-2754
        logStep("Check Functional area value");
        AssessmentDetailsForm detailsForm = new AssessmentDetailsForm();
        detailsForm.validateAssessmentInformation(assessment);

        logStep("Click on Edit button");
        detailsForm.clickButton(Buttons.Edit);

        logStep("Check Functional area drop down value");
        AssessmentEditForm editForm = new AssessmentEditForm();
        CustomAssertion.softAssertEquals(editForm.getFunctionalSelectedLabel(), assessment.getFunctionalArea(),
                "Incorrect value is selected in Functional Area drop down");

        logStep("Click on Save Changes");
        editForm.saveChanges();

        logStep("Check Functional area value");
        detailsForm.validateAssessmentInformation(assessment);
    }
}
