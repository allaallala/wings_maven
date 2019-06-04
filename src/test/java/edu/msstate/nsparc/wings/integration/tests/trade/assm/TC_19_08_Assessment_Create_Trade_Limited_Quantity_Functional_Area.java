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
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import webdriver.Browser;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Create one pre-test Assessment for TRADE programm and check, that it is impossible to create another.
 * Created by a.korotkin
 * modified by a.vnuchko
 */

@TestCase(id = "WINGS-10955")
public class TC_19_08_Assessment_Create_Trade_Limited_Quantity_Functional_Area extends TC_19_01_Assessment_Create_Cancel {

    public void main() {
        info("Precondition: creating Trade Enrollment for Assessment");
        TradeEnrollment tradeEnrollment = new TradeEnrollment();
        TradeEnrollmentSteps.createTradeEnrollment(tradeEnrollment, Roles.ADMIN);
        Assessment assessment = new Assessment(tradeEnrollment.getParticipant(), Constants.TRADE);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS, Popup.Create);

        logStep("Select participant with TRADE program and fill out all required information");
        AssessmentCreationForm creationForm = new AssessmentCreationForm();
        creationForm.fillAssessmentInformation(new User(Roles.STAFF), assessment);

        logStep("Click the [Create] button");
        creationForm.clickButton(Buttons.Create);

        logStep("Validate information of Assessment");
        AssessmentDetailsForm assessmentDetailsForm = new AssessmentDetailsForm();
        assessmentDetailsForm.validateAssessmentInformation(assessment);
        assessment.setParticipantPrePopulated(Constants.TRUE);

        logStep("Click the [Create Another] button");
        assessmentDetailsForm .clickButton(Buttons.CreateAnother);

        logStep("Select program (Trade)");
        creationForm.selectProgram(assessment.getProgram());

        logStep("Select trade enrollment");
        creationForm.selectFirstEnrollment("Trade");

        logStep("Select Pre Test");
        creationForm.clickPreTest();


        logStep("Check the Functional Area drop-down list");
        assertTrue("Functional Area drop down isn't displayed", creationForm.checkFunctionalAreaCbPresent());

        logResult("Assessment with the same functioanal area cannot be created due to the fact that there is possibility\n " +
                "to create just one Pre-Test Assessments per functional area for TRADE program");
        //TBD
        assertEquals(Constants.FALSE, creationForm.checkFunctionalType(assessment.getFunctionalArea()), "Incorrect value '" + assessment.getFunctionalArea() + "' exists in Functional Area drop " +
                "down");
    }
}
