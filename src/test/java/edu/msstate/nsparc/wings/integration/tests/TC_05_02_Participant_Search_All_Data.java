package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10616")
public class TC_05_02_Participant_Search_All_Data extends BaseWingsTest {


    public void main() {


        logStep("Login to the System");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);

        logStep("Don't fill in all controls->Search");
        ParticipantSearchForm searchForm = new ParticipantSearchForm();
        searchForm.clickButton(Buttons.Search);

        logStep("All Participant records are displayed (with 500 records limit)");
        searchForm.checkParticipantFound();

        logEnd();
    }
}
