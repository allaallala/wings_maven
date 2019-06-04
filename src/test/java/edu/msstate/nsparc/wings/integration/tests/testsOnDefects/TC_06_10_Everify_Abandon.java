package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.everify.EverifyDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.everify.EverifySearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EverifySteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.AssertJUnit.assertFalse;

@TestCase(id = "WINGS-10658")
public class TC_06_10_Everify_Abandon extends BaseWingsTest {

    public void main() {
        info("Generate test data");
        String caseNumber = "12345";

        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        EverifySteps.eVerifyParticipant(participant, new User(Roles.STAFF));

        logStep("Login to the System");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Wagner-Peyser->E-Verify");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_E_VERIFY);

        logStep("Search");
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Search for E-Verify record");
        EverifySearchForm searchForm = new EverifySearchForm();
        searchForm.performSearchAndCheckResults(participant, caseNumber);

        logStep("Open E-Verify record");
        searchForm.openFirstSearchResult();

        logStep("Click on Delete button");
        EverifyDetailsForm detailsForm = new EverifyDetailsForm();
        detailsForm.deleteRecord();

        logStep("Search again for E-Verify");
        searchForm.clickButton(Buttons.Search);

        logStep("Check that record can't be found");
        assertFalse("E-Verify record is still searchable", searchForm.isFirstSearchResultPresent());

        logEnd();
    }
}
