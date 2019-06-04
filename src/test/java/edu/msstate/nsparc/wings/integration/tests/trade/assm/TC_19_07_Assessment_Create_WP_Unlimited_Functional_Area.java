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
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Check, that it's possible to create 'Wagner-Peyser' Assessments, though there is no limit for W-P Pre-Test Assessments per functional area.
 * Created by a.korotkin
 * modified by a.vnuchko
 */

@TestCase(id = "WINGS-10954")
public class TC_19_07_Assessment_Create_WP_Unlimited_Functional_Area extends TC_19_01_Assessment_Create_Cancel {

    public void main() {
        info("Precondition: Creating Wagner-Peyser Assessment");
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), Constants.FALSE);
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        Assessment assessment = new Assessment(participant, WAGNER_PEYSER);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS, Popup.Create);


        logStep("Select participant with Wagner-Peyser program and fill out all required information");
        AssessmentCreationForm creationPage = new AssessmentCreationForm();
        creationPage.fillAssessmentInformation(new User(Roles.STAFF), assessment);

        logStep("Click the [Create] button");
        creationPage.clickButton(Buttons.Create);

        logStep("Validate information of Assessment");
        AssessmentDetailsForm detailsPage = new AssessmentDetailsForm();
        detailsPage.validateAssessmentInformation(assessment);

        logStep("Click the [Create Another] button");
        detailsPage.clickButton(Buttons.CreateAnother);

        assessment.setParticipantPrePopulated(Constants.TRUE);

        logStep("Select the same participant with Wagner-Peyser program and repeat step 5");
        creationPage.fillAssessmentInformation(new User(Roles.STAFF), assessment);

        logStep("Click the [Create] button");
        creationPage.clickButton(Buttons.Create);

        logResult("The Assessment Creation screen is shown. Assessment is created due to the fact that there is no limit for W-P Pre-Test Assessments per functional area ");
        //new AssessmentDetailsForm().assertIsOpen();
    }
}
