package edu.msstate.nsparc.wings.integration.tests.trade.individualEmploymentPlan;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanCreationForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.IEPSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Create some individual employment plan, navigate to participant service enrollment. Check, that services "Case Manager Assigned" and "Individual employment plan"
 * are created.
 * Created by a.vnuchko on 27.08.2015.
 */

@TestCase(id = "WINGS-11005")
public class TC_21_13_IEP_Create_Background_Services_Creation extends BaseWingsTest {
    String serviceName = "Case Manager Assigned";
    String roleName = "Auto Staff";
    String module = "Individual Employment Plan";

    public void main() {
        info("Create some data for the test");
        IndividualEmploymentPlan plan = new IndividualEmploymentPlan();
        ParticipantCreationSteps.createParticipantDriver(plan.getParticipant(), Boolean.TRUE, Boolean.FALSE);

        IndividualEmploymentPlanCreationForm creationPage = IEPSteps.openFillOutCreationForm(new User(Roles.STAFF), plan);

        logStep("Click the [Create] button");
        creationPage.clickButton(Buttons.Create);

        logStep("Navigate to Participant Service Enrollment search page");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_SERVICE_ENROLLMENT);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Search for Participant services (Case Manager Assigned)");
        ParticipantEnrollmentSearchForm searchPage = new ParticipantEnrollmentSearchForm();
        searchPage.performSearch(plan.getParticipant(), serviceName);

        logResult("Background services should be created: 'Case Manager Assigned'. It should not reset an Exit Date.");
        searchPage.validateSearchResultsInChosenRow(Constants.RANDOM_ONE.toString(), plan.getParticipant().getFirstName(), serviceName, CommonFunctions.getCurrentDate(),
                CommonFunctions.getCurrentDate(), roleName);

        logStep("Search for Participant services (Individual Employment Plan)");
        searchPage.clickButton(Buttons.Clear);
        searchPage.performSearch(plan.getParticipant(), "Individual Employment Plan");

        logResult("Background services should be created: 'Individual Employment Plan'. It should not reset an Exit Date.");
        searchPage.validateSearchResultsInChosenRow(Constants.RANDOM_ONE.toString(), plan.getParticipant().getFirstName(), module, CommonFunctions.getCurrentDate(),
                CommonFunctions.getCurrentDate(), roleName);
    }
}
