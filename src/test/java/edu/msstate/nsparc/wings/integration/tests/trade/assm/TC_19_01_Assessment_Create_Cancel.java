package edu.msstate.nsparc.wings.integration.tests.trade.assm;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Created by a.vnuchko on 17.06.2016.
 */

@TestCase(id = "WINGS-10944")
public class TC_19_01_Assessment_Create_Cancel extends BaseWingsTest {
    protected static final String WAGNER_PEYSER = "Wagner-Peyser";

    public void main() {
        info("Generate some test data");
        TradeEnrollment tradeEnrollment = new TradeEnrollment();
        TradeEnrollmentSteps.createTradeEnrollment(tradeEnrollment, Roles.ADMIN);
        Assessment assessment = new Assessment(tradeEnrollment.getParticipant(), Constants.TRADE);

        AssessmentCreationForm creationPage = AssessmentSteps.openFillCreationForm(assessment);

        logStep("Click 'Cancel'");
        creationPage.clickButton(Buttons.Cancel);
        creationPage.areYouSure(Popup.Yes);

        logResult("The Staff Home screen is shown, a new Assessment isn't created ");
        new StaffHomeForm();

        info("Navigate to Participants - Assessments - Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS);
        BaseWingsSteps.popClick(Popup.Search);

        logResult("Check, that assessment isn't created.");
        AssessmentSearchForm assessmentSearchForm = new AssessmentSearchForm();
        assessmentSearchForm.performSearch(assessment);
        assessmentSearchForm.noSearchResults();
    }

}
