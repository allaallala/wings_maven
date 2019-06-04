package edu.msstate.nsparc.wings.integration.tests.staff;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.everify.EverifyCreationForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.Everify;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * Try to create e-verify with e-verify date + 1 month less than current date and check, that validation message is displayed.
 * Created by a.vnuchko on 14.03.2017.
 */

@TestCase(id = "WINGS-11257")
public class TC_38_18_Everify_Create_Edate_Less_Current extends BaseWingsTest {
    String errorText = "E-Verify Date must be no more than 1 month(s) in the past.";

    public void main() {
        info("Preconditions: some participant haven't got e-verify numbers(don't notice by EV)");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        Everify everify = new Everify();
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);

        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_WP_E_VERIFY, Popup.Create);

        logStep("Select non e-verifying participant");
        EverifyCreationForm creationForm = new EverifyCreationForm();
        creationForm.selectParticipant(participant);

        logStep("Select Status");
        creationForm.selectStatus(everify.getStatus());

        logStep("Select e-verify date + 1 month<current date");
        creationForm.inputEverifyDate(CommonFunctions.getDaysAgoDate(Constants.SEMESTER_DATE));

        logStep("Fill in other required fields with correct data");
        creationForm.inputStatusDate(everify.getStatusDate());
        creationForm.inputCaseNumber(everify.getCaseNumber());
        creationForm.selectWorkerStatus(everify.getWorkerStatus());
        creationForm.inputFirstSecondDocumentNumbers(everify.getDocumentIdentityType(), everify.getDocumentIdentityTypeNumber(), everify.getDocumentEmploymentType(),
                everify.getDocumentEmploymentTypeNumber());

        logStep("Click [Create]");
        creationForm.clickButton(Buttons.Create);

        logResult("Warning message appears");
        creationForm.validateErrorText(errorText);
    }
}
