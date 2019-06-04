package edu.msstate.nsparc.wings.integration.tests.staff;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.everify.EverifyCreationForm;
import edu.msstate.nsparc.wings.integration.forms.everify.EverifySearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.Everify;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Open the e-verify creation form and fill in necessary fields, then click [Cancel] and check, that the Staff home form
 * is displayed.
 * Created by User on 14.03.2017.
 */

@TestCase(id = "WINGS-11258")
public class TC_38_19_Everify_Create_Cancel extends BaseWingsTest {

    public void main() {
        info("Preconditions: some participant haven't got e-verify numbers(don't notice by EV)");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        Everify everify = new Everify();
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);

        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_WP_E_VERIFY, Popup.Create);

        logStep("Select non e-verifying participant");
        EverifyCreationForm creationForm = new EverifyCreationForm();
        creationForm.selectParticipant(participant);

        logStep("Fill in all required fields with correct data");
        creationForm.fillRequiredFields(everify);

        logStep("Cancel");
        creationForm.clickButton(Buttons.Cancel);
        creationForm.areYouSure(Popup.Yes);

        logResult("Home page is opened, e-verify isn't created");
        new StaffHomeForm();
        BaseNavigationSteps.logout();

        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_WP_E_VERIFY, Popup.Search);
        EverifySearchForm searchForm = new EverifySearchForm();
        searchForm.performSearch(participant);
        searchForm.noSearchResults();
    }

}
