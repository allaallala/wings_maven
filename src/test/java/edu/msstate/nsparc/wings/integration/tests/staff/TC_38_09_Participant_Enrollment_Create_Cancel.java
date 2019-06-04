package edu.msstate.nsparc.wings.integration.tests.staff;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ServiceSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * Open the participant service enrollment creation form, fill it out and click cancel.
 * Check, that new record is not appeared.
 * Created by a.vnuchko on 09.03.2017.
 */

@TestCase(id = "WINGS-11250")
public class TC_38_09_Participant_Enrollment_Create_Cancel extends BaseWingsTest {
    protected String SERVICE_NAME = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);

    public void main() {
        info("Precondition: create participant and service first");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        ServiceSteps.createService(Roles.ADMIN, SERVICE_NAME, Constants.FALSE, Constants.FALSE);

        logStep("Select participant, fill in all required fields");
        ParticipantEnrollmentCreationForm creationForm = ParticipantNavigationSteps.openFillEnrlCreationPage(participant, SERVICE_NAME);
        creationForm.chooseScheduledService(Constants.FALSE);
        creationForm.inputCreationDate(CommonFunctions.getCurrentDate());
        creationForm.chooseEndedService(Constants.TRUE);
        creationForm.inputDateResult(CommonFunctions.getCurrentDate());
        creationForm.selectResult(Constants.COMPLETED);

        logStep("Cancel creation");
        creationForm.clickButton(Buttons.Cancel);
        creationForm.areYouSure(Popup.Yes);

        logResult("Enrollment Service  isn't created. Home page is opened");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_SERVICE_ENROLLMENT);
        BaseWingsSteps.popClick(Popup.Search);
        ParticipantEnrollmentSearchForm searchPage = new ParticipantEnrollmentSearchForm();
        searchPage.performSearch(participant, SERVICE_NAME);
        searchPage.noSearchResults();
    }
}
