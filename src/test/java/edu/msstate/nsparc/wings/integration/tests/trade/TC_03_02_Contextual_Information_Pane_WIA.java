package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wia.WIAEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


@TestCase(id = "WINGS-10558")
public class TC_03_02_Contextual_Information_Pane_WIA extends BaseWingsTest {

    public void main() {
        WIAEnrollment wiaEnrollment = new WIAEnrollment(new Participant(AccountUtils.getParticipantAccount(), Constants.TRUE));
        ParticipantCreationSteps.createParticipantDriver(wiaEnrollment.getParticipant(), Constants.TRUE, Constants.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), wiaEnrollment.getParticipant(), Constants.TRUE, Constants.FALSE);

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WIA_ENROLLMENT, Popup.Search);

        logStep("Enter parameters for searching");
        WIAEnrollmentSearchForm searchForm = new WIAEnrollmentSearchForm();
        searchForm.performSearchAndOpen(wiaEnrollment.getParticipant());

        logStep("Validate information in the Contextual Information Pane");
        WIAEnrollmentDetailsForm detailsForm = new WIAEnrollmentDetailsForm();
        detailsForm.validateWiaContextualInformationPane(wiaEnrollment);
    }
}
