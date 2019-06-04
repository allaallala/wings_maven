package edu.msstate.nsparc.wings.integration.tests.trade.assm;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import webdriver.Browser;


/**
 * Create one pre-test Assessment for WIA programm and check, that it is impossible to create another.
 * Created by a.korotkin
 * modified by a.vnuchko
 */

@TestCase(id = "WINGS-10956")
public class TC_19_09_Assessment_Create_WIA_Limited_Quantity_Functional_Area extends TC_19_01_Assessment_Create_Cancel {

    public void main() {

        logStep("Precondition: creating WIA Enrollment");
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), Constants.FALSE);
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, Constants.FALSE, Constants.FALSE);
        Assessment assessment = new Assessment(participant, Constants.WIOA.toUpperCase());

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS, Popup.Create);

        logStep("Select participant with WIA program and fill out all required information");
        AssessmentCreationForm creationForm = new AssessmentCreationForm();
        creationForm.fillAssessmentInformation(new User(Roles.STAFF), assessment);

        logStep("Click the [Create] button");
        creationForm.clickButton(Buttons.Create);

        logStep("Validate information of Assessment");
        AssessmentDetailsForm assessmentDetailsForm = new AssessmentDetailsForm();
        assessmentDetailsForm.validateAssessmentInformation(assessment);
        assessment.setParticipantPrePopulated(Constants.TRUE);

        logStep("Click the [Create Another] button");
        creationForm.clickButton(Buttons.CreateAnother);

        logStep("Select program (WIA)");
        creationForm.selectProgram(assessment.getProgram());

        logStep("Select wia enrollment");
        creationForm.selectFirstEnrollment(Constants.WIOA.toUpperCase());

        logStep("Select Pre Test");
        creationForm.clickPreTest();


        logStep("Check the Functional Area drop-down list");
        creationForm.selectFunctionalArea(assessment.getFunctionalArea());
    }
}
