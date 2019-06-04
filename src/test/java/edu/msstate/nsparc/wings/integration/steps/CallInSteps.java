package edu.msstate.nsparc.wings.integration.steps;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.callIn.CallInCreationForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;

public class CallInSteps extends BaseWingsSteps {

    public static void createCallIn(Participant participant, JobOrder order, Roles role) {
        BaseWingsSteps.openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.P_WP_CALL_INS, Popup.Create);
        CallInCreationForm creationPage = new CallInCreationForm();
        creationPage.selectParticipant(participant);
        creationPage.selectJobOrder(order);

        creationPage.clickButton(Buttons.Search);
        creationPage.processCallIn();
        BaseNavigationSteps.logout();
    }
}
