package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10516")
public class TC_01_12_WIA_Enrollment_Create_Dislocated_Worker extends BaseWingsTest {

    protected String applicationDate = CommonFunctions.getYesterdayDate();
    protected String contactPerson = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
    protected String contactPhone = CommonFunctions.getRandomIntegerNumber(Constants.CONTACT_LENGTH);
    protected String relation = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
    protected String expectedParticipantType = "Dislocated Worker";

    public void main () {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        WIAEnrollmentCreationForm wiaEnrollmentCreationForm = openEnrollmentCreationForm(participant);

        logStep("Select Participant type = Dislocated Worker");
        wiaEnrollmentCreationForm.fillWIAEnrollmentBasicInformation(applicationDate, contactPerson, contactPhone, relation, false);
        wiaEnrollmentCreationForm.fillBasicInformationDislocatedWorker();

        logStep("Fill in all required fields->Create");
        wiaEnrollmentCreationForm.fillWIAEnrollmentProgramInformation(false);

        repeatedSteps(participant, expectedParticipantType);

        BaseNavigationSteps.logout();
        logEnd();
    }

    /**
     * Open WIA enrollment creation form and return it
     * @param participant - participant
     */
    protected WIAEnrollmentCreationForm openEnrollmentCreationForm(Participant participant) {
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);

        logStep("Log in WINGS and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WIA_ENROLLMENT, Popup.Create);

        logStep("Select a participant");
        WIAEnrollmentCreationForm wiaEnrollmentCreationForm = new WIAEnrollmentCreationForm();
        wiaEnrollmentCreationForm.selectParticipant(participant);
        return wiaEnrollmentCreationForm;
    }

    /**
     * Some steps, that are repeated with other test
     * @param participant - participant
     */
    protected WIAEnrollmentDetailsForm repeatedSteps(Participant participant, String expectedType) {
        logStep("Find new created participant WIA Enrollment");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_ENROLLMENT);
        BaseWingsSteps.popClick(Popup.Search);
        WIAEnrollmentSearchForm wiaEnrollmentSearchForm = new WIAEnrollmentSearchForm();
        wiaEnrollmentSearchForm.selectParticipant(participant);
        wiaEnrollmentSearchForm.clickButton(Buttons.Search);

        logStep("Validate Enrollment data");
        wiaEnrollmentSearchForm.openFirstSearchResult();
        WIAEnrollmentDetailsForm wiaEnrollmentDetailsForm = new WIAEnrollmentDetailsForm();

        CustomAssertion.softTrue("Assert Basic Information Failed", wiaEnrollmentDetailsForm.validateBasicInformation(applicationDate, contactPerson, relation));
        CustomAssertion.softAssertEquals(wiaEnrollmentDetailsForm.getParticipantTypeText(), expectedType,"Assert Participant Type Failed");
        return wiaEnrollmentDetailsForm;
    }
}
