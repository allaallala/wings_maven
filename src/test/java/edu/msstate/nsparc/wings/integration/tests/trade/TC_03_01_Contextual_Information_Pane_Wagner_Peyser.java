package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.everify.EverifyDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.everify.EverifySearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EverifySteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


@TestCase(id = "WINGS-10555")
public class TC_03_01_Contextual_Information_Pane_Wagner_Peyser extends BaseWingsTest {

    public void main() {


        info("For E-Verifying we need:");
        info("Unverified Participant");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        EverifySteps.eVerifyParticipant(participant, new User(Roles.STAFF));

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WP_E_VERIFY, Popup.Search);

        logStep("Find E-Verify record and open it");
        EverifySearchForm everifySearchForm = new EverifySearchForm();
        everifySearchForm.performSearchAndOpen(participant);

        logStep("Validate information in the Contextual Information Pane");
        EverifyDetailsForm detailsForm = new EverifyDetailsForm();
        detailsForm.validateWagnerPeyserContextualInformationPane(participant);
    }
}
