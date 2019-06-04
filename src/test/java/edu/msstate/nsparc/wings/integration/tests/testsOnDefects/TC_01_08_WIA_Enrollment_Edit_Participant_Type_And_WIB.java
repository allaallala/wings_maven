package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentEditForm;
import edu.msstate.nsparc.wings.integration.forms.youthGoals.YouthGoalsSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.tests.WIA.TC_01_24_WIA_Enrollment_View;
import edu.msstate.nsparc.wings.integration.tests.WIA.TC_03_08_Youth_Goals_Create;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10507")
public class TC_01_08_WIA_Enrollment_Edit_Participant_Type_And_WIB extends TC_01_24_WIA_Enrollment_View {

    private static final String PARTICIPANT_TYPE = "Adult";
    private static final String WIB = "Statewide Activities";

    //Bug WINGS-2354 , sub-task WINGS-2408
    public void main() {
        //============Preconditions====================


        info("We need to create Participant, WIA Enrollment and Youth Assessment");
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        ParticipantCreationSteps.createRegularParticipant(participant, Boolean.TRUE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, Boolean.TRUE, Boolean.FALSE);
        Assessment assessment = new Assessment(participant, "WIA");
        User staff = new User(Roles.STAFF);
        AssessmentSteps.createAssessment(staff, assessment);

        info("We need to create WIA Youth Goal");
        TC_03_08_Youth_Goals_Create youthGoalsCreate = new TC_03_08_Youth_Goals_Create();
        youthGoalsCreate.createYouthGoalSteps1to5(participant);
        youthGoalsCreate.createYouthGoalSteps6to8(participant);

        //===========Test steps============
        openWiaEnrollmentDetails(participant);

        logStep("Click [Eidt] button");
        new WIAEnrollmentDetailsForm().clickAndWait(BaseWingsForm.BaseButton.EDIT_BASIC_INFORMATION);
        logStep("Change some data and click [Save Changes]");
        WIAEnrollmentEditForm wiaEnrollmentEditForm = new WIAEnrollmentEditForm();
        wiaEnrollmentEditForm.changeParticipantTypeAndWIB(PARTICIPANT_TYPE, WIB);
        wiaEnrollmentEditForm.clickButton(Buttons.Save);
        logStep("Validate changed data");
        WIAEnrollmentDetailsForm wiaEnrollmentDetailsForm = new WIAEnrollmentDetailsForm();
        assertEquals("Participant Type is incorrect after editing", wiaEnrollmentDetailsForm.getParticipantTypeText(), PARTICIPANT_TYPE);
        assertEquals("WIB is incorrect after editing", wiaEnrollmentDetailsForm.getWibText(), WIB);
        //Check that youth assessment was not changed for participant after editing
        logStep("Participant->Assessments-> Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Perform search for assessment");
        AssessmentSearchForm assessmentSearchForm = new AssessmentSearchForm();
        assessmentSearchForm.performSearchAndOpen(assessment);

        //Check that youth goal was not changed for participant after editing
        logStep("Participant->WIA->Youth Goals-> Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_YOUTH_GOALS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Search for Youth Goals using participant");
        YouthGoalsSearchForm youthGoalsSearchForm = new YouthGoalsSearchForm();
        youthGoalsSearchForm.performSearch(participant);

        logStep("Check search result");
        assertTrue("Assert Youth Goal is present failed", youthGoalsSearchForm.checkParticipantNamePresent());
    }
}
